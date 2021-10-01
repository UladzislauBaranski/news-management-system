package com.gmail.vladbaransky.newsmanagementsystem.service.unit;

import com.gmail.vladbaransky.newsmanagementsystem.repository.CommentRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.Comment;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.News;
import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.converter.CommentConverter;
import com.gmail.vladbaransky.newsmanagementsystem.service.impl.CommentServiceImpl;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private String time = "2017-10-06T17:48:23";

    private LocalDateTime alarmTime = LocalDateTime.parse(time);

    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentConverter commentConverter;

    @BeforeEach
    public void setup() {
        this.commentService = new CommentServiceImpl(commentRepository, commentConverter);
    }

    @Test
    public void getAllComments_returnComments() {
        List<Comment> commentList = getCommentList();
        Comment comment = getPersistComment();
        CommentDTO commentDTO = getPersistCommentDTO();
        when(commentRepository.findAll()).thenReturn(commentList);
        when(commentConverter.getDTOFromObject(comment)).thenReturn(commentDTO);
        List<CommentDTO> returnedComments = commentService.getAllComments();
        verify(commentRepository, times(1)).findAll();

        for (int i = 0; i < returnedComments.size(); i++) {
            getAssertion(returnedComments.get(i), commentList.get(i));
        }
    }

    @Test
    public void addComment_returnComments() {
        Comment comment = getComment();
        Comment persistComment = getPersistComment();
        CommentDTO commentDTO = getCommentDTO();

        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentConverter.getObjectFromDTO(commentDTO)).thenReturn(comment);
        when(commentConverter.getDTOFromObject(comment)).thenReturn(commentDTO);
        CommentDTO returnedComment = commentService.addComment(commentDTO);
        verify(commentRepository, times(1)).save(comment);

        getAssertion(returnedComment, persistComment);
    }

    @Test
    public void getPageableCommentsByNewsId_returnComments() {
        Comment comment = getPersistComment();
        CommentDTO commentDTO = getPersistCommentDTO();

        List<Comment> commentList = getCommentList();
        Long newsId=1L;
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");

        when(commentRepository.findAllByNewsId(pageable, newsId)).thenReturn(commentList);
        when(commentConverter.getDTOFromObject(comment)).thenReturn(commentDTO);
        List<CommentDTO> returnedComment = commentService.getPageableCommentsByNewsId(pageable, newsId);
        verify(commentRepository, times(1)).findAllByNewsId(pageable, newsId);
        System.out.println("returned:"+returnedComment);
        System.out.println("expect:"+commentList);
        for (int i = 0; i < returnedComment.size(); i++) {
            getAssertion(returnedComment.get(i), commentList.get(i));
        }
    }

    private void getAssertion(CommentDTO returnedComments, Comment comment) {
        assertThat(returnedComments).isNotNull();
        assertThat(returnedComments.getDate()).isEqualTo(comment.getDate());
        assertThat(returnedComments.getText()).isEqualTo(comment.getText());
        assertThat(returnedComments.getUsername()).isEqualTo(comment.getUsername());
    }


    private CommentDTO getCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDate(alarmTime);
        commentDTO.setText("test text");
        commentDTO.setUsername("test username");

        return commentDTO;
    }

    private NewsDTO getNewsDTO() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setDate(alarmTime);
        newsDTO.setText("test text");
        newsDTO.setTitle("test title");
        return newsDTO;
    }

    private CommentDTO getPersistCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setDate(alarmTime);
        commentDTO.setText("test text");
        commentDTO.setUsername("test username");
        commentDTO.setNewsDTO(getPersistNewsDTO());
        return commentDTO;
    }

    private NewsDTO getPersistNewsDTO() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(1L);
        newsDTO.setDate(alarmTime);
        newsDTO.setText("test text");
        newsDTO.setTitle("test title");
        return newsDTO;
    }

    private List<CommentDTO> getPersistCommentListDTO() {
        List<CommentDTO> comments = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            CommentDTO commentDTO = getPersistCommentDTO();
            commentDTO.setId(i);
         //   commentDTO.setNewsDTO(getNewsDTO());
            comments.add(commentDTO);
        }
        return comments;
    }


    private Comment getComment() {
        Comment comment = new Comment();
        comment.setDate(alarmTime);
        comment.setText("test text");
        comment.setUsername("test username");
        return comment;
    }

    private News getNews() {
        News news = new News();
        news.setDate(alarmTime);
        news.setText("test text");
        news.setTitle("test title");
        return news;
    }

    private Comment getPersistComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setDate(alarmTime);
        comment.setText("test text");
        comment.setUsername("test username");
        comment.setNews(getPersistNews());
        return comment;
    }

    private News getPersistNews() {
        News news = new News();
        news.setId(1L);
        news.setDate(alarmTime);
        news.setText("test text");
        news.setTitle("test title");
        return news;
    }

    private List<Comment> getPersistCommentList() {
        List<Comment> comments = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Comment comment = getPersistComment();
            comment.setId(i);
            comments.add(comment);
        }
        return comments;
    }

    private List<Comment> getCommentList() {
        List<Comment> comments = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Comment comment = getPersistComment();
         //   comment.setId(i);
            comments.add(comment);
        }
        return comments;
    }
}
