package com.bridjitlearning.www.jwt.tutorial.service;

import com.bridjitlearning.www.jwt.tutorial.repository.ReactiveUserRepository;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService {

    private ReactiveUserRepository reactiveUserRepository;

    public UserService(ReactiveUserRepository reactiveUserRepository){
        this.reactiveUserRepository = reactiveUserRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(reactiveUserRepository.findByUsername(username).block());
    }


}