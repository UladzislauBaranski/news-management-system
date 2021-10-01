package com.gmail.vladbaransky.newsmanagementsystem.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.vladbaransky.newsmanagementsystem.util.JsonDateDeserializer;
import com.gmail.vladbaransky.newsmanagementsystem.util.JsonDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NewsCommentDTO {
    private Long id;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String title;
    private String text;
    private List<CommentDTO> commentsDTO = new ArrayList<>();
}
