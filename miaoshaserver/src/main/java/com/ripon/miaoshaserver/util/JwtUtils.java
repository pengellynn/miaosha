package com.ripon.miaoshaserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {
    private final static String SECRET = "adfjkp";
    private final static Algorithm algorithm = Algorithm.HMAC256(SECRET);
    public static String createToken(String userId) {
        String token = "";
        Long expireMillis = System.currentTimeMillis() + 1000*60*60*24;
        Date expireDate = new Date(expireMillis);
        try {
            token = JWT.create()
                    .withAudience(userId)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return token;
    }

    public static String verifyToken(String token) {
        String userId = "";
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException exception) {
            exception.printStackTrace();
            throw new RuntimeException("401");
        }
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withAudience(userId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            throw new RuntimeException("token无效");
        }
        return userId;
    }
}
