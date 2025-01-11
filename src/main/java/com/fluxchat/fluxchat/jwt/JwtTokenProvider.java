package com.fluxchat.fluxchat.jwt;

import com.fluxchat.fluxchat.enteties.User;
import com.fluxchat.fluxchat.enteties.enums.Role;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${spring.security.jwt.token.secret}")
    private String secret;

    @Value("${spring.security.jwt.token.expired}")
    private long validateInMilliseconds;

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtTokenProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {secret = Base64.getEncoder().encodeToString(secret.getBytes());}

    public String createToken(User user, List<Role> role){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", getRoleNames(role));
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("id", user.getId());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validateInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }

    public String getUserName(String token){
        return getClaims(token).getBody().get("email").toString();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null){ //&& bearerToken.startsWith("Bearer_")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = getClaims(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                throw new JwtAuthenticationException("JWT token has expired");
            }
            return true;
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("Ви використовуєте невірний JWT токен", e);
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getAuthority());
        });

        return result;
    }
}
