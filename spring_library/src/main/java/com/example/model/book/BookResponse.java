package com.example.model.book;

import com.example.model.author.AuthorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookResponse {
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate realiseDate;
    private AuthorResponse author;
}
