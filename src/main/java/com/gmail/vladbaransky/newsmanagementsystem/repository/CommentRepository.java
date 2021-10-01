package com.gmail.vladbaransky.newsmanagementsystem.repository;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Class with CRUD operations on comments.
 * Repository level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.repository.CommentRepository
 */

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll();

    List<Comment> findAllByNewsId(Pageable pageable, Long id);
}
