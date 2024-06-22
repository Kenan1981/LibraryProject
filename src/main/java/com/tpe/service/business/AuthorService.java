package com.tpe.service.business;

import com.tpe.entity.concretes.business.Author;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.mappers.AuthorMapper;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.messages.SuccessMessages;
import com.tpe.payload.request.AuthorRequest;
import com.tpe.payload.response.AuthorResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.repository.AuthorRepository;
import com.tpe.payload.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final PageableHelper pageableHelper;

    // Not: createAuthor *********************************************************************
    public ResponseMessage<AuthorResponse> saveAuthor(AuthorRequest authorRequest) {

        isAuthorExistByName(authorRequest.getName());
        Author savedAuthor = authorRepository.save(authorMapper.mapAuthorRequestToAuthor(authorRequest));

        return ResponseMessage.<AuthorResponse>builder()
                .object(authorMapper.mapAuthorToAuthorResponse(savedAuthor))
                .message(SuccessMessages.AUTHOR_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    private boolean isAuthorExistByName(String name) {
        boolean authorExist = authorRepository.existsAuthorByNameEqualsIgnoreCase(name);

        if(authorExist){
            throw new ConflictException(String.format(ErrorMessages.AUTHOR_ALREADY_EXIST_WITH_LESSON_NAME, name));
        }else return false;

    }

    // Not: getAuthorWithPage *********************************************************************
    public Page<AuthorResponse> findAuthorByName(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithPropertiesASC(page, size, sort, type);
        return authorRepository.findAll(pageable).map(authorMapper::mapAuthorToAuthorResponse);
    }

    // Not: getAuthorById
    public AuthorResponse getAuthorById(Long id) {
        Author author = isAuthorExist(id);
        return authorMapper.mapAuthorToAuthorResponse(author);
    }

    private Author isAuthorExist(Long id) {
        return authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.AUTHOR_NOT_FOUND_MESSAGE, id)));
    }


    // Not:authorUpdate *************************************************************************************
    public AuthorResponse updateAuthorById(Long id, AuthorRequest authorRequest) {
        Author author = isAuthorExistById(id);
        if(
                !(author.getName().equals(authorRequest.getName()))  &&
                        (authorRepository.existsAuthorByNameEqualsIgnoreCase(authorRequest.getName()))
        ){
            throw new ConflictException(String.format(ErrorMessages.AUTHOR_ALREADY_EXIST_WITH_LESSON_NAME, authorRequest.getName()));
        }

        Author updatedAuthor = authorMapper.mapAuthorRequestToAuthor(authorRequest);
        updatedAuthor.setBooks(author.getBooks());
        Author savedAuthor = authorRepository.save(updatedAuthor);

        return authorMapper.mapAuthorToAuthorResponse(savedAuthor);
    }

    private Author isAuthorExistById(Long id) {

        return authorRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.AUTHOR_NOT_FOUND_MESSAGE, id)));
    }

    // Not: deleteAuthorById **************************************************************
    public ResponseMessage<?> deleteAuthorById(Long id) {
        isAuthorExist(id);
        authorRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccessMessages.AUTHOR_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}