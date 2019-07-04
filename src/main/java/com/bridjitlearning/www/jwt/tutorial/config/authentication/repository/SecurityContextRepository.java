package com.bridjitlearning.www.jwt.tutorial.config.authentication.repository;

import com.bridjitlearning.www.jwt.tutorial.config.authentication.JWTReactiveAuthenticationManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * we use this class to handle the bearer token extraction
 * and pass it to the JWTReactiveAuthentication manager so in the end 
 * we produce
 * 
 * simply said we extract the authorization we authenticate and 
 * depending on our implementation we produce a security context
 */

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private JWTReactiveAuthenticationManager authenticationManager;

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        
        ServerHttpRequest request = swe.getRequest();
        System.out.println("trying to authenticate");
        String authorizationHeaderContent = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if( authorizationHeaderContent !=null &&  !authorizationHeaderContent.isEmpty() &&  authorizationHeaderContent.startsWith("Bearer ")){
        		System.out.println("gettin bearer token");
                String token = authorizationHeaderContent.substring(7);

                Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
                return this.authenticationManager.authenticate(authentication).map((auth) -> {
                    return new SecurityContextImpl(auth);
                });

        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> save(ServerWebExchange arg0, SecurityContext arg1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}