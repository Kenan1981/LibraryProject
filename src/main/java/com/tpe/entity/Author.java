package com.tpe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "authors")

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Author name can not be null")
    @NotBlank(message = "Author name can not be white space")
    @Size(min = 4, max=70, message = "Author name '${valudatedValue}' must be between {min} and {max} chars")
    @Column(nullable = false, length = 70)
    private String name;

    @Column(nullable = false)
    @NotNull
    private Boolean builtIn=false;

    private String deneme123;



}
