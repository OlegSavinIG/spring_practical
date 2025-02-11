package com.example.service;

import com.example.model.book.BookRequest;
import com.example.model.book.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> getAllBooks(Pageable pageable);

    BookResponse getBookById(Long bookId);

    void createBook(BookRequest bookRequest);

    void updateBook(Long bookId, BookRequest bookRequest);

    void deleteBook(Long bookId);
}
