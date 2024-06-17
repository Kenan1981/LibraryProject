package com.tpe.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.entity.concretes.business.Author;
import com.tpe.entity.concretes.business.Category;
import com.tpe.entity.concretes.business.Loan;
import com.tpe.entity.concretes.business.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookResponse {



    private Long id;

    private String bookName;

    private String isbn;

    private int pageCount;

    private Long authorId;

    private Long publisherId;

    private int publishDate;

    private Long categoryId;

    private File image;   //bunu ilk defa görüyruz

    private boolean loanable=true;

    private String shelfCode;

    private boolean active=true;

    private boolean featured=false;

    private LocalDateTime createDate; //? dogru mu

    //  private boolean builtIn=false;

    private Author authorName;

    private Category categoryName;

    private Publisher publisherName;

    private List<Loan> loans;


}