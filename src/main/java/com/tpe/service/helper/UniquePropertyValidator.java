package com.tpe.service.helper;

import com.tpe.entity.concretes.user.User;
import com.tpe.exception.ConflictException;
import com.tpe.payload.messages.ErrorMessages;
import com.tpe.payload.request.UserRequest;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkDuplicate(String phone, String email) {

        if (userRepository.existsByPhone(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE, phone));
        }
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }

    }

    public void checkUniqueProperties(User user, UserRequest userRequest) {

        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChanced = false;

        if (!user.getPhone().equalsIgnoreCase(userRequest.getPhone())) {
            updatedPhone = userRequest.getPhone();
            isChanced = true;
        }
        if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())) {
            updatedEmail = userRequest.getEmail();
            isChanced = true;
        }

        if (isChanced) {
            checkDuplicate(updatedPhone, updatedEmail);
        }

    }
}