package com.gmail.vladbaransky.newsmanagementsystem.controller;

import com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.ValidationError;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class with CRUD operations on news and comments.
 * Controller level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.controller.NewsCommentController
 */

@RestController
@RequestMapping("/news-comment")
public class NewsCommentController {
    private final NewsCommentService newsCommentService;

    public NewsCommentController(NewsCommentService newsCommentService) {
        this.newsCommentService = newsCommentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCommentDTO> getNewsAndCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentById(id));
    }

    @GetMapping("/by-news-date")
    public ResponseEntity<List<NewsCommentDTO>> getNewsAndCommentByNewsDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentByNewsDate(date));
    }

    @GetMapping("/by-news-title")
    public ResponseEntity<List<NewsCommentDTO>> getNewsAndCommentByNewsTitle(@RequestParam String title) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentByNewsTitle(title));
    }

    @GetMapping("/by-news-text")
    public ResponseEntity<List<NewsCommentDTO>> getNewsAndCommentByNewsText(@RequestParam String text) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentByNewsText(text));
    }

    @GetMapping("/by-comment-date")
    public ResponseEntity<List<NewsCommentDTO>> getNewsAndCommentByCommentDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentByCommentDate(date));
    }

    @GetMapping("/by-comment-text")
    public ResponseEntity<List<NewsCommentDTO>> getNewsAndCommentByCommentText(@RequestParam String text) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentByCommentText(text));
    }

    @GetMapping("/by-comment-username")
    public ResponseEntity<List<NewsCommentDTO>> getNewsAndCommentByCommentUsername(@RequestParam String username) {
        return ResponseEntity.ok(newsCommentService.getNewsAndCommentByCommentUsername(username));
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(Exception exception) {
        return new ValidationError(exception.getMessage());
    }
}
