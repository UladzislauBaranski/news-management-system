package com.gmail.vladbaransky.newsmanagementsystem.comtroller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.newsmanagementsystem.controller.CommentController;
import com.gmail.vladbaransky.newsmanagementsystem.controller.NewsController;
import com.gmail.vladbaransky.newsmanagementsystem.service.CommentService;
import com.gmail.vladbaransky.newsmanagementsystem.service.NewsCommentService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NewsController.class)
@AutoConfigureMockMvc
public class NewsControllerTest {
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
    private NewsService newsService;

    @MockBean
    private NewsCommentService newsCommentService;

    @MockBean
    private CommentService commentService;


    //------------------getAllNews---------------------------------
    @Test
    void getAllNews_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news")).andExpect(status().isOk());
    }

    @Test
    void getAllNews_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/news"))
                .andExpect(status().isOk());
        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void getAllNews_returnComments() throws Exception {
        List<NewsDTO> news = getPersistNewsListDTO();
        when(newsService.getAllNews()).thenReturn(news);
        MvcResult result = mockMvc.perform(get("/news")).andReturn();
        verify(newsService, times(1)).getAllNews();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedContent = objectMapper.writeValueAsString(news);
        Assertions.assertThat(returnedContent).isEqualTo(expectedContent);
    }

    //------------------getNewsById-------------------
    @Test
    void getNewsById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news/by/1")).andExpect(status().isOk());
    }

    @Test
    void getNewsById_returnStatusBadRequest() throws Exception {
        mockMvc.perform(get("/news/by/invalidVariable")).andExpect(status().isBadRequest());

    }

    @Test
    void getNewsById_callBusinessLogic() throws Exception {
        Long newsId = 1L;
        mockMvc.perform(get("/news/by/1"))
                .andExpect(status().isOk());
        verify(newsService, times(1)).getNewsById(newsId);
    }

    @Test
    void getNewsById_returnNews() throws Exception {
        Long newsId = 1L;
        NewsDTO persistNewsDTO = getPersistNewsDTO();
        when(newsService.getNewsById(newsId)).thenReturn(persistNewsDTO);
        MvcResult result = mockMvc.perform(get("/news/by/1")).andReturn();
        verify(newsService, times(1)).getNewsById(newsId);

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------getNewsAndPageableCommentByNewsId---------------------------------
/*    @Test
    void getNewsAndPageableCommentByNewsId_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news/1")).andExpect(status().isOk());
    }

    @Test
    void getNewsAndPageableCommentByNewsIdWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news/1")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk());
    }*/

  /*  @Test
    void getNewsAndPageableCommentByNewsId_callBusinessLogic() throws Exception {
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        mockMvc.perform(get("/news")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(newsService, times(1)).
                getNewsByPage(eq(pageable));
    }

    @Test
    void getNewsAndPageableCommentByNewsId_returnNewsByPage() throws Exception {
        List<NewsDTO> persistNewsListDTO = getPersistNewsListDTO();
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");

        when(newsService.getNewsByPage(pageable)).thenReturn(persistNewsListDTO);
        MvcResult result = mockMvc.perform(get("/news/by-page")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andReturn();
        verify(newsService, times(1)).
                getNewsByPage(eq(pageable));

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }
*/
    //------------------getNewsByPage---------------------------------
    @Test
    void getNewsByPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news/by-page")).andExpect(status().isOk());
    }

    @Test
    void getNewsByPageWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/news/by-page")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getNewsByPage_callBusinessLogic() throws Exception {
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        mockMvc.perform(get("/news/by-page")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(newsService, times(1)).
                getNewsByPage(eq(pageable));
    }

    @Test
    void getNewsByPage_returnNewsByPage() throws Exception {
        List<NewsDTO> persistNewsListDTO = getPersistNewsListDTO();
        Pageable pageable =
                PageRequest.of(0, 1, Sort.Direction.DESC, "id");

        when(newsService.getNewsByPage(pageable)).thenReturn(persistNewsListDTO);
        MvcResult result = mockMvc.perform(get("/news/by-page")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andReturn();
        verify(newsService, times(1)).
                getNewsByPage(eq(pageable));

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(persistNewsListDTO);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //------------------addNews---------------------------------
    @Test
    void addNews_returnStatusOk() throws Exception {
        NewsDTO newsDTO = getNewsDTO();

        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

   /* @Test
    void addNews_returnStatusBadRequest() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest());
    }*/

    @Test
    void addNews_callBusinessLogic() throws Exception {
        NewsDTO newsDTO = getNewsDTO();

        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

        verify(newsService, times(1)).addNews(eq(newsDTO));
        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void addNews_returnComments() throws Exception {
        NewsDTO newsDTO = getNewsDTO();

        NewsDTO persistNewsDTO = getPersistNewsDTO();


        String content = objectMapper.writeValueAsString(newsDTO);
        when(newsService.addNews(newsDTO)).thenReturn(persistNewsDTO);
        when(newsService.getAllNews()).thenReturn(getPersistNewsListDTO());

        MvcResult result = mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();

        verify(newsService, times(1)).addNews(eq(newsDTO));
        verify(newsService, times(1)).getAllNews();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(getPersistNewsListDTO());

        Assertions.assertThat(returnedContent).isEqualTo(expectedReturnedContent);
    }

    //------------------editNews---------------------------------
    @Test
    void editNews_returnStatusOk() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(1L);

        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

   /* @Test
    void editNews_returnStatusBadRequest() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest());
    }*/

    @Test
    void editNews_callBusinessLogic() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(1L);
        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(put("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

        verify(newsService, times(1)).addNews(eq(newsDTO));
        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void editNews_returnComments() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(1L);
        NewsDTO persistNewsDTO = getPersistNewsDTO();

        String content = objectMapper.writeValueAsString(newsDTO);
        when(newsService.addNews(newsDTO)).thenReturn(persistNewsDTO);
        when(newsService.getAllNews()).thenReturn(getPersistNewsListDTO());

        MvcResult result = mockMvc.perform(put("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();

        verify(newsService, times(1)).addNews(eq(newsDTO));
        verify(newsService, times(1)).getAllNews();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(getPersistNewsListDTO());

        Assertions.assertThat(returnedContent).isEqualTo(expectedReturnedContent);
    }

    //------------------deleteNews---------------------------------
    @Test
    void deleteNews_returnStatusOk() throws Exception {
        Long id = 1L;
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(id);
        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(delete("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment_returnStatusBadRequest() throws Exception {
        Long id = 1L;
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(id);
        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(delete("/news/invalidVariable")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteComment_callBusinessLogic() throws Exception {
        Long id = 1L;
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(id);

        String content = objectMapper.writeValueAsString(newsDTO);
        mockMvc.perform(delete("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());

        verify(newsService, times(1)).deleteNews(eq(newsDTO));
        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void deleteComment_returnComments() throws Exception {
        Long id = 1L;
        NewsDTO newsDTO = getNewsDTO();
        newsDTO.setId(id);

        String content = objectMapper.writeValueAsString(newsDTO);
        when(newsService.getAllNews()).thenReturn(getPersistNewsListDTO());

        MvcResult result = mockMvc.perform(delete("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();

        verify(newsService, times(1)).deleteNews(eq(newsDTO));
        verify(newsService, times(1)).getAllNews();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(getPersistNewsListDTO());

        Assertions.assertThat(returnedContent).isEqualTo(expectedReturnedContent);
    }

    //------------------deleteNewsById-------------------
    @Test
    void deleteNewsById_returnStatusOk() throws Exception {
        mockMvc.perform(delete("/news/1")).andExpect(status().isOk());
    }

    @Test
    void deleteNewsById_returnStatusBadRequest() throws Exception {
        mockMvc.perform(delete("/news/invalidVariable")).andExpect(status().isBadRequest());

    }

    @Test
    void deleteNewsById_callBusinessLogic() throws Exception {
        Long commentId = 1L;
        mockMvc.perform(delete("/news/1"))
                .andExpect(status().isOk());
        verify(newsService, times(1)).deleteNewsById(commentId);
        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void deleteNewsById_returnComments() throws Exception {
        List<NewsDTO> news = getPersistNewsListDTO();
        when(newsService.getAllNews()).thenReturn(news);
        MvcResult result = mockMvc.perform(delete("/news/1")).andReturn();
        verify(newsService, times(1)).getAllNews();

        String returnedContent = result.getResponse().getContentAsString();
        String expectedReturnedContent = objectMapper.writeValueAsString(news);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
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
}
