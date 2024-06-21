package com.tpe.controller; // checked

import com.tpe.payload.request.AuthorRequest;
import com.tpe.payload.response.AuthorResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // Not: createAuthor *********************************************************************

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/publisher") // http://localhost:8080/authors/publisher + POST + JSON ?
    public ResponseMessage<AuthorResponse> saveAuthor(@RequestBody @Valid AuthorRequest authorRequest){
        return authorService.saveAuthor(authorRequest);
    }

    // Not: getAuthorWithPage *********************************************************************

    @GetMapping //http://localhost:8080/authors?page=1&size=10&sort=name&type=asc
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')")
    public Page<AuthorResponse> findAuthorByPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "20") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type
    ){
        return authorService.findAuthorByName(page, size, sort, type);
    }

    // Not:getLessonsById *********************************************************************

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ANONYMOUS')") // http://localhost:8080/authors/1  + GET
    public AuthorResponse getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    // Not:authorUpdate ***********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("update/{id}") // http://localhost:8080/authors/update/4
    private ResponseEntity<AuthorResponse> updateAuthorById(@PathVariable Long id,
                                                            @RequestBody AuthorRequest authorRequest){
        return ResponseEntity.ok(authorService.updateAuthorById(id, authorRequest));
    }

    // Not:deleteAuthorById **********************************************************************
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}") // http://localhost:8080/authors/delete/4  + JSON
    public ResponseMessage<?> deleteAuthorById(@PathVariable Long id){
        return authorService.deleteAuthorById(id);
    }





}

