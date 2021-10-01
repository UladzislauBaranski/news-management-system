package com.gmail.vladbaransky.newsmanagementsystem.service.impl;

import com.gmail.vladbaransky.newsmanagementsystem.repository.NewsCommentRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.NewsCommentConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class with CRUD operations on news and comments.
 * Service level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService
 */

@Service
public class NewsCommentServiceImpl implements NewsCommentService {
    private final NewsCommentRepository newsCommentRepository;
    private final NewsCommentConverter newsCommentConverter;

    public NewsCommentServiceImpl(NewsCommentRepository newsCommentRepository, NewsCommentConverter newsCommentConverter) {
        this.newsCommentRepository = newsCommentRepository;
        this.newsCommentConverter = newsCommentConverter;
    }

    @Override
    public List<NewsCommentDTO> getAllNewsComment() {
        List<News> allNewsFromDB = (List<News>) newsCommentRepository.findAll();
        return allNewsFromDB.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public NewsCommentDTO getNewsAndCommentById(Long id) {
        Optional<News> newsById = newsCommentRepository.findById(id);
        News news = newsById.orElse(new News());
        return newsCommentConverter.getDTOFromObject(news);
    }

    @Override
    public List<NewsCommentDTO> getNewsAndCommentByNewsDate(LocalDateTime date) {
        List<News> allNewsCommentByNewsDate = newsCommentRepository.findAllByDate(date);
        return allNewsCommentByNewsDate.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsCommentDTO> getNewsAndCommentByNewsTitle(String title) {
        List<News> allNewsCommentByNewsTitle = newsCommentRepository.findAllByTitleContains(title);
        return allNewsCommentByNewsTitle.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsCommentDTO> getNewsAndCommentByNewsText(String text) {
        List<News> allNewsCommentByNewsText = newsCommentRepository.findAllByTextContains(text);
        return allNewsCommentByNewsText.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsCommentDTO> getNewsAndCommentByCommentDate(LocalDateTime date) {
        List<News> allNewsCommentByCommentDate = newsCommentRepository.findAllByCommentDate(date);
        return allNewsCommentByCommentDate.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsCommentDTO> getNewsAndCommentByCommentText(String text) {
        List<News> allNewsCommentByCommentText = newsCommentRepository.findAllByCommentText(text);
        return allNewsCommentByCommentText.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsCommentDTO> getNewsAndCommentByCommentUsername(String username) {
        List<News> allNewsCommentByCommentUsername = newsCommentRepository.findAllByCommentUsername(username);
        return allNewsCommentByCommentUsername.stream()
                .map(newsCommentConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }
}
