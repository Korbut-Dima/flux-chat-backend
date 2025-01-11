package com.fluxchat.fluxchat.services;

import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.jwt.JwtUser;
import com.fluxchat.fluxchat.jwt.JwtUserFactory;
import com.fluxchat.fluxchat.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача немає: " + email));

        JwtUser jwtUser = JwtUserFactory.create(user);

        return jwtUser;
    }
}
