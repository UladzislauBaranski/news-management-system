package com.gmail.vladbaransky.newsmanagementsystem.service.unit;

import com.gmail.vladbaransky.newsmanagementsystem.repository.NewsCommentRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.NewsCommentConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.impl.NewsCommentServiceImpl;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsCommentServiceTest {

    private String time = "2017-10-06T17:48:23";

    private LocalDateTime alarmTime = LocalDateTime.parse(time);

    private NewsCommentService newsCommentService;
    @Mock
    private NewsCommentRepository newsCommentRepository;
    @Mock
    private NewsCommentConverter newsCommentConverter;

    @BeforeEach
    public void setup() {
        this.newsCommentService = new NewsCommentServiceImpl(newsCommentRepository, newsCommentConverter);
    }

    @Test
    public void getAllNewsComment_returnNewsComment() {
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAll()).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getAllNewsComment();
        verify(newsCommentRepository, times(1)).findAll();

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

/*    @Test
    public void getAllNewsCommentById_returnNewsComment() {
        Long newsId=1l;
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getPersistNewsCommentDTO();

        when(newsCommentRepository.findById(newsId)).thenReturn(java.util.Optional.of(news));
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getAllNewsComment();
        verify(newsCommentRepository, times(1)).findAll();

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }*/


    @Test
    public void getAllNewsCommentByNewsDate_returnNewsComment() {
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAllByDate(alarmTime)).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getNewsAndCommentByNewsDate(alarmTime);
        verify(newsCommentRepository, times(1)).findAllByDate(alarmTime);

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

    @Test
    public void getAllNewsCommentByNewsTitle_returnNewsComment() {
        String title = "test title";
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAllByTitleContains(title)).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getNewsAndCommentByNewsTitle(title);
        verify(newsCommentRepository, times(1)).findAllByTitleContains(title);

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

    @Test
    public void getAllNewsCommentByNewsText_returnNewsComment() {
        String text = "test text";
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAllByTextContains(text)).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getNewsAndCommentByNewsText(text);
        verify(newsCommentRepository, times(1)).findAllByTextContains(text);

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

    @Test
    public void getNewsAndCommentByCommentDate_returnNewsComment() {
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAllByCommentDate(alarmTime)).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getNewsAndCommentByCommentDate(alarmTime);
        verify(newsCommentRepository, times(1)).findAllByCommentDate(alarmTime);

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

    @Test
    public void getAllNewsCommentByCommentText_returnNewsComment() {
        String text = "test text";
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAllByCommentText(text)).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getNewsAndCommentByCommentText(text);
        verify(newsCommentRepository, times(1)).findAllByCommentText(text);

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

    @Test
    public void getAllNewsCommentByCommentUsername_returnNewsComment() {
        String username = "test username";
        List<News> newsList = getNewsList();
        News news = getNews();
        NewsCommentDTO newsCommentDTO = getNewsCommentDTO();

        when(newsCommentRepository.findAllByCommentUsername(username)).thenReturn(newsList);
        when(newsCommentConverter.getDTOFromObject(news)).thenReturn(newsCommentDTO);
        List<NewsCommentDTO> returnedNewsComments = newsCommentService.getNewsAndCommentByCommentUsername(username);
        verify(newsCommentRepository, times(1)).findAllByCommentUsername(username);

        for (int i = 0; i < returnedNewsComments.size(); i++) {
            getAssertion(returnedNewsComments.get(i), newsList.get(i));
        }
    }

    private void getAssertion(NewsCommentDTO returnedNewsComments, News news) {
        assertThat(returnedNewsComments).isNotNull();
        assertThat(returnedNewsComments.getDate()).isEqualTo(news.getDate());
        assertThat(returnedNewsComments.getText()).isEqualTo(news.getText());
        assertThat(returnedNewsComments.getTitle()).isEqualTo(news.getTitle());
        for (int i = 0; i < returnedNewsComments.getCommentsDTO().size(); i++) {

            assertThat(returnedNewsComments.getCommentsDTO().get(i).getDate())
                    .isEqualTo(news.getComments().get(i).getDate());

            assertThat(returnedNewsComments.getCommentsDTO().get(i).getText())
                    .isEqualTo(news.getComments().get(i).getText());

            assertThat(returnedNewsComments.getCommentsDTO().get(i).getUsername())
                    .isEqualTo(news.getComments().get(i).getUsername());
        }
    }

    private CommentDTO getCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDate(alarmTime);
        commentDTO.setText("test text");
        commentDTO.setUsername("test username");
        return commentDTO;
    }


    private List<CommentDTO> getPersistCommentListDTO() {
        List<CommentDTO> comments = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            CommentDTO commentDTO = getCommentDTO();
            commentDTO.setId(i);
            comments.add(commentDTO);
        }
        return comments;
    }

    private NewsCommentDTO getNewsCommentDTO() {
        NewsCommentDTO newsCommentDTO = new NewsCommentDTO();
        newsCommentDTO.setDate(alarmTime);
        newsCommentDTO.setText("test text");
        newsCommentDTO.setTitle("test title");
        newsCommentDTO.setCommentsDTO(getPersistCommentListDTO());
        return newsCommentDTO;
    }

    private NewsCommentDTO getPersistNewsCommentDTO() {
        NewsCommentDTO newsCommentDTO = new NewsCommentDTO();
        newsCommentDTO.setId(1L);
        newsCommentDTO.setDate(alarmTime);
        newsCommentDTO.setText("test text");
        newsCommentDTO.setTitle("test title");
        newsCommentDTO.setCommentsDTO(getPersistCommentListDTO());
        return newsCommentDTO;
    }

    private List<NewsCommentDTO> getPersistNewsCommentListDTO() {
        List<NewsCommentDTO> newsCommentDTOList = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            NewsCommentDTO newsCommentDTO = getPersistNewsCommentDTO();
            newsCommentDTO.setId(i);
            newsCommentDTOList.add(newsCommentDTO);
        }
        return newsCommentDTOList;
    }

    //----------------------------------
    private Comment getComment() {
        Comment comment = new Comment();
        comment.setDate(alarmTime);
        comment.setText("test text");
        comment.setUsername("test username");
        return comment;
    }


    private List<Comment> getPersistNewsList() {
        List<Comment> comments = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Comment comment = getComment();
            comment.setId(i);
            comments.add(comment);
        }
        return comments;
    }


    private News getNews() {
        News news = new News();
        news.setDate(alarmTime);
        news.setText("test text");
        news.setTitle("test title");
        news.setComments(getPersistNewsList());
        return news;
    }

    private News getPersistNews() {
        News news = new News();
        news.setId(1L);
        news.setDate(alarmTime);
        news.setText("test text");
        news.setTitle("test title");
        news.setComments(getPersistNewsList());
        return news;
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
