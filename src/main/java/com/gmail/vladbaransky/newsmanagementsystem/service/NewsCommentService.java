package com.gmail.vladbaransky.newsmanagementsystem.service;

import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsCommentService {

    List<NewsCommentDTO> getAllNewsComment();

    NewsCommentDTO getNewsAndCommentById(Long id);


    List<NewsCommentDTO> getNewsAndCommentByNewsDate(LocalDateTime date);

    List<NewsCommentDTO> getNewsAndCommentByNewsTitle(String title);

    List<NewsCommentDTO> getNewsAndCommentByNewsText(String text);

    List<NewsCommentDTO> getNewsAndCommentByCommentDate(LocalDateTime date);

    List<NewsCommentDTO> getNewsAndCommentByCommentText(String text);

    List<NewsCommentDTO> getNewsAndCommentByCommentUsername(String username);
}
