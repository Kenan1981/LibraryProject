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

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookResponse {



    private Long bookId;

    private String name;

    private String isbn;

    private int pageCount;

    private Long authorId;

    private Long publisherId;

    private int publishDate;

    private Long categoryId;

    private String imageUrl;   // private File image;   //bunu ilk defa görüyruz //  // File yerine String URL

    private boolean loanable=true;

    private String shelfCode;

    private boolean active=true;

    private boolean featured=false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") //sonradan eklendi chat!!
    private LocalDateTime createDate; //? dogru mu

    //  private boolean builtIn=false;

    private Author author;

    private Category category;

    private Publisher publisher;

    private List<Loan> loans;






}