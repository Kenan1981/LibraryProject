package com.tpe.controller;

import com.tpe.entity.concretes.business.Role;
import com.tpe.entity.concretes.user.User;
import com.tpe.payload.request.UserRequest;
import com.tpe.payload.response.ResponseMessage;
import com.tpe.payload.response.UserResponse;
import com.tpe.service.LoanService;
import com.tpe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoanService loanService;

    //register
    @PostMapping("/register/{userRole}") // http://localhost:8080/register  + JSON + POST
    public ResponseEntity<User> registerUser(@RequestBody UserRequest userRequest) {
        User registeredUser = userService.registerUser(userRequest);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    //authenticate user
    @PostMapping("/user") // http://localhost:8080/user  + JSON + POST
    @PreAuthorize("hasAnyAuthority('ADMIN','MEMBER','EMPLOYEE')")
    public ResponseEntity<User> getAuthenticatedUser(Principal principal) {
        User authenticatedUser = userService.getAuthenticatedUser(principal.getName());
        return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
    }

    //authenticate user loans
//    @PostMapping("/user/loans{userRole}") // http://localhost:8080/user/loans?page=1&size=10&sort=createDate&type=desc
//    @PreAuthorize("hasAnyAuthority('ADMIN','MEMBER','EMPLOYEE')")
//    public ResponseEntity<List<Loan>> getUserLoans(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "20") int size,
//            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
//            @RequestParam(value = "type", defaultValue = "desc") String type,
//            Principal principal) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(type), sort));
//        List<Loan> userLoans = userService.findUserLoans(principal.getName(), pageable);
//        return new ResponseEntity<>(userLoans, HttpStatus.OK);
//    }

    //get users
    @GetMapping("/users") // http://localhost:8080/users?page=1&size=10&sort=createDate&type=desc
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<Page<UserResponse>> getUsersByPage(
            @PathVariable String userRole,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createDate") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        Page<UserResponse> users = userService.getUsersByPage(page, size, sort, type, userRole);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // getUserById()
    @GetMapping("getUserById/{userId}")  // http://localhost:8080/user/getUserById/1
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    //create user
    @PostMapping("/users/{userRole}") // http://localhost:8080/users  + JSON + POST
    @PreAuthorize("hasAnyAuthority('ADMIN','EMPLOYEE')")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest, Authentication authentication) {
        Role creatorRole = (Role) authentication.getPrincipal();
        User createdUser = userService.createUser(userRequest, creatorRole.getRoleType());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    //update user
    @PutMapping("/update/{userId}") // http://localhost:8080/user/update/1
    @PreAuthorize("hasAuthority('ADMIN','EMPLOYEE')")
    public ResponseMessage<UserResponse> updateUser(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable Long userId) {
        return userService.updateUser(userRequest, userId);
    }

    //delete
    @DeleteMapping("/delete/{id}") //http://localhost:8080/user/delete/3
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }


}