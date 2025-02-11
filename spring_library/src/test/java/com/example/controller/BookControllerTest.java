package com.example.controller;

import com.example.model.book.BookResponse;
import com.example.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks_ShouldReturnPaginatedResults() throws Exception {
        List<BookResponse> bookList = List.of(
                new BookResponse("Book Title 1", LocalDate.of(1998, 11, 12), null),
                new BookResponse("Book Title 2", LocalDate.of(1999, 11, 12), null)
        );
        Page<BookResponse> bookPage = new PageImpl<>(bookList, PageRequest.of(0, 2), 2);
        when(bookService.getAllBooks(PageRequest.of(0, 2))).thenReturn(bookPage);

        mockMvc.perform(get("/books?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void getAllBooks_ShouldReturnPaginatedResultsWithSort() throws Exception {
        List<BookResponse> bookList = List.of(
                new BookResponse("Book 1", LocalDate.of(1998, 11, 12), null),
                new BookResponse("The Book 2", LocalDate.of(1999, 11, 12), null)
        );
        Page<BookResponse> bookPage = new PageImpl<>(bookList, PageRequest.of(0, 2, Sort.by("title")), 2);
        when(bookService.getAllBooks(PageRequest.of(0, 2, Sort.by("title")))).thenReturn(bookPage);

        mockMvc.perform(get("/books?page=0&size=2&sort=title")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Book 1"))
                .andExpect(jsonPath("$.content[1].title").value("The Book 2"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }
}
