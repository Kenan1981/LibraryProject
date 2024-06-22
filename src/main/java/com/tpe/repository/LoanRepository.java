package com.tpe.repository;

import com.tpe.entity.concretes.business.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
//    @Query("SELECT l FROM Loan l WHERE l.user.username = :username")
//    List<Loan> findByUserByLoans(@Param("username") String username, Pageable pageable);



    Page<Loan> findByUserId(Long userId, Pageable pageable);

    Page<Loan> findByBookId(Long bookId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM Loan l WHERE l.userId = :userId AND l.returnDate IS NULL AND l.expireDate < :expireDate")
    boolean existsByUserIdAndReturnDateIsNullAndExpireDateBefore(@Param("userId") Long userId, @Param("expireDate") LocalDateTime expireDate);



//    Loan findById(long loanId);

}