package com.tpe.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthorRequest {
    @NotNull(message = "Please enter Author name")
    @Size(min = 4, max = 70, message = "Author name should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Author name must consist of the characters .")
    private String name;

//    @NotNull(message = "")// ??????? Olamaz diye dusunuyorum
//    private Boolean builtIn=false;
}