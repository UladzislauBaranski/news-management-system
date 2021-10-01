package com.gmail.vladbaransky.newsmanagementsystem.service.impl;

import com.gmail.vladbaransky.newsmanagementsystem.repository.NewsRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsService;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.NewsConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class with CRUD operations on news.
 * Service level.
 *
 * @author Uladzislau Baranski
 * @see com.gmail.vladbaransky.newsmanagementsystem.service.NewsService
 */

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsConverter newsConverter;

    public NewsServiceImpl(NewsRepository newsRepository, NewsConverter newsConverter) {
        this.newsRepository = newsRepository;
        this.newsConverter = newsConverter;
    }


    @Override
    public List<NewsDTO> getAllNews() {
        List<News> allNewsFromDB = newsRepository.findAll();
        return allNewsFromDB.stream()
                .map(newsConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        Optional<News> newsById = newsRepository.findById(id);
        News news = newsById.orElse(new News());
        return newsConverter.getDTOFromObject(news);
    }

    @Override
    public List<NewsDTO> getNewsByPage(Pageable pageable) {
        Page<News> newsByPageFromDB = newsRepository.findAll(pageable);
        return newsByPageFromDB.stream()
                .map(newsConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NewsDTO addNews(NewsDTO newsDTO) {
        News news = newsConverter.getObjectFromDTO(newsDTO);
        News saveNews = newsRepository.save(news);
        return newsConverter.getDTOFromObject(saveNews);
    }

    @Override
    @Transactional
    public void deleteNews(NewsDTO newsDTO) {
        News news = newsConverter.getObjectFromDTO(newsDTO);
        newsRepository.delete(news);
    }

    @Override
    @Transactional
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }
}
