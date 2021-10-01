package com.gmail.vladbaransky.newsmanagementsystem.comtroller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.CommentDTO;
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
public class NewsCommentControllerITTest {

    private String date = "2021-11-11T12:12:12";
    private LocalDateTime dateTime = LocalDateTime.parse(date);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentById_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentByNewsDate_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-date")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("date", "2021-11-11 12:12:12"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentByNewsTitle_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-title")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("title", "title"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentByNewsText_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/by-news-text")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("text", "text"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentByCommentDate_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-date")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("date", "2021-11-11 12:12:12"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentByCommentText_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-text")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("text", "text"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getNewsAndCommentByCommentUsername_returnNewsComments() throws Exception {
        mockMvc.perform(get("/news-comment/by-comment-username")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("username", "username"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].title", is("title")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists());
    }

}
