package com.tpe.payload.mappers;

import com.tpe.entity.concretes.business.Book;
import com.tpe.payload.request.BookRequest;
import com.tpe.payload.response.BookResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class BookMappers {



    public Book mapBookRequestToBook(BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .authorId(bookRequest.getAuthorId())
                .publisherId(bookRequest.getPublisherId())
                .publishDate(bookRequest.getPublishDate())
                .categoryId(bookRequest.getCategoryId())
                .imageUrl(bookRequest.getImageUrl())
                .loanable(bookRequest.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.isActive())
                .featured(bookRequest.isFeatured())
                .createDate(LocalDateTime.now()) // Varsayılan olarak şu anın zamanı
                .builtIn(false) // Varsayılan olarak false
                .build();
    }




    public BookResponse mapBookToBookResponse(Book book) {
        return BookResponse.builder()
                .bookId(book.getId())
                .name(book.getName())
                .isbn(book.getIsbn())
                .authorId(book.getAuthorId())
                .publisherId(book.getPublisherId())
                .pageCount(book.getPageCount())
                .publishDate(book.getPublishDate())
                .categoryId(book.getCategoryId())
                .imageUrl(book.getImageUrl()) // Değiştirilmiş
                .loanable(book.isLoanable())
                .shelfCode(book.getShelfCode())
                .active(book.isActive())
                .featured(book.isFeatured())
                .createDate(book.getCreateDate())
                .loans(book.getLoans()) // Belki ileride lazım olabilir
                .build();
    }




    public Book mapBookRequestToUpdatedBook(Long id, BookRequest bookRequest) {
        return Book.builder()
                .id(id)
                .name(bookRequest.getName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .authorId(bookRequest.getAuthorId())
                .publisherId(bookRequest.getPublisherId())
                .publishDate(bookRequest.getPublishDate())
                .categoryId(bookRequest.getCategoryId())
                .imageUrl(bookRequest.getImageUrl())
                .loanable(bookRequest.isLoanable())
                .shelfCode(bookRequest.getShelfCode())
                .active(bookRequest.isActive())
                .featured(bookRequest.isFeatured())
                .createDate(LocalDateTime.now()) // Varsayılan olarak şu anın zamanı
                .builtIn(false) // Varsayılan olarak false
                .build();
    }
}