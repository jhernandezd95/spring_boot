package com.example.crud.common.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.crud.common.http_errors.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtService {

    private static final String BEARER = "Bearer ";
    private static final String USER_CLAIM = "user";
    private static final String ROLE_CLAIM = "roles";

    @Value("${jwt.issuer}")
    private static String ISSUER = "com.example.crud";

    @Value("${jwt.expire}")
    private static Long EXPIRES_IN_MILLISECONDS = Long.valueOf(36000);

    @Value("${jwt.secret}")
    private static String SECRET = "123446";


    public JwtService() {
    }

    public boolean isBearer(String authorization) {
        return authorization != null
                && authorization.startsWith(BEARER)
                && authorization.split("\\.").length == 3;
    }

    public String createToken(String user, Collection<? extends GrantedAuthority> authorities) {

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }

        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date())
                    .withNotBefore(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLISECONDS * 1000))
                    .withClaim(USER_CLAIM, user)
                    .withClaim(ROLE_CLAIM, roles)
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (Exception error) {
            throw new JWTCreationException(error.getMessage(), error);
        }
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
        String token = authorization.split(" ")[1];
        return JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer(ISSUER).build()
                .verify(token);

    }
}
