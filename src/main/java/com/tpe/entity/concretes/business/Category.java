package com.tpe.entity.concretes.business;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name must not be empty")
    @Size(min = 2, max = 80, message = "Name '${validatedValue}' must be between {min} and {max} chars")
    private String name;

    @NotNull(message = "builtIn must not be empty")
    private Boolean builtIn = false;


    // sequence alanına mevcut en büyük sequence alanından bir fazlasını varsayılan değer olarak atama
// işlemi repoda yapılacak
    @NotNull(message = "sequence must not be empty")
    private int sequence;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;





}
