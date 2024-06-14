package com.tpe.security.service;


import com.tpe.entity.concretes.user.User;
import com.tpe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameEquals(username);

        if(user != null){
            return new UserDetailsImpl(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPassword(),
                    user.getRole().getRoleType().name
            );
        }
        throw new UsernameNotFoundException("User' " + username + " not found");
    }
}
