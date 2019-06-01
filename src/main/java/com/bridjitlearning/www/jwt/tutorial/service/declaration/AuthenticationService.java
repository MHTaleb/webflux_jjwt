package com.bridjitlearning.www.jwt.tutorial.service.declaration;

import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;

public interface AuthenticationService {

	Mono<ResponseEntity<?>> authenticate(String username, String password);

	Mono<ResponseEntity<?>> logout(String token);

}