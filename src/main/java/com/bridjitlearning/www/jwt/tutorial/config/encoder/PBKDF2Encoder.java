package com.bridjitlearning.www.jwt.tutorial.config.encoder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * creating a specific encoder using a salt, an iteration and a secret key
 * this class is a tool it handles two main topics
 *  1- pasword encoding : using sha215 with salt (jwt)
 *  2- checking if a raw password is the same as an encoded one
 */

@Component
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${springbootwebfluxjjwt.password.encoder.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.password.encoder.iteration}")
    private Integer iteration;

    @Value("${springbootwebfluxjjwt.password.encoder.keylength}")
    private Integer keylength;

    @Override
    public String encode(CharSequence rawPassword) {

        try {
            byte[] encoded = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512").generateSecret(
                    new PBEKeySpec(rawPassword.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(encoded);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secret;

    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

}