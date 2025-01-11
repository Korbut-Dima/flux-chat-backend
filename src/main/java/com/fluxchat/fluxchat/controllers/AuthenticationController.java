package com.fluxchat.fluxchat.controllers;

import com.fluxchat.fluxchat.controllers.dtos.AuthDto;
import com.fluxchat.fluxchat.controllers.dtos.TokenDto;
import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.services.interfaces.AuthService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public TokenDto login(@RequestBody AuthDto authDto) {
        return authService.login(authDto);
    }

    @GetMapping("/current-user")
    public User getCurrentUser() {
        User user = authService.getUserFromContext();
//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(user, userDto);
//        if (user.getMaf() != null) {
//            userDto.setMafId(user.getMaf().getId());
//        }
        return user;
    }
}
