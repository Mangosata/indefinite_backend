package com.overwall.demo.user.token;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.overwall.demo.user.User;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Token {

    private static final String SECRET_KEY = "Aj56sk128he19";
    private static final int EXPIRE_HOUR = 24; // token在24小时之后过期

    public Token() {
        String token = "";
    }

    public String createToken(User user) {
        Date now = new Date();
        Long expireTime = TimeUnit.HOURS.toMillis(EXPIRE_HOUR);
        Date expireAt = new Date(expireTime + now.getTime());

        String token = JWT.create()
                // 自定义数据
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                // 生成时间
                .withIssuedAt(now)
                // 过期时间
                .withExpiresAt(expireAt)
                .sign(Algorithm.HMAC256(SECRET_KEY));
        return token;
    }

    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
