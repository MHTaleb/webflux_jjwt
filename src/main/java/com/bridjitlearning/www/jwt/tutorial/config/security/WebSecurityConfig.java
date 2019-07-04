package com.bridjitlearning.www.jwt.tutorial.config.security;

import com.bridjitlearning.www.jwt.tutorial.config.authentication.JWTReactiveAuthenticationManager;
import com.bridjitlearning.www.jwt.tutorial.config.authentication.repository.SecurityContextRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig{

    @Autowired private JWTReactiveAuthenticationManager authenticationManager;

    @Autowired private SecurityContextRepository securityContext;

    @Bean public SecurityWebFilterChain configure(ServerHttpSecurity http){
        
        return http.exceptionHandling()
        .authenticationEntryPoint((swe , e) -> {
            return Mono.fromRunnable(()->{
                System.out.println( "authenticationEntryPoint user trying to access unauthorized api end points : "+
                                    swe.getRequest().getRemoteAddress()+
                                    " in "+swe.getRequest().getPath());
                swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            });
        }).accessDeniedHandler((swe, e) -> {
            return Mono.fromRunnable(()->{
                System.out.println( "accessDeniedHandler user trying to access unauthorized api end points : "+
                                    swe.getPrincipal().block().getName()+
                                    " in "+swe.getRequest().getPath());
                swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);                    
            });
        })
        .and()
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContext)
        .authorizeExchange()
        .pathMatchers(HttpMethod.OPTIONS).permitAll()
        .pathMatchers("/auth/login").permitAll()
        .anyExchange().authenticated()
        .and()
        .build();

         
    }

}