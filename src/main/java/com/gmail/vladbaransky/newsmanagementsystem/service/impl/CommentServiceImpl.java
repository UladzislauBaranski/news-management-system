package com.gmail.vladbaransky.newsmanagementsystem.service.impl;

import com.gmail.vladbaransky.newsmanagementsystem.repository.CommentRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.CommentConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class with CRUD operations on comments.
 * Service level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.service.CommentService
 */

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;

    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    public List<CommentDTO> getAllComments() {
        List<Comment> allCommentsFromDB = commentRepository.findAll();
        return allCommentsFromDB.stream().map(commentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDTO addComment(CommentDTO commentDTO) {
        Comment comment = commentConverter.getObjectFromDTO(commentDTO);
        Comment saveComment = commentRepository.save(comment);
        return commentConverter.getDTOFromObject(saveComment);
    }

    @Override
    @Transactional
    public void deleteComment(CommentDTO commentDTO) {
        Comment comment = commentConverter.getObjectFromDTO(commentDTO);
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDTO> getPageableCommentsByNewsId(Pageable pageable, Long id) {
        List<Comment> pageableCommentsByNewsId = commentRepository.findAllByNewsId(pageable, id);
        return pageableCommentsByNewsId.stream()
                .map(commentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }
}
