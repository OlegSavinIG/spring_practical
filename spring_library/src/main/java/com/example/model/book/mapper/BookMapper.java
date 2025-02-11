package com.example.model.book.mapper;

import com.example.model.author.Author;
import com.example.model.author.AuthorResponse;
import com.example.model.book.Book;
import com.example.model.book.BookRequest;
import com.example.model.book.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public static Book toEntity(BookRequest request, Author author) {
        return Book.builder()
                .title(request.getTitle())
                .realiseDate(request.getRealiseDate())
                .author(author)
                .build();
    }

    public static BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(toAuthorResponse(book.getAuthor()))
                .build();
    }

    private static AuthorResponse toAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .name(author.getName())
                .build();
    }

    public static void updateFromRequest(BookRequest request, Book book, Author author) {
        book.setTitle(request.getTitle());
        book.setAuthor(author);
        book.setRealiseDate(request.getRealiseDate());
    }
}