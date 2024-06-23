
package com.tpe.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tpe.entity.concretes.business.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublisherResponse {

    private Long id;
    private String name;
    private Boolean builtIn;
    private List<Book> books;

}
