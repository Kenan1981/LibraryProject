package com.tpe.service.business;


import com.tpe.entity.concretes.business.Book;
import com.tpe.entity.concretes.business.Loan;
import com.tpe.entity.concretes.user.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.helper.MethodHelper;
import com.tpe.payload.helper.PageableHelper;
import com.tpe.payload.mappers.LoanMapper;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.messages.SuccessMessages;
import com.tpe.payload.request.LoanRequest;
import com.tpe.payload.response.LoanResponse;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.repository.BookRepository;
import com.tpe.repository.LoanRepository;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;
    private final PageableHelper pageableHelper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
//    private final MethodHelper methodHelper;


    //1.GET
    public Page<LoanResponse> findLoanByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);
        return loanRepository.findAll(pageable).map(loanMapper::mapLoanToLoanResponse);
    }


    //2.GET
    public ResponseMessage<LoanResponse> getLoanById(Long loanId) {

        Loan loan = isLoanExistById(loanId);

        return ResponseMessage.<LoanResponse>builder()
                .message(SuccessMessages.LOAN_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(loanMapper.mapLoanToLoanResponse(loan))
                .build();
    }


    //3.GET
    public List<Loan> getLoansByUserId(Long userId, int page, int size, String sort, String type) {
        Sort.Direction direction = type.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));
        return loanRepository.findByUserId(userId, pageRequest).getContent();
    }


    //4.GET
    public List<Loan> findLoansByBookId(Long bookId, int page, int size, String sort, String type) {
        Sort.Direction direction = type.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sort));
        return loanRepository.findByBookId(bookId, pageRequest).getContent();
    }


    //5.GET ===>>
    public ResponseMessage<LoanResponse> getAuthById(Long loanId) {

        Loan loan = isLoanExistById(loanId);

        LoanResponse loanDetails = loanMapper.mapLoanToLoanResponse(loan);

        return ResponseMessage.<LoanResponse>builder()
                .object(loanDetails)
                .message(SuccessMessages.LOAN_FOUND)
                .httpStatus(HttpStatus.OK)
                .build();
    }


    //POST ===>
//    public ResponseMessage<LoanResponse> createLoan(LoanRequest loanRequest) {
//
//        isLoanExistById(loanRequest.getId());
//        Loan createdLoan = loanRepository.save(loanMapper.mapLoanRequestToLoan(loanRequest));
//
//        return ResponseMessage.<LoanResponse>builder()
//                .object(loanMapper.mapLoanToLoanResponse(createdLoan))
//                .message(SuccessMessages.LOAN_CREATED)
//                .httpStatus(HttpStatus.CREATED)
//                .build();
//    }


    // Gerekli bağımlılıklar burada tanımlanabilir
    // POST ===>
    public ResponseMessage<LoanResponse> createLoan(LoanRequest loanRequest) {
        // Kullanıcı ve kitap bilgilerini alın
        User user = userRepository.findById(loanRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(loanRequest.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        // Eğer kitap mevcut değilse
        if (!book.isLoanable()) {
            return ResponseMessage.<LoanResponse>builder()
                    .message(ErrorMessages.BOOK_NOT_LOANABLE_MESSAGE)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Kullanıcının geri getirilmemiş ve iade tarihi geçmiş kitapları var mı kontrol edin
        if (loanRepository.existsByUserIdAndReturnDateIsNullAndExpireDateBefore(
                loanRequest.getUserId(), LocalDateTime.now())) {
            return ResponseMessage.<LoanResponse>builder()
                    .message("User has overdue books")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

//    // Kullanıcının puanına göre kitap alabileceği süreyi hesaplayın
//    int loanDays = user.getScore() * 7;
//
//    // Örneğin, her puan 1 hafta anlamına geliyor
//    LocalDateTime expireDate = LocalDateTime.now().plusDays(loanDays);

        MethodHelper methodHelper = new MethodHelper();
        methodHelper.setLoanDetails(user.getScore());


        // Yeni bir loan oluşturun
//        Loan loan = new Loan();
//        loan.setUserId(loanRequest.getUserId());
//        loan.setBookId(loanRequest.getBookId());
//        loan.setNotes(loanRequest.getNotes());
//        loan.setLoanDate(LocalDateTime.now());
//        loan.setExpireDate(LocalDateTime.now().plusDays(methodHelper.getLoanDays()));

        //mapleme
        Loan loan =loanMapper.mapLoanRequestToLoan(loanRequest);


        // Loan'u kaydedin
        loanRepository.save(loan);

        // Kitabı güncelleyin book.setLoanable(false);
        book.setLoanable(false);

        // Yanıt olarak Loan ve ilgili Book nesnelerini döndürün

        return ResponseMessage.<LoanResponse>builder()
                .object(loanMapper.mapLoanToLoanResponse(loan))
                .message(SuccessMessages.LOAN_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


    //PUT ==>>>
    public LoanResponse updateLessonById(Long loanId, LoanRequest loanRequest) {

        Loan loan = isLoanExistById(loanId);

        if (!loanRepository.existsById(loanId)) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LOAN_MESSAGE);
        }

        Loan updatedLoan = loanMapper.mapLoanRequestToUpdatedLoan(loanId, loanRequest);

        updatedLoan.setLoanDate(loan.getLoanDate());
        updatedLoan.setExpireDate(loan.getExpireDate());
        updatedLoan.setNotes(loan.getNotes());
        updatedLoan.setBook(loan.getBook());
        // Diğerleri????
        Loan savedLoan = loanRepository.save(updatedLoan);

        return loanMapper.mapLoanToLoanResponse(savedLoan);

    }


    //YARDIMCI METHOD
    private Loan isLoanExistById(Long loanId) {
        return loanRepository.findById(loanId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LOAN_MESSAGE))
        );

    }


}