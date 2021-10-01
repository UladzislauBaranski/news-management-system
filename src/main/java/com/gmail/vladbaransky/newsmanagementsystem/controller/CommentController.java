package com.gmail.vladbaransky.newsmanagementsystem.controller;

import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsService;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.ValidationError;
import com.gmail.vladbaransky.newsmanagementsystem.util.ValidationErrorBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Class with CRUD operations on comments.
 * Controller level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.controller.CommentController
 */

@RestController
@RequestMapping("/comment")
public class CommentController {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final CommentService commentService;
    private final NewsService newsService;

    public CommentController(CommentService commentService, NewsService newsService) {
        this.commentService = commentService;
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPageableCommentsByNewsId(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                         @PathVariable Long id) {
        return ResponseEntity.ok(commentService.getPageableCommentsByNewsId(pageable, id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentDTO comment, @PathVariable Long id, Errors errors) {
        return persistComment(comment, id, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editComment(@Valid @RequestBody CommentDTO comment, @PathVariable Long id, Errors errors) {
        return persistComment(comment, id, errors);
    }

    private ResponseEntity<?> persistComment(CommentDTO comment, Long id, Errors errors) {
        if (errors.hasErrors()) {
            logger.info("Entity :" + comment + " not valid");
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        } else {
            NewsDTO newsById = newsService.getNewsById(id);
            if (newsById.getId() != null) {
                comment.setNewsDTO(newsById);
                commentService.addComment(comment);
                logger.info("Entity :" + comment + " saved");
            } else {
                logger.info("News not found");
            }
            return ResponseEntity.ok(commentService.getAllComments());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@Valid @RequestBody CommentDTO comment, Errors errors) {
        if (errors.hasErrors()) {
            logger.info("Entity :" + comment + " not valid");
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        } else {
            commentService.deleteComment(comment);
            logger.info("Entity :" + comment + " deleted");
            return ResponseEntity.ok(commentService.getAllComments());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        logger.info("Comment with id: " + id + " deleted");
        return ResponseEntity.ok(commentService.getAllComments());
    }


    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}
