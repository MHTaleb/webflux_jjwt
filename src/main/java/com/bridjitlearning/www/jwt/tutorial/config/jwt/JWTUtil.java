package com.bridjitlearning.www.jwt.tutorial.config.jwt;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bridjitlearning.www.jwt.tutorial.config.authentication.ResignTokenMemory;
import com.bridjitlearning.www.jwt.tutorial.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil implements Serializable {

    private static final long serialVersionUID = 198465128L;

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;
    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expiraiton;


    @Autowired private ResignTokenMemory resignTokenMemory;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        Long expirationTimeLong = Long.parseLong(expiraiton);
        final Date creationDate = new Date();
        final Date expiraitonDate = new Date(creationDate.getTime() + expirationTimeLong);
        return Jwts.builder().setIssuedAt(creationDate).setSubject(username).setClaims(claims)
                .setExpiration(expiraitonDate)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();

    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token) && resignTokenMemory.check(token);
    }

    public String generateToken(User user){
        final Map<String,Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        String token = doGenerateToken(claims, user.getUsername());
        resignTokenMemory.forget(token);
        return token;
    }

}