package com.tpe.payload.mappers;


import com.tpe.entity.concretes.business.Loan;
import com.tpe.payload.request.LoanRequest;
import com.tpe.payload.response.LoanResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoanMapper {

    public Loan mapLoanRequestToLoan(LoanRequest loanRequest) {
        return Loan.builder()
                .bookId(loanRequest.getBookId())
                .userId(loanRequest.getUserId())
                .loanDate(loanRequest.getLoanDate())
                .expireDate(loanRequest.getExpireDate())
                .returnDate(loanRequest.getReturnDate())
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