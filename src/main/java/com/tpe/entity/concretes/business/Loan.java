package com.tpe.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.entity.concretes.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "loans")

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @NotNull(message = "userId must not be empty")
    private Long userId;

    @Column(name = "book_id")
    @NotNull(message = "bookId must not be empty")
    private Long bookId;


    @Column(name = "loan_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime loanDate;

    @Column(name = "return_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime returnDate;

    @NotNull(message = "expireDate must not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime expireDate;


    @Column(nullable = true)
    @Size(max = 300)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // ?   Notes for employee or admin

    //@JoinTable(
    //        name = "lesson_program_lesson",
    //        joinColumns = @JoinColumn(name = "lessonprogram_id"),
    //        inverseJoinColumns = @JoinColumn(name = "lesson_id")
    //)

}
