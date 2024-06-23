package com.tpe.payload.mappers;


import com.tpe.entity.concretes.business.Loan;
import com.tpe.payload.helper.MethodHelper;
import com.tpe.payload.request.LoanRequest;
import com.tpe.payload.response.LoanResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class LoanMapper {

    @Autowired
    private final MethodHelper methodHelper;

    public Loan mapLoanRequestToLoan(LoanRequest loanRequest) {
        return Loan.builder()
                .bookId(loanRequest.getBookId())
                .userId(loanRequest.getUserId())
                .loanDate(loanRequest.getLoanDate())
                .expireDate(LocalDateTime.now().plusDays(methodHelper.getLoanDays()))
                //.returnDate(loanRequest.getReturnDate())
                .notes(loanRequest.getNotes())
                .build();
    }

    public LoanResponse mapLoanToLoanResponse(Loan loan) {
        return LoanResponse.builder()
                .id(loan.getId())
                .bookId(loan.getBookId())
                .userId(loan.getUserId())
                .notes(loan.getNotes())
                .expireDate(loan.getExpireDate())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .build();
    }

    public Loan mapLoanRequestToUpdatedLoan(Long id, LoanRequest loanRequest) {
        return mapLoanRequestToLoan(loanRequest)
                .toBuilder()
                .id(id)
                .build();
    }


}