package com.tpe.payload.mappers;

import com.tpe.entity.concretes.user.User;
import com.tpe.payload.request.UserRequest;
import com.tpe.payload.response.UserResponse;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class UserMapper {
    public User mapUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .birthDate(userRequest.getBirthDate())
                .password(userRequest.getPassword())
                .phone(userRequest.getPhone())
                .build();

    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .password(user.getPassword())
                .phone(user.getPhone())
                .build();

    }

    public User mapUserRequestToUpdatedUser(UserRequest userRequest, Long userId){
        return User.builder()
                .id(userId)
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .birthDate(userRequest.getBirthDate())
                .password(userRequest.getPassword())
                .phone(userRequest.getPhone())
                .build();
    }





}