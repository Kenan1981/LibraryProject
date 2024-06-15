package com.tpe.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpe.entity.concretes.business.Loan;
import com.tpe.entity.concretes.business.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "username", nullable = false, unique = true, length = 50)
//    private String username;

    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @Min(value = -2)
    @Max(value = 2)
    private Integer score = 0;

    @NotNull
    @Size(min = 10, max = 100)
    private String address;

    @NotNull
    @Pattern(regexp = "\\d{3} \\d{3} \\d{4}")
    private String phone;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy MM dd")
    private Date birthDate;

    @NotNull
    @Email
    @Size(min = 10, max = 80)
    private String email;

    @NotNull
    private String password; // Bu alan şifrelenmiş (hashed) olmalıdır

    @NotNull
    private LocalDateTime createDate;

    private String resetPasswordCode; // Bu alan hashlenmiş olmalıdır

    @NotNull
    private Boolean builtIn = false;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Role role;


}
