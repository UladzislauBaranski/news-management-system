package com.gmail.vladbaransky.newsmanagementsystem.service;

import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllComments();

    CommentDTO addComment(CommentDTO commentDTO);

    void deleteComment(CommentDTO comment);

    void deleteCommentById(Long id);

    List<CommentDTO> getPageableCommentsByNewsId(Pageable pageable, Long id);
}
