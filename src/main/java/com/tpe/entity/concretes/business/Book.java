package com.tpe.entity.concretes.business; // checked

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_books")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 80)
    private String name;


    @NotNull
    @Size(min = 17, max = 17)
    @Pattern(regexp = "\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d", message = "Invalid ISBN format. Correct format: 999-99-99999-99")
    private String isbn;


    @Column(name = "page_count")
    private int pageCount;


    @NotNull
    @Column(name = "author_id")
    private Long authorId;


    @NotNull
    @Column(name = "publisher_id")
    private Long publisherId;


    @Min(value = 1000, message = "Publish year must be greater than or equal to 1000")
    @Max(value = 9999, message = "Publish year must be less than or equal to 9999")
    private int publishDate;


    @NotNull
    @Column(name = "category_id")
    private Long categoryId;


    //private File image;   //bunu ilk defa görüyruz; VERITABANINDA SAKLMAK ICIN UYGUN DEGILMIS
    private String imageUrl;  // File yerine String URL


    @NotNull
    private boolean loanable=true;


    @Column(nullable = false, length = 6) //gereksiz mi?
    @NotNull
    @Pattern(regexp = "[A-Z]{2}-\\d{3}", message = "Invalid shelf code format. Correct format: AA-999")
    private String shelfCode;


    @NotNull
    private boolean active=true;


    @NotNull
    private boolean featured;


    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss") //@PREPERSIST
    private LocalDateTime createDate;


    @NotNull
    private boolean builtIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = false, updatable = false)
    private Author author;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false, insertable = false, updatable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false, insertable = false, updatable = false)
    private Publisher publisher;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Loan> loans; // ifadesi gereksiz yere yüklemeleri artırabilir. Eager loading daha iyi olabilir.


}