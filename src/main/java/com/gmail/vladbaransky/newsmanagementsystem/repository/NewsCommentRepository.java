package com.gmail.vladbaransky.newsmanagementsystem.repository;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class with CRUD operations on news and comments.
 * Repository level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.repository.NewsCommentRepository
 */

public interface NewsCommentRepository extends CrudRepository<News, Long> {
    List<News> findAllByDate(LocalDateTime date);

    List<News> findAllByTitleContains(String title);

    List<News> findAllByTextContains(String text);

    @Query(value = "select * from news n join comment c on n.id=c.id_news where c.date =:date", nativeQuery = true)
    List<News> findAllByCommentDate(@Param("date") LocalDateTime date);

    @Query(value = "select * from news n join comment c on n.id=c.id_news where c.text like %:text%", nativeQuery = true)
    List<News> findAllByCommentText(@Param("text") String text);

    @Query(value = "select * from news n join comment c on n.id=c.id_news where c.username like %:username%", nativeQuery = true)
    List<News> findAllByCommentUsername(@Param("username") String username);
}
