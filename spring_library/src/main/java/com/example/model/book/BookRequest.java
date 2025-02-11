package com.example.model.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String authorName;
    @PastOrPresent
    private LocalDate realiseDate;
}
