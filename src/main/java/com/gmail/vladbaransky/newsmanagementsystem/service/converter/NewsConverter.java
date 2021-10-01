package com.gmail.vladbaransky.newsmanagementsystem.service.converter;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.springframework.stereotype.Component;

@Component
public class NewsConverter {
    public NewsDTO getDTOFromObject(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setDate(news.getDate());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setText(news.getText());
        return newsDTO;
    }

    public News getObjectFromDTO(NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setDate(newsDTO.getDate());
        news.setTitle(newsDTO.getTitle());
        news.setText(newsDTO.getText());
        return news;
    }
}
