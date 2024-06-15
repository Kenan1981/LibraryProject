package com.tpe.security.service;

import com.tpe.entity.concretes.user.User;

import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDetailsImpl(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().getRoleType().getName()
                    );
        } throw new UsernameNotFoundException("user email ' = " + email + " not found");


    }
}
