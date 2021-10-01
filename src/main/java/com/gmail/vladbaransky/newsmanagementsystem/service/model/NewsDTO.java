package com.gmail.vladbaransky.newsmanagementsystem.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.vladbaransky.newsmanagementsystem.util.JsonDateDeserializer;
import com.gmail.vladbaransky.newsmanagementsystem.util.JsonDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NewsDTO implements Serializable {
    private Long id;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String title;
    private String text;
}