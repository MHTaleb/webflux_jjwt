package com.bridjitlearning.www.jwt.tutorial.service;

import com.bridjitlearning.www.jwt.tutorial.config.authentication.ResignTokenMemory;
import com.bridjitlearning.www.jwt.tutorial.config.encoder.PBKDF2Encoder;
import com.bridjitlearning.www.jwt.tutorial.config.jwt.JWTUtil;
import com.bridjitlearning.www.jwt.tutorial.domain.User;
import com.bridjitlearning.www.jwt.tutorial.dto.AuthResponseParam;
import com.bridjitlearning.www.jwt.tutorial.repository.ReactiveUserRepository;
import com.bridjitlearning.www.jwt.tutorial.service.declaration.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class JWTAuthenticationService implements AuthenticationService {

    @Autowired private ReactiveUserRepository reactiveUserRepository;

    @Autowired private JWTUtil jwtUtils;

    @Autowired private PBKDF2Encoder encoder;

    @Autowired private ResignTokenMemory resignTokenMemory;

    @Override
    public Mono<ResponseEntity<?>> authenticate(String username, String password) {
        return getUser(username).map((user) -> {
            boolean matches = encoder.matches(password, user.getPassword());
            if(matches){
                return ResponseEntity.ok(new AuthResponseParam(jwtUtils.generateToken(user)));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        
    }

    private Mono<User> getUser(String username){
        return reactiveUserRepository.findByUsername(username);
    }

    @Override
    public Mono<ResponseEntity<?>> logout(String token) {
        return Mono.fromRunnable(()->{
            resignTokenMemory.put(token);
        }).justOrEmpty(ResponseEntity.status(HttpStatus.OK).build());
    }

}