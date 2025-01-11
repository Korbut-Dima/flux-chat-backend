package com.fluxchat.fluxchat.services;

import com.fluxchat.fluxchat.controllers.dtos.AuthDto;
import com.fluxchat.fluxchat.controllers.dtos.TokenDto;
import com.fluxchat.fluxchat.repositories.UserRepository;
import com.fluxchat.fluxchat.services.interfaces.AuthService;
import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.jwt.JwtTokenProvider;
import com.fluxchat.fluxchat.enteties.enums.Role;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenDto login(AuthDto authDto) throws UsernameNotFoundException {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken( authDto.getEmail(), authDto.getPassword() );
            authenticate(usernamePasswordAuthenticationToken);
        } catch ( AuthenticationException e) {
            throw new BadCredentialsException("Невірний логін або пароль");
        }

        User user = userRepository.findByEmail(authDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Користувача з даною поштовою скринькою знайдено: " + authDto.getEmail())
        );
        return buildTokenFromUser(user);
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private TokenDto buildTokenFromUser(User user) {
        List<Role> list = new ArrayList<>();
        list.add(user.getRole())  ;
        String token = jwtTokenProvider.createToken(user, list);
        return new TokenDto(token);
    }

    @Override
    public User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new AuthorizationServiceException("No user in context");
        }
        String email = authentication.getName();
        return userRepository.findByEmail( email ).get();
    }

    @Override
    public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
