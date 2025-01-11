package com.fluxchat.fluxchat.jwt;

import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.enteties.enums.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@NoArgsConstructor
public final class JwtUserFactory {
    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRole())
        );
    }

    private static GrantedAuthority mapToGrantedAuthorities(Role role){
        return new SimpleGrantedAuthority("ROLE_" + role.getAuthority());
    }
}
