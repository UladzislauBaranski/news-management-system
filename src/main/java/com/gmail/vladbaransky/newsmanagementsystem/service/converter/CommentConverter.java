package com.gmail.vladbaransky.newsmanagementsystem.service.converter;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public CommentDTO getDTOFromObject(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setText(comment.getText());
        commentDTO.setUsername(comment.getUsername());
        if (comment.getNews() != null) {
            NewsDTO newsDTO = new NewsDTO();
            newsDTO.setId(comment.getNews().getId());
            newsDTO.setDate(comment.getNews().getDate());
            newsDTO.setTitle(comment.getNews().getTitle());
            newsDTO.setText(comment.getNews().getText());
            commentDTO.setNewsDTO(newsDTO);
        }
        return commentDTO;
    }

    public Comment getObjectFromDTO(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setDate(commentDTO.getDate());
        comment.setText(commentDTO.getText());
        comment.setUsername(commentDTO.getUsername());
        if (commentDTO.getNewsDTO() != null) {
            News news = new News();
            news.setId(commentDTO.getNewsDTO().getId());
            news.setDate(commentDTO.getNewsDTO().getDate());
            news.setTitle(commentDTO.getNewsDTO().getTitle());
            news.setText(commentDTO.getNewsDTO().getText());
            comment.setNews(news);
        }
        return comment;
    }
}
