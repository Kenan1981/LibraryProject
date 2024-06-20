package com.tpe.payload.request;


import com.tpe.entity.concretes.business.Author;
import com.tpe.entity.concretes.business.Category;
import com.tpe.entity.concretes.business.Loan;
import com.tpe.entity.concretes.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookRequest {

    @NotNull(message = "Please enter book name")
    @Size(min = 2, max = 80, message = "Book name should be at least 2 characters")
    private String name;

    @NotNull
    @Size(min = 17, max = 17)
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d", message = "Invalid ISBN format. Correct format: 999-99-99999-99")
    private String isbn;

    @NotNull(message = "Please enter author name")
    private Author author;

    @NotNull(message = "Please enter category name")
    private Category category;


    @NotNull(message = "Please enter publisher name")
    private Publisher publisher;

    private Long bookId;

    private int pageCount;

    private Long authorId;

    private Long publisherId;

    private int publishDate;

    private Long categoryId;

    private String imageUrl;  // private File image;   //bunu ilk defa görüyruz // // File yerine String URL

    private boolean loanable=true;

    private String shelfCode;

    private boolean active=true;

    private boolean featured=false;

    private LocalDateTime createDate; //? dogru mu

    private List<Loan> loans;



}