package com.example.crud.common.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private static final String BEARER = "Bearer ";
    private static final String USER_CLAIM = "user";
    private static final String ROLE_CLAIM = "roles";
    private static String ISSUER;

    private static Long EXPIRES_IN_MILLISECONDS;

    private static String SECRET;

    @Autowired
    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.issuer}") String issuer,
                      @Value("${jwt.expire}") int expire) {
        SECRET = secret;
        EXPIRES_IN_MILLISECONDS = (long) expire;
        ISSUER = issuer;
    }

    public boolean isBearer(String authorization) {
        return authorization != null
                && authorization.startsWith(BEARER)
                && authorization.split("\\.").length == 3;
    }

    public String createToken(String user, Collection<GrantedAuthority> role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLISECONDS * 1000))
                .withClaim(USER_CLAIM, user)
                .withClaim(ROLE_CLAIM, (List<?>) role)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String user(String authorization) throws JWTVerificationException {
        return this.verify(authorization).getClaim(USER_CLAIM).asString();
    }

    public List<String> roles(String authorization) {
        return Arrays.asList(this.verify(authorization).getClaim(ROLE_CLAIM).asArray(String.class));
    }

    private DecodedJWT verify(String authorization) {
        if (!this.isBearer(authorization)) {
            throw new JWTVerificationException("It is not bearer");
        }
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER).build()
                    .verify(authorization);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
