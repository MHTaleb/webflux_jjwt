package com.bridjitlearning.www.jwt.tutorial.config.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.bridjitlearning.www.jwt.tutorial.config.jwt.JWTUtil;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * en JWT les credential sont en effet le token JWT
 */

@Component
public class JWTReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String authToken = authentication.getCredentials().toString();
        
        System.out.println("auth begin");
        
        String username;
        try {
            username = jwtUtil.getUsernameFromToken(authToken);
            System.out.println("getting username");
        } catch (Exception e) {
            username = null;
        }
        System.out.println(username);
        System.out.println(authToken);
        if (jwtUtil.validateToken(authToken)) {
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            System.out.println("getting claims");
            
            List<String> roleNames = claims.get("role",List.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

            for (String roleName : roleNames) {
                authorities.add(new SimpleGrantedAuthority(Role.valueOf(roleName).name()));
            }
            
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, authorities);
                    return Mono.just(usernamePasswordAuthenticationToken);
        }

        return Mono.empty();
    }

}