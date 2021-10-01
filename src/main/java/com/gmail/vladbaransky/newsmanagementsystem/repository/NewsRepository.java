package com.gmail.vladbaransky.newsmanagementsystem.repository;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Class with CRUD operations on news.
 * Repository level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.repository.NewsRepository
 */

public interface NewsRepository extends CrudRepository<News, Long>, PagingAndSortingRepository<News, Long> {
    List<News> findAll();
    Page<News> findAll(Pageable pageable);

}
