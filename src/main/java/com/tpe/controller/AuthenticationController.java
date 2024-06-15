package com.tpe.controller;

import com.tpe.payload.request.SigninRequest;
import com.tpe.payload.response.AuthResponse;
import com.tpe.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping // http://localhost:8080/signin + POST + JSON
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid SigninRequest signinRequest) {
        return authenticationService.authenticateUser(signinRequest);
    }
}