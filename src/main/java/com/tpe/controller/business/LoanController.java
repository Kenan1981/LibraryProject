package com.tpe.controller.business;


import com.tpe.entity.concretes.business.Loan;
import com.tpe.payload.response.LoanResponse;
import com.tpe.payload.request.LoanRequest;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.service.business.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    //1.GET
    @PreAuthorize("hasAnyAuthority('MEMBER')")
    @GetMapping("/findLoanByPage") // http://localhost:8080/loans?page=1&size=10&sort=loanDate&type=desc
    public Page<LoanResponse> findLoanByPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "loanDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type) {
        return loanService.findLoanByPage(page, size, sort, type);
    }


    //2.GET

    @PreAuthorize("hasAnyAuthority( 'MEMBER')")
    @GetMapping("/getLoanById/{loanId}")  // http://localhost:8080/loans/getLoanById/1
    public ResponseMessage<LoanResponse> getLoanById(@PathVariable Long loanId) {
        return loanService.getLoanById(loanId);
    }


    //3.GET
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @GetMapping("/getLoansByUserId/{userId}")  // http://localhost:8080/loans/user/5?page=1&size=10&sort=loanDate&type=desc
    public ResponseEntity<List<Loan>> getLoansByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "loanDate") String sort,
            @RequestParam(defaultValue = "desc") String type) {

        List<Loan> loans = loanService.getLoansByUserId(userId, page, size, sort, type);

        return ResponseEntity.ok(loans);
    }


    //4.GET
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @GetMapping("/getLoansByBookId/{bookId}") // http://localhost:8080/loans/book/5?page=1&size=10&sort=loanDate&type=desc
    public ResponseEntity<List<Loan>> getLoansByBookId(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "loanDate") String sort,
            @RequestParam(defaultValue = "desc") String type) {

        List<Loan> loans = loanService.findLoansByBookId(bookId, page, size, sort, type);

        return ResponseEntity.ok(loans);
    }


    //5.GET ===>>
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @GetMapping("/getLoanDetails/{loanId}") // http://localhost:8080/loans/auth/5
    public ResponseMessage<LoanResponse> getLoanDetails(@PathVariable Long loanId) {
        return loanService.getAuthById(loanId);
    }


    //POST
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @PostMapping("/createLoan")  // http://localhost:8080/loans
    public ResponseMessage<LoanResponse> createLoan(@RequestBody @Valid LoanRequest loanRequest) {
        return loanService.createLoan(loanRequest);
    }


    //PUT ==>>>
    @PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
    @PutMapping("/updateLoanById/{id}") // http://localhost:8080/loans/5
    public ResponseEntity<LoanResponse> updateLoanById(@PathVariable Long id,
                                                       @RequestBody LoanRequest loanRequest) {
        return ResponseEntity.ok(loanService.updateLessonById(id, loanRequest));
    }


}