package com.tpe.library_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    @Size(min = 2, max = 80)
    private String name;

    @NotNull
    private Boolean builtIn = false;

    @NotNull
    private int sequence;//todo @Prepersist


    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
