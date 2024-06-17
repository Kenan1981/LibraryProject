package com.tpe.controller;

import com.tpe.payload.response.BookResponse;
import com.tpe.service.BookService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")

public class BookController {

    private final BookService bookService;

    // @PreAuthorize("hasAnyAuthority('ADMIN','MEMBER','ANONYMOUS','EMPLOYEE')")
    @GetMapping("/books")  //http://
    public List<Page<BookResponse>> getAllBookByPage(

            @RequestParam(name = "category_id")Long category_id,  // ilk 3 satir ??
            @RequestParam(name = "author_id")Long author_id,
            @RequestParam(name = "publisher_id")Long publisher_id,

            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type){

        return bookService.getAllBookByPage(category_id,author_id, publisher_id,page,size,sort,type);
    }







}