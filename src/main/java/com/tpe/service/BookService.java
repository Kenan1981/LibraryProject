package com.tpe.service;

import com.tpe.entity.concretes.business.Book;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.mappers.BookMappers;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.messages.SuccessMessages;
import com.tpe.payload.request.BookRequest;
import com.tpe.payload.response.BookResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.repository.BookRepository;
import com.tpe.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMappers bookMappers;
    private  final PageableHelper pageableHelper;





    //GET 1
    public Page<BookResponse> getAllBookByPage(String q, Long catId, Long authorId, Long publisherID, int page,
                                                      int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(q,catId,authorId,publisherID,page, size, sort, type);
        return bookRepository.findAll(pageable).map(bookMappers::mapBookToBookResponse);
    }






    //GET 2
    public List<BookResponse> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMappers::mapBookToBookResponse)
                .collect(Collectors.toList());
    }




    //YARDIMCI METHOD
    private Book isBookExistById(Long id){
        return  bookRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.BOOK_NOT_FOUND_MESSAGE, id)));
    }




    //POST
    public ResponseMessage<BookResponse> createBook(BookRequest bookRequest) {
        isBookExistByBookName(bookRequest.getName());

        Book savedBook = bookRepository.save(bookMappers.mapBookRequestToBook(bookRequest));

        return ResponseMessage.<BookResponse>builder()
                .object(bookMappers.mapBookToBookResponse(savedBook))
                .message(SuccessMessages.BOOK_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }



    //YARDIMCI METHOD
    private boolean isBookExistByBookName(String name){
        boolean bookExist = bookRepository.existsByNameIgnoreCase(name);

        if(bookExist){
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXIST_WITH_BOOK_NAME, name));
        } else return false;
    }





    //PUT
    public BookResponse updateBookById(Long id, BookRequest bookRequest) {
        Book book1 = isBookExistById(id);
        if(
                !(book1.getName().equals(bookRequest.getName()))   &&
                        (bookRepository.existsByNameIgnoreCase(bookRequest.getName()))
        ){
            throw new ConflictException(String.format(ErrorMessages.BOOK_ALREADY_EXIST_WITH_BOOK_NAME,
                    bookRequest.getName()));
        }
        Book updatedBook = bookMappers.mapBookRequestToUpdatedBook(id, bookRequest);
        updatedBook.setName(book1.getName());
        Book savedBook = bookRepository.save(updatedBook);

        return bookMappers.mapBookToBookResponse(savedBook);
    }



    //DELETE
    public ResponseMessage deleteBook(Long id, HttpServletRequest httpServletRequest) {

        isBookExistById(id);
        bookRepository.deleteById(id);   //??? repository bak?
        return ResponseMessage.builder()
                .message(SuccessMessages.BOOK_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }



}
