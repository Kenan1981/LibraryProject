package com.tpe.payload.helper;

import com.tpe.entity.concretes.user.User;
import com.tpe.exception.BadRequestException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodHelper {


    private UserRepository userRepository;

    private int score;
    private int maxBooks;
    private int loanDays;



    // !!! isUserExist
    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,
                        userId)));
    }



    // !!! checkBuiltIn
    public void checkBuiltIn(User user){
        if(Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }


    public void setLoanDetails(int score) {
        if (score >= 2) {
            this.maxBooks = 5;
            this.loanDays = 20;
        } else if (score == 1) {
            this.maxBooks = 4;
            this.loanDays = 15;
        } else if (score == 0) {
            this.maxBooks = 3;
            this.loanDays = 10;
        } else if (score == -1) {
            this.maxBooks = 2;
            this.loanDays = 6;
        } else if (score <= -2) {
            this.maxBooks = 1;
            this.loanDays = 3;
        }
    }



}