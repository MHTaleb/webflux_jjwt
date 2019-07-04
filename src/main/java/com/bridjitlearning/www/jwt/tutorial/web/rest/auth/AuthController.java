package com.bridjitlearning.www.jwt.tutorial.web.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridjitlearning.www.jwt.tutorial.dto.AuthRequestParam;
import com.bridjitlearning.www.jwt.tutorial.dto.LogoutRequestParam;
import com.bridjitlearning.www.jwt.tutorial.service.declaration.AuthenticationService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(AuthController.AUTH)
public class AuthController {

    static final String AUTH = "/auth";

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequestParam arp) {

        String username = arp.getUsername();
        String password = arp.getPassword();

        return authService.authenticate(username, password);
    }

    @PostMapping("/logout")
    @Secured({})
    public Mono<ResponseEntity<?>> logout(@RequestBody LogoutRequestParam lrp) {

        String token = lrp.getToken();
        
        return authService.logout(token);
    }

}