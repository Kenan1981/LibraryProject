package com.tpe.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse  {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private String password;
}