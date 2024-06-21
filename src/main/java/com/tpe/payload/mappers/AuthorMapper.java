package com.tpe.payload.mappers;

import com.tpe.entity.concretes.business.Author;
import com.tpe.payload.request.AuthorRequest;
import com.tpe.payload.response.AuthorResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthorMapper {

    public Author mapAuthorRequestToAuthor(AuthorRequest authorRequest ){
        return Author.builder()
                .name(authorRequest.getName())
                .build();
    }

    public AuthorResponse mapAuthorToAuthorResponse(Author author) {

        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }
}