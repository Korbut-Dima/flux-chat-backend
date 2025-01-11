package com.fluxchat.fluxchat.services.interfaces;

import com.fluxchat.fluxchat.controllers.dtos.AuthDto;
import com.fluxchat.fluxchat.controllers.dtos.TokenDto;
import com.fluxchat.fluxchat.enteties.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface AuthService {

    User getUserFromContext();

    Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

    TokenDto login(AuthDto authDto);

    Authentication getAuthentication();
}
