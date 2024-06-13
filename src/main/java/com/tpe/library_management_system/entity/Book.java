package com.tpe.library_management_system.entity;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Entity
@Data
@NoArgsConstructor
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 80)
    private String name;

    @NotNull
    @Pattern(regexp = "\\d{3} \\d{2} \\d{5} \\d{2} \\d")
    @Size(min = 17, max = 17)
    private String isbn;

    @NotNull
    private Integer pageCount;

    @NotNull
    private Long authorId;//todo ilişki kontrol et

    @NotNull
    private Long publisherId;//todo ilişki kontrol et

    @Pattern(regexp = "\\d{4}")
    @NotNull
    private Integer publishDate;

    @NotNull
    private Long categoryId;//todo ilişki kontrol et

    @Nullable
    private File image;

    @NotNull
    private Boolean loanable = true;

    @NotNull
    @Pattern(regexp = "[A-Z]{2} \\d{3}")
    @Size(min = 6, max = 6)
    private String shelfCode;

    @NotNull
    private Boolean active = true;

    @NotNull
    private Boolean featured = false;

    @NotNull
    private LocalDateTime createDate;

    @NotNull
    private Boolean builtIn = false;


    @OneToMany(mappedBy = "book")
    private List<Loan> loans;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
