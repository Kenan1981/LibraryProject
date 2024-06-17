package com.tpe.service;

import com.tpe.payload.response.BookResponse;
import com.tpe.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<Page<BookResponse>> getAllBookByPage(Long category_id, Long author_id, Long publisher_id,
                                                     int page, int size, String sort, String type) {
        return null;
    }
}