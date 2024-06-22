package com.tpe.payload.request;

import com.tpe.entity.concretes.user.Role;
import com.tpe.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRequest {

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @Size(min = 10, max = 100)
    private String address;

    @NotNull
    @Pattern(regexp = "\\d{3} \\d{3} \\d{4}")
    private String phone;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy MM dd")
    private LocalDate birthDate;

    @NotNull
    @Email
    @Size(min = 10, max = 80)
    private String email;

    @NotNull
    private String password;

    @NotNull(message = "Role is mandatory")
    private RoleType roleType;

    private Role role;

}