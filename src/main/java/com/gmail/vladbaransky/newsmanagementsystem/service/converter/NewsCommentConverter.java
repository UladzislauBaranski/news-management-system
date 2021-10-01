package com.gmail.vladbaransky.newsmanagementsystem.service.converter;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsCommentConverter {
    public NewsCommentDTO getDTOFromObject(News news) {
        NewsCommentDTO newsCommentDTO = new NewsCommentDTO();
        newsCommentDTO.setId(news.getId());
        newsCommentDTO.setDate(news.getDate());
        newsCommentDTO.setTitle(news.getTitle());
        newsCommentDTO.setText(news.getText());
        if (news.getComments() != null) {
            List<CommentDTO> commentDTOList = new ArrayList<>();
            for (int i = 0; i < news.getComments().size(); i++) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(news.getComments().get(i).getId());
                commentDTO.setDate(news.getComments().get(i).getDate());
                commentDTO.setText(news.getComments().get(i).getText());
                commentDTO.setUsername(news.getComments().get(i).getUsername());
                commentDTOList.add(commentDTO);
            }
            newsCommentDTO.setCommentsDTO(commentDTOList);
        }
        return newsCommentDTO;
    }

    public News getObjectFromDTO(NewsCommentDTO newsCommentDTO) {
        News news = new News();
        news.setId(newsCommentDTO.getId());
        news.setDate(newsCommentDTO.getDate());
        news.setTitle(newsCommentDTO.getTitle());
        news.setText(newsCommentDTO.getText());
        if (newsCommentDTO.getCommentsDTO() != null) {
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < newsCommentDTO.getCommentsDTO().size(); i++) {
                Comment comment = new Comment();
                comment.setId(newsCommentDTO.getCommentsDTO().get(i).getId());
                comment.setDate(newsCommentDTO.getCommentsDTO().get(i).getDate());
                comment.setText(newsCommentDTO.getCommentsDTO().get(i).getText());
                comment.setUsername(newsCommentDTO.getCommentsDTO().get(i).getUsername());
                comments.add(comment);
            }
            news.setComments(comments);
        }
        return news;
    }
}
