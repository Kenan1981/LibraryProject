package com.tpe.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "t_books")
@EqualsAndHashCode(of = "id")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 80)
    private String bookName;


    @NotNull
    @Size(min = 17, max = 17)
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d", message = "Invalid ISBN format. Correct format: 999-99-99999-99")
    private String isbn;


    @Column(name = "page_count", nullable = true)
    private int pageCount;


    @Column(name = "authors_id")
    @NotNull
    private Long authorId;


    @Column(name="publishers_id")
    @NotNull
    private Long publisherId;


    @Column(name = "publish_date", nullable = true) //?
    @Min(value = 1000, message = "Publish year must be greater than or equal to 1000")
    @Max(value = 9999, message = "Publish year must be less than or equal to 9999")
    private int publishDate;


    @Column(name="categories_id")
    @NotNull
    private Long categoryId;


    @Column(nullable = true)
    private File image;   //bunu ilk defa görüyruz


    @NotNull
    private boolean loanable=true;


    @Column(name = "shelf_code", nullable = false, length = 6)
    @NotNull
    @Pattern(regexp = "[A-Z]{2}-\\d{3}", message = "Invalid shelf code format. Correct format: AA-999")
    private String shelfCode;


    @NotNull
    private boolean active=true;


    @NotNull
    private boolean featured=false;


    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime createDate; //? dogru mu


    @NotNull
    private boolean builtIn=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisherName;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Loan> loans;


}