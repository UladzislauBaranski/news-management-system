package com.gmail.vladbaransky.newsmanagementsystem.service.unit;

import com.gmail.vladbaransky.newsmanagementsystem.repository.CommentRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.NewsRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsService;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.CommentConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.NewsConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.impl.CommentServiceImpl;
import com.gmail.vladbaransky.newsmanagementsystem.service.impl.NewsServiceImpl;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {

    private String time = "2017-10-06T17:48:23";

    private LocalDateTime alarmTime = LocalDateTime.parse(time);

    private NewsService newsService;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsConverter newsConverter;

    @BeforeEach
    public void setup() {
        this.newsService = new NewsServiceImpl(newsRepository, newsConverter);
    }

    @Test
    public void getAllNews_returnNews() {
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsDTO newsDTO = getNewsDTO();
        when(newsRepository.findAll()).thenReturn(newsList);
        when(newsConverter.getDTOFromObject(news)).thenReturn(newsDTO);
        List<NewsDTO> returnedNews = newsService.getAllNews();
        verify(newsRepository, times(1)).findAll();

        for (int i = 0; i < returnedNews.size(); i++) {
            getAssertion(returnedNews.get(i), newsList.get(i));
        }
    }

    @Test
    public void getNewsById_returnNews() {
        Long newsId = 1L;
        News persistNews = getPersistNews();
        Optional<News> news = Optional.of(getPersistNews());
        NewsDTO newsDTO = getPersistNewsDTO();

        when(newsRepository.findById(newsId)).thenReturn(news);
        when(newsConverter.getDTOFromObject(persistNews)).thenReturn(newsDTO);
        NewsDTO returnedNews = newsService.getNewsById(newsId);
        verify(newsRepository, times(1)).findById(newsId);

        getAssertion(returnedNews, persistNews);
    }

/*    @Test
    public void getNewsByPage_returnNews() {
        News news = getPersistNews();
        NewsDTO newsDTO = getPersistNewsDTO();

        Page<News> newsList = (Page<News>) getPersistNewsList();
        List<News> persistNewsList = getPersistNewsList();

        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");

        when(newsRepository.findAll(pageable)).thenReturn(newsList);
        when(newsConverter.getDTOFromObject(news)).thenReturn(newsDTO);
        List<NewsDTO> returnedNews = newsService.getNewsByPage(pageable);
        verify(newsRepository, times(1)).findAll(pageable);

        System.out.println("returned:" + returnedNews);
        System.out.println("expect:" + persistNewsList);
        for (int i = 0; i < returnedNews.size(); i++) {
            getAssertion(returnedNews.get(i), persistNewsList.get(i));
        }
    }*/

    @Test
    public void addNews_returnNews() {
        News news = getNews();
        NewsDTO newsDTO = getNewsDTO();

        when(newsRepository.save(news)).thenReturn(news);
        when(newsConverter.getObjectFromDTO(newsDTO)).thenReturn(news);
        when(newsConverter.getDTOFromObject(news)).thenReturn(newsDTO);
        NewsDTO returnedNews = newsService.addNews(newsDTO);
        verify(newsRepository, times(1)).save(news);

        getAssertion(returnedNews, news);
    }

    private void getAssertion(NewsDTO returnedNews, News news) {
        assertThat(returnedNews).isNotNull();
        assertThat(returnedNews.getDate()).isEqualTo(news.getDate());
        assertThat(returnedNews.getText()).isEqualTo(news.getText());
        assertThat(returnedNews.getTitle()).isEqualTo(news.getTitle());
    }

    private NewsDTO getNewsDTO() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setDate(alarmTime);
        newsDTO.setText("test text");
        newsDTO.setTitle("test title");
        return newsDTO;
    }

    private NewsDTO getPersistNewsDTO() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(1L);
        newsDTO.setDate(alarmTime);
        newsDTO.setText("test text");
        newsDTO.setTitle("test title");
        return newsDTO;
    }

    private List<NewsDTO> getPersistNewsListDTO() {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            NewsDTO newsDTO = getNewsDTO();
            newsDTO.setId(i);
            newsDTOList.add(newsDTO);
        }
        return newsDTOList;
    }


    private News getNews() {
        News news = new News();
        news.setDate(alarmTime);
        news.setText("test text");
        news.setTitle("test title");
        return news;
    }

    private News getPersistNews() {
        News news = new News();
        news.setId(1L);
        news.setDate(alarmTime);
        news.setText("test text");
        news.setTitle("test title");
        return news;
    }

    private List<News> getPersistNewsList() {
        List<News> newsList = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            News news = getNews();
            news.setId(i);
            newsList.add(news);
        }
        return newsList;
    }

    private List<News> getNewsList() {
        List<News> newsList = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            News news = getNews();
            newsList.add(news);
        }
        return newsList;
    }


}
