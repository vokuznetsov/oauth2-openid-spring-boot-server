package com.github.oauth.authorization.server.config.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Service
public class PasswordEncoderExt implements PasswordEncoder {

    private static final String ALGORITHM_NAME = "SHA-256";

    private MessageDigest md;

    @SneakyThrows
    @Override
    public String encode(CharSequence rawPassword) {
        md = MessageDigest.getInstance(ALGORITHM_NAME);
        byte[] digest = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, digest));
    }

    @SneakyThrows
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        md = MessageDigest.getInstance(ALGORITHM_NAME);
        byte[] digest = md.digest(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
        String pass = String.format("%064x", new BigInteger(1, digest));
        return pass.equals(encodedPassword);
    }
}

