package com.tpe.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoanResponse {

    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private LocalDateTime expireDate;
    private String notes;

}