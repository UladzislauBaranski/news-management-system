package com.gmail.vladbaransky.newsmanagementsystem.comtroller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.newsmanagementsystem.controller.CommentController;
import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsService;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
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

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerTest {
    private String time = "2017-10-06T17:48:23";

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
    private CommentService commentService;

    @MockBean
    private NewsService newsService;


    //------------------getAllComments---------------------------------
    @Test
    void getAllComments_returnStatusOk() throws Exception {
        mockMvc.perform(get("/comment")).andExpect(status().isOk());
    }

    @Test
    void getAllComments_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/comment"))
                .andExpect(status().isOk());
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void getAllComments_returnComments() throws Exception {
        List<CommentDTO> comments = getPersistCommentListDTO();
        when(commentService.getAllComments()).thenReturn(comments);
        MvcResult result = mockMvc.perform(get("/comment")).andReturn();
        verify(commentService, times(1)).getAllComments();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(comments);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }


    //------------------getPageableCommentsByNewsId---------------------------------
    @Test
    void getPageableCommentsByNewsId_returnStatusOk() throws Exception {
        mockMvc.perform(get("/comment/1")).andExpect(status().isOk());
    }

    @Test
    void getPageableCommentsByNewsIdWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/comment/1")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getPageableCommentsByNewsIdWithInvalidParam_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/comment/invalidParam")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPageableCommentsByNewsId_callBusinessLogic() throws Exception {
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        mockMvc.perform(get("/comment/1")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(commentService, times(1)).
                getPageableCommentsByNewsId(eq(pageable), eq(1L));
    }

    @Test
    void getPageableCommentsByNewsId_returnPageableCommentsByNewsId() throws Exception {
        Long newsId = 1L;
        List<CommentDTO> commentsByPage = getPersistCommentListDTO();
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");

        when(commentService.getPageableCommentsByNewsId(pageable, newsId)).thenReturn(commentsByPage);
        MvcResult result = mockMvc.perform(get("/comment/1")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andReturn();
        verify(commentService, times(1)).
                getPageableCommentsByNewsId(eq(pageable), eq(1L));

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(commentsByPage);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }


    //------------------addComment---------------------------------
    @Test
    void addComment_returnStatusOk() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(getPersistNewsDTO());
        mockMvc.perform(post("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addComment_returnStatusBadRequest() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(getPersistNewsDTO());
        mockMvc.perform(post("/comment/invalidVariable")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addComment_callBusinessLogic() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        NewsDTO persistNewsDTO = getPersistNewsDTO();

        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(getPersistNewsDTO());

        mockMvc.perform(post("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

        verify(newsService, times(1)).getNewsById(eq(id));
        commentDTO.setNewsDTO(persistNewsDTO);
        verify(commentService, times(1)).addComment(eq(commentDTO));
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void addComment_returnComments() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        CommentDTO persistCommentDTO = getPersistCommentDTO();
        NewsDTO persistNewsDTO = getPersistNewsDTO();


        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(persistNewsDTO);
        commentDTO.setNewsDTO(persistNewsDTO);
        when(commentService.addComment(commentDTO)).thenReturn(persistCommentDTO);
        when(commentService.getAllComments()).thenReturn(getPersistCommentListDTO());

        MvcResult result = mockMvc.perform(post("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();

        verify(newsService, times(1)).getNewsById(eq(id));
        verify(commentService, times(1)).addComment(eq(commentDTO));
        verify(commentService, times(1)).getAllComments();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(getPersistCommentListDTO());

        Assertions.assertThat(returnedContent).isEqualTo(expectedReturnedContent);
    }


    //------------------editComment---------------------------------
    @Test
    void editComment_returnStatusOk() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        commentDTO.setId(id);
        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(getPersistNewsDTO());
        mockMvc.perform(put("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void editComment_returnStatusBadRequest() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        commentDTO.setId(id);
        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(getPersistNewsDTO());
        mockMvc.perform(put("/comment/invalidVariable")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    void editComment_callBusinessLogic() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        commentDTO.setId(id);
        NewsDTO persistNewsDTO = getPersistNewsDTO();

        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(persistNewsDTO);

        mockMvc.perform(put("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

        verify(newsService, times(1)).getNewsById(eq(id));
        commentDTO.setNewsDTO(persistNewsDTO);
        verify(commentService, times(1)).addComment(eq(commentDTO));
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void editComment_returnComments() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        commentDTO.setId(id);
        CommentDTO persistCommentDTO = getPersistCommentDTO();
        NewsDTO persistNewsDTO = getPersistNewsDTO();


        String content = objectMapper.writeValueAsString(commentDTO);
        when(newsService.getNewsById(id)).thenReturn(persistNewsDTO);
        commentDTO.setNewsDTO(persistNewsDTO);
        when(commentService.addComment(commentDTO)).thenReturn(persistCommentDTO);
        when(commentService.getAllComments()).thenReturn(getPersistCommentListDTO());

        MvcResult result = mockMvc.perform(put("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();

        verify(newsService, times(1)).getNewsById(eq(id));
        verify(commentService, times(1)).addComment(eq(commentDTO));
        verify(commentService, times(1)).getAllComments();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(getPersistCommentListDTO());

        Assertions.assertThat(returnedContent).isEqualTo(expectedReturnedContent);
    }


    //------------------deleteComment---------------------------------
    @Test
    void deleteComment_returnStatusOk() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getPersistCommentDTO();
        commentDTO.setId(id);
        String content = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(delete("/comment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment_returnStatusBadRequest() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        commentDTO.setId(id);
        String content = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(delete("/comment/invalidVariable")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteComment_callBusinessLogic() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        NewsDTO persistNewsDTO = getPersistNewsDTO();
        commentDTO.setId(id);
        commentDTO.setNewsDTO(persistNewsDTO);

        String content = objectMapper.writeValueAsString(commentDTO);
        mockMvc.perform(delete("/comment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

        verify(commentService, times(1)).deleteComment(eq(commentDTO));
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void deleteComment_returnComments() throws Exception {
        Long id = 1L;
        CommentDTO commentDTO = getCommentDTO();
        NewsDTO persistNewsDTO = getPersistNewsDTO();
        commentDTO.setId(id);
        commentDTO.setNewsDTO(persistNewsDTO);
        ;
        String content = objectMapper.writeValueAsString(commentDTO);
        when(commentService.getAllComments()).thenReturn(getPersistCommentListDTO());

        MvcResult result = mockMvc.perform(delete("/comment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();

        verify(commentService, times(1)).deleteComment(eq(commentDTO));
        verify(commentService, times(1)).getAllComments();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(getPersistCommentListDTO());

        Assertions.assertThat(returnedContent).isEqualTo(expectedReturnedContent);
    }

    //------------------deleteCommentById-------------------
    @Test
    void deleteCommentById_returnStatusOk() throws Exception {
        mockMvc.perform(delete("/comment/1")).andExpect(status().isOk());
    }

    @Test
    void deleteCommentById_returnStatusBadRequest() throws Exception {
        mockMvc.perform(delete("/comment/invalidVariable")).andExpect(status().isBadRequest());

    }

    @Test
    void deleteCommentById_callBusinessLogic() throws Exception {
        Long commentId = 1L;
        mockMvc.perform(delete("/comment/1"))
                .andExpect(status().isOk());
        verify(commentService, times(1)).deleteCommentById(commentId);
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void deleteCommentById_returnComments() throws Exception {
        List<CommentDTO> comments = getPersistCommentListDTO();
        when(commentService.getAllComments()).thenReturn(comments);
        MvcResult result = mockMvc.perform(delete("/comment/1")).andReturn();
        verify(commentService, times(1)).getAllComments();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(comments);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }


    private List<CommentDTO> getPersistCommentListDTO() {
        List<CommentDTO> comments = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            CommentDTO commentDTO = getPersistCommentDTO();
            commentDTO.setId(i);
            commentDTO.setNewsDTO(getNewsDTO());
            comments.add(commentDTO);
        }
        return comments;
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
}