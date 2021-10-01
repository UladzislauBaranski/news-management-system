package com.gmail.vladbaransky.newsmanagementsystem.service;

import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    List<NewsDTO> getAllNews();

    NewsDTO getNewsById(Long id);

    List<NewsDTO> getNewsByPage(Pageable pageable);

    NewsDTO addNews(NewsDTO newsDTO);

    void deleteNews(NewsDTO newsDTO);

    void deleteNewsById(Long id);
}
