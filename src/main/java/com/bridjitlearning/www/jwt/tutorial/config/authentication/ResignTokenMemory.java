package com.bridjitlearning.www.jwt.tutorial.config.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ResignTokenMemory{

    private List<String> tokens;

    public ResignTokenMemory(){
        this.tokens = new ArrayList<>();
    }

    /**
     * black list a token
     * @param token to be blacklisted
     * @return true if blacklisted
     */
    public boolean put(String token){
        if(!tokens.contains(token)) {
            tokens.add(token);
        }
        return true;
    }

    /**
     * remove expired token from blacklist
     * @param token expired token to be removed
     * @return true if remoev sucess, false if already removed
     */
    public boolean forget(String token){
        if(tokens.contains(token)) {
            tokens.remove(token);
            return true;
        }
        return false;
    }

    /**
     * check if token is  blacklisted
     * @param token token to be validated
     * @return true if invalid
     */
    public boolean check(String token){
        return tokens.contains(token);
    }
}