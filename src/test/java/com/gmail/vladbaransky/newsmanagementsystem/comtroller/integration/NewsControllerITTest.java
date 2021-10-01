package com.gmail.vladbaransky.newsmanagementsystem.comtroller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.NewsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-integration.properties")
public class NewsControllerITTest {

    private String date = "2021-11-11T12:12:12";
    private LocalDateTime dateTime = LocalDateTime.parse(date);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/data.sql")
    public void getAllNews_returnAllNews() throws Exception {
        mockMvc.perform(get("/news"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndPageableCommentByNewsId_returnNews() throws Exception {
        mockMvc.perform(get("/news/2")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
               /* .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].commentDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].commentDTO.text", is("text")).exists())
                .andExpect(jsonPath("$[0].commentDTO.username", is("username")).exists());*/
    }

    @Test
    @Sql("/data.sql")
    public void addNews_returnNews() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        String content = objectMapper.writeValueAsString(newsDTO);

        mockMvc.perform(post("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void editNews_returnNews() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        String content = objectMapper.writeValueAsString(newsDTO);

        mockMvc.perform(put("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void deleteNews_returnNews() throws Exception {
        NewsDTO newsDTO = getNewsDTO();
        String content = objectMapper.writeValueAsString(newsDTO);

        mockMvc.perform(delete("/news")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void deleteNewsById_returnNews() throws Exception {
        mockMvc.perform(delete("/news/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    private NewsDTO getNewsDTO() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setDate(dateTime);
        newsDTO.setText("test text");
        newsDTO.setTitle("test title");
        return newsDTO;
    }

}
