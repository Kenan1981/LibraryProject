package com.tpe.payload.request;

import com.tpe.entity.concretes.business.Author;
import com.tpe.entity.concretes.business.Category;
import com.tpe.entity.concretes.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookRequest {

    @NotNull(message = "Please enter Lesson name")
    @Size(min = 2, max = 80, message = "Lesson name should be at least 2 characters")
    private String bookName;


    @NotNull
    @Size(min = 17, max = 17)
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d", message = "Invalid ISBN format. Correct format: 999-99-99999-99")
    private String isbn;

    @NotNull(message = "Please enter author name")
    private Author authorName;

    @NotNull(message = "Please enter category name")
    private Category categoryName;


    @NotNull(message = "Please enter publisher name")
    private Publisher publisherName;


}