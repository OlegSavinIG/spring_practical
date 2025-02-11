package com.example.service;

import com.example.exception.AuthorNotFoundException;
import com.example.exception.BookNotFoundException;
import com.example.model.author.Author;
import com.example.model.book.Book;
import com.example.model.book.BookRequest;
import com.example.model.book.BookResponse;
import com.example.model.book.mapper.BookMapper;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(BookMapper::toResponse);
    }

    public BookResponse getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .map(BookMapper::toResponse)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found"));
    }

    public void createBook(BookRequest bookRequest) {
        Author author = authorRepository.findByName(bookRequest.getAuthorName()).orElseThrow(
                () -> new AuthorNotFoundException("Author not found")
        );
        bookRepository.save(BookMapper.toEntity(bookRequest, author));
    }

    public void updateBook(Long bookId, BookRequest bookRequest) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException("Book not found");
        }
        Author author = authorRepository.findByName(bookRequest.getAuthorName()).orElseThrow(
                () -> new AuthorNotFoundException("Author not found")
        );
        Book book = bookRepository.findById(bookId).get();
        BookMapper.updateFromRequest(bookRequest, book, author);
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException("Book not found");
        }
        bookRepository.deleteById(bookId);
    }
}
