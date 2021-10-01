package com.gmail.vladbaransky.newsmanagementsystem.controller;

import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsService;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.ValidationError;
import com.gmail.vladbaransky.newsmanagementsystem.util.ValidationErrorBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Class with CRUD operations on news.
 * Controller level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.controller.NewsController
 */

@RestController
@RequestMapping("/news")
public class NewsController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsService newsService;
    private final NewsCommentService newsCommentService;
    private final CommentService commentService;

    public NewsController(NewsService newsService, NewsCommentService newsCommentService, CommentService commentService) {
        this.newsService = newsService;
        this.newsCommentService = newsCommentService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCommentDTO> getNewsAndPageableCommentByNewsId(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id) {
        NewsCommentDTO newsAndCommentById = newsCommentService.getNewsAndCommentById(id);
        List<CommentDTO> pageableCommentsByNewsId = commentService.getPageableCommentsByNewsId(pageable, id);
        newsAndCommentById.setCommentsDTO(pageableCommentsByNewsId);
        return ResponseEntity.ok(newsAndCommentById);
    }

    @GetMapping("/by-page")
    public ResponseEntity<List<NewsDTO>> getNewsByPage(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(newsService.getNewsByPage(pageable));
    }

    @PostMapping
    public ResponseEntity<?> addNews(@RequestBody @Valid NewsDTO news, Errors errors) {
        return persistComment(news, errors);
    }

    @PutMapping
    public ResponseEntity<?> editNews(@RequestBody @Valid NewsDTO news, Errors errors) {
        return persistComment(news, errors);
    }

    private ResponseEntity<?> persistComment(NewsDTO news, Errors errors) {
        if (errors.hasErrors()) {
            logger.info("Entity :" + news + " not valid");
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        } else {
            newsService.addNews(news);
            logger.info("Entity :" + news + " saved");
            return ResponseEntity.ok(newsService.getAllNews());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteNews(@Valid @RequestBody NewsDTO news, Errors errors) {
        if (errors.hasErrors()) {
            logger.info("Entity :" + news + " not valid");
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        } else {
            newsService.deleteNews(news);
            logger.info("Entity :" + news + " deleted");
            return ResponseEntity.ok(newsService.getAllNews());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewsById(@PathVariable Long id) {
        newsService.deleteNewsById(id);
        logger.info("News with id: " + id + " deleted");
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}
