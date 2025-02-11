package com.example.controller;

import com.example.exception.BookNotFoundException;
import com.example.model.book.BookResponse;
import com.example.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
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

    private List<BookResponse> createTestBooks() {
        return Arrays.asList(
                new BookResponse("Book 1", LocalDate.of(1987, 11, 11), null),
                new BookResponse("Book 2", LocalDate.of(1988, 11, 11), null),
                new BookResponse("Book 3", LocalDate.of(1989, 11, 11), null)
        );
    }

    @Test
    void getAllBooks_shouldReturnPaginatedResults() throws Exception {
        List<BookResponse> books = createTestBooks();
        Pageable pageable = PageRequest.of(0, 2);
        Page<BookResponse> mockPage = new PageImpl<>(books.subList(0, 2), pageable, books.size());

        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/books?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Book 1"))
                .andExpect(jsonPath("$.content[0].realiseDate").value("1987-11-11"))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }
    @Test
    void getBook_shouldReturnNotFound() throws Exception {
        when(bookService.getBookById(anyLong())).thenThrow(BookNotFoundException.class);

        mockMvc.perform(get("/books/999"))
                .andExpect(status().isNotFound());
    }
}