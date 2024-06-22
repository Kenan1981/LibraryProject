package com.tpe.controller.business;

import com.tpe.payload.request.BookRequest;
import com.tpe.payload.response.BookResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.service.business.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")

public class BookController {

    private final BookService bookService;



    //1.ENDPOINT GET
    @GetMapping("/books")  //http://localhost/8080/books?q=sefiller&cat=4&author=34&publisher=42&page=1&size=10&sort=name&type=asc

    public Page<BookResponse> getAllBookByPage(

            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long catId,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long publisherID,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type){

        // return bookService.getAllBookByPage(category_id,author_id, publisher_id,page,size,sort,type);
        return bookService.getAllBookByPage(q, catId, authorId, publisherID, page, size, sort, type);
    }








    //2. ENDPOINT GET
    @GetMapping("/{bookId}") //http://localhost/8080/books
    @PreAuthorize("hasAnyAuthority( 'ADMIN')")
    public List<BookResponse> getAll() {
        return bookService.getAll();
    }








    //3: ENDPOINT POST
    @PostMapping ("/books")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest){

        return bookService.createBook(bookRequest);

    }







    //4.ENDPOINT PUT

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/books/{id}") //http://localhost:8080//books/5
    public ResponseEntity<BookResponse> updateBookById(@PathVariable Long id,
                                                       @RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(bookService.updateBookById(id, bookRequest));
    }





    //5.ENDPOINT DELETE

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/books/{id}") // http://localhost:8080/meet/2
    public ResponseMessage deleteBook(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return bookService.deleteBook(id, httpServletRequest);
    }






}
