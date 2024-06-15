package com.tpe.controller;

import com.tpe.entity.concretes.user.User;
import com.tpe.payload.request.UserRequest;
import com.tpe.payload.response.UserResponse;
import com.tpe.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //register
    @PostMapping("/register/{userRole}") // http://localhost:8080/user/save/Admin  + JSON + POST
    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest,
                                                                  @PathVariable String userRole) {
        return ResponseEntity.ok(userService.saveUser(userRequest, userRole));
    }

    //post
    @PostMapping("/user")// http://localhost:8080/user  + JSON + POST
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE','MEMBER')")
    public User postUserAuthenticatedUser() {
        return userService.getAuthenticatedUser();
    }

    //delete
    @DeleteMapping("/delete/{id}") //http://localhost:8080/user/delete/3
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id,
                                                 HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.deleteUserById(id, httpServletRequest));
    }
}
