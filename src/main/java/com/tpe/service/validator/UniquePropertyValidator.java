package com.tpe.service.validator;

import com.tpe.exception.ConflictException;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private final UserRepository userRepository;

    public void checkDuplicate(String username, String phone, String email) {


        if (userRepository.existsByUsername(username)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
        }

        if (userRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE, phone));
        }
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }

    }

    public void checkUniqueProperties(User user, AbstractUserRequest abstractUserRequest) {
        String updatedUsername = "";
        String updatedSnn = "";
        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChanced = false;
        if (!user.getUsername().equalsIgnoreCase(abstractUserRequest.getUsername())) {
            updatedUsername = abstractUserRequest.getUsername();
            isChanced = true;
        }
        if (!user.getSsn().equalsIgnoreCase(abstractUserRequest.getSsn())) {
            updatedSnn = abstractUserRequest.getSsn();
            isChanced = true;
        }
        if (!user.getPhoneNumber().equalsIgnoreCase(abstractUserRequest.getPhoneNumber())) {
            updatedPhone = abstractUserRequest.getPhoneNumber();
            isChanced = true;
        }
        if (!user.getEmail().equalsIgnoreCase(abstractUserRequest.getEmail())) {
            updatedEmail = abstractUserRequest.getEmail();
            isChanced = true;
        }

        if (isChanced) {
            checkDuplicate(updatedUsername, updatedSnn, updatedPhone, updatedEmail);
        }

    }
}
