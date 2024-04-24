package org.example.lab12.Service;

import lombok.RequiredArgsConstructor;
import org.example.lab12.Api.ApiException;
import org.example.lab12.Model.User;
import org.example.lab12.Repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final AuthRepository authRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findUserByUsername(username);

        if (user == null) {
            throw new ApiException("wrong username or password");

        }
        return user;
    }
}


