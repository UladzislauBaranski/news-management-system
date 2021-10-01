package com.gmail.vladbaransky.newsmanagementsystem.comtroller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.newsmanagementsystem.controller.CommentController;
import com.gmail.vladbaransky.newsmanagementsystem.controller.NewsCommentController;
import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsService;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsCommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NewsCommentController.class)
@AutoConfigureMockMvc
public class NewsCommentControllerTest {
    private String time = "2021-08-09T12:12:12";

    private LocalDateTime alarmTime = LocalDateTime.parse(time);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NewsCommentService newsCommentService;


    //------------------getNewsAndCommentById---------------------------------
    @Test
    void getNewsAndCommentById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/1")).andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentById_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/invalidVariable")).andExpect(status().isBadRequest());

    }

    @Test
    void getNewsAndCommentById_callBusinessLogic() throws Exception {
        Long newsId = 1L;
        mockMvc.perform(get("/news-comment/1"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentById(newsId);
    }

    @Test
    void getNewsAndCommentById_returnNewsAndComment() throws Exception {
        Long newsId = 1L;
        NewsCommentDTO persistNewsCommentDTO = getPersistNewsCommentDTO();

        when(newsCommentService.getNewsAndCommentById(newsId)).thenReturn(persistNewsCommentDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/1")).andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentById(newsId);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndCommentByNewsDate---------------------------------
    @Test
    void getNewsAndCommentByNewsDateWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-date")
                .param("date", "2021-08-09 12:12:12"))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentByNewsDateWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-date")
                .param("date", "invalid param"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNewsAndCommentByNewsDate_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-date")
                .param("date", "2021-08-09 12:12:12"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentByNewsDate(alarmTime);
    }

    @Test
    void getNewsAndCommentByNewsDate_returnNewsAndComment() throws Exception {
        List<NewsCommentDTO> persistNewsCommentListDTO = getPersistNewsCommentListDTO();

        when(newsCommentService.getNewsAndCommentByNewsDate(alarmTime)).thenReturn(persistNewsCommentListDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/by-news-date")
                .param("date", "2021-08-09 12:12:12"))
                .andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentByNewsDate(alarmTime);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndCommentByNewsTitle---------------------------------
    @Test
    void getNewsAndCommentByNewsTitleWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-title")
                .param("title", "test title"))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentByNewsTitleWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-title")
                .param("titleInvalid", "test title"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNewsAndCommentByNewsTitle_callBusinessLogic() throws Exception {
        String title = "test title";
        mockMvc.perform(get("/news-comment/by-news-title")
                .param("title", "test title"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentByNewsTitle(title);
    }

    @Test
    void getNewsAndCommentByNewsTitle_returnNewsAndComment() throws Exception {
        String title = "test title";
        List<NewsCommentDTO> persistNewsCommentListDTO = getPersistNewsCommentListDTO();

        when(newsCommentService.getNewsAndCommentByNewsTitle(title)).thenReturn(persistNewsCommentListDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/by-news-title")
                .param("title", "test title"))
                .andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentByNewsTitle(title);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndCommentByNewsText---------------------------------
    @Test
    void getNewsAndCommentByNewsTextWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-text")
                .param("text", "test text"))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentByNewsTextWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-text")
                .param("textInvalid", "test text"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNewsAndCommentByNewsText_callBusinessLogic() throws Exception {
        String text = "test text";
        mockMvc.perform(get("/news-comment/by-news-text")
                .param("text", "test text"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentByNewsText(text);
    }

    @Test
    void getNewsAndCommentByNewsText_returnNewsAndComment() throws Exception {
        String text = "test text";
        List<NewsCommentDTO> persistNewsCommentListDTO = getPersistNewsCommentListDTO();

        when(newsCommentService.getNewsAndCommentByNewsText(text)).thenReturn(persistNewsCommentListDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/by-news-text")
                .param("text", "test text"))
                .andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentByNewsText(text);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndCommentByCommentDate---------------------------------
    @Test
    void getNewsAndCommentByCommentDateWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-date")
                .param("date", "2021-08-09 12:12:12"))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentByCommentDateWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-date")
                .param("date", "invalid param"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNewsAndCommentByCommentDate_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-date")
                .param("date", "2021-08-09 12:12:12"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentByCommentDate(alarmTime);
    }

    @Test
    void getNewsAndCommentByCommentDate_returnNewsAndComment() throws Exception {
        List<NewsCommentDTO> persistNewsCommentListDTO = getPersistNewsCommentListDTO();

        when(newsCommentService.getNewsAndCommentByCommentDate(alarmTime)).thenReturn(persistNewsCommentListDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/by-comment-date")
                .param("date", "2021-08-09 12:12:12"))
                .andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentByCommentDate(alarmTime);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndCommentByCommentText---------------------------------
    @Test
    void getNewsAndCommentByCommentTextWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-text")
                .param("text", "test text"))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentByCommentTextWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-text")
                .param("textInvalid", "test text"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNewsAndCommentByCommentText_callBusinessLogic() throws Exception {
        String text = "test text";
        mockMvc.perform(get("/news-comment/by-comment-text")
                .param("text", "test text"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentByCommentText(text);
    }

    @Test
    void getNewsAndCommentByCommentText_returnNewsAndComment() throws Exception {
        String text = "test text";
        List<NewsCommentDTO> persistNewsCommentListDTO = getPersistNewsCommentListDTO();

        when(newsCommentService.getNewsAndCommentByCommentText(text)).thenReturn(persistNewsCommentListDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/by-comment-text")
                .param("text", "test text"))
                .andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentByCommentText(text);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndCommentByCommentUsername---------------------------------
    @Test
    void getNewsAndCommentByCommentUsernameWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-username")
                .param("username", "test username"))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsAndCommentByCommentUsernameWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-username")
                .param("usernameInvalid", "test username"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNewsAndCommentByCommentUsername_callBusinessLogic() throws Exception {
        String username = "test username";
        mockMvc.perform(get("/news-comment/by-comment-username")
                .param("username", "test username"))
                .andExpect(status().isOk());
        verify(newsCommentService, times(1)).getNewsAndCommentByCommentUsername(username);
    }

    @Test
    void getNewsAndCommentByCommentUsername_returnNewsAndComment() throws Exception {
        String username = "test username";
        List<NewsCommentDTO> persistNewsCommentListDTO = getPersistNewsCommentListDTO();

        when(newsCommentService.getNewsAndCommentByCommentUsername(username)).thenReturn(persistNewsCommentListDTO);
        MvcResult result = mockMvc.perform(get("/news-comment/by-comment-username")
                .param("username", "test username"))
                .andReturn();
        verify(newsCommentService, times(1)).getNewsAndCommentByCommentUsername(username);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsCommentListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
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


}