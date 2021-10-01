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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-integration.properties")
public class CommentControllerITTest {

    private String date = "2021-11-11T12:12:12";
    private LocalDateTime dateTime = LocalDateTime.parse(date);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/data.sql")
    public void getAllComments_returnAllComments() throws Exception {
        mockMvc.perform(get("/comment"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].username", is("username")).exists())
                .andExpect(jsonPath("$[0].newsDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].newsDTO.title", is("title")).exists())
                .andExpect(jsonPath("$[0].newsDTO.text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void getPageableCommentsByNewsId_returnComments() throws Exception {
        mockMvc.perform(get("/comment/1")
                .param("pageNumber", String.valueOf(0))
                .param("pageSize", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].username", is("username")).exists())
                .andExpect(jsonPath("$[0].newsDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].newsDTO.title", is("title")).exists())
                .andExpect(jsonPath("$[0].newsDTO.text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void addComment_returnComments() throws Exception {
        CommentDTO commentDTO = getCommentDTO();
        String content = objectMapper.writeValueAsString(commentDTO);

        mockMvc.perform(post("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].username", is("username")).exists())
                .andExpect(jsonPath("$[0].newsDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].newsDTO.title", is("title")).exists())
                .andExpect(jsonPath("$[0].newsDTO.text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void editComment_returnComments() throws Exception {
        CommentDTO commentDTO = getCommentDTO();
        String content = objectMapper.writeValueAsString(commentDTO);

        mockMvc.perform(put("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].username", is("username")).exists())
                .andExpect(jsonPath("$[0].newsDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].newsDTO.title", is("title")).exists())
                .andExpect(jsonPath("$[0].newsDTO.text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void deleteComment_returnComments() throws Exception {
        CommentDTO commentDTO = getCommentDTO();
        String content = objectMapper.writeValueAsString(commentDTO);

        mockMvc.perform(delete("/comment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].username", is("username")).exists())
                .andExpect(jsonPath("$[0].newsDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].newsDTO.title", is("title")).exists())
                .andExpect(jsonPath("$[0].newsDTO.text", is("text")).exists());
    }

    @Test
    @Sql("/data.sql")
    public void deleteCommentById_returnComments() throws Exception {

        mockMvc.perform(delete("/comment/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].text", is("text")).exists())
                .andExpect(jsonPath("$[0].username", is("username")).exists())
                .andExpect(jsonPath("$[0].newsDTO.date", is("2021-11-11 12:12:12")).exists())
                .andExpect(jsonPath("$[0].newsDTO.title", is("title")).exists())
                .andExpect(jsonPath("$[0].newsDTO.text", is("text")).exists());
    }

    private CommentDTO getCommentDTO() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setDate(dateTime);
        commentDTO.setText("test text");
        commentDTO.setUsername("test username");

        return commentDTO;
    }

}
