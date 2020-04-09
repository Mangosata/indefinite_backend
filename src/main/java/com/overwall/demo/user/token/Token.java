package com.overwall.demo.user.token;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.overwall.demo.user.User;
import com.overwall.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Token {

    @Autowired
    UserRepository userRepository;

    private static final String SECRET_KEY = "Aj56sk128he19";
    private static final int EXPIRE_HOUR = 24; // token在24小时之后过期

    public Token() {
        String token = "";
    }


    public String createToken(Long id, String username) {
        Date now = new Date();
        Long expireTime = TimeUnit.HOURS.toMillis(EXPIRE_HOUR);
        Date expireAt = new Date(expireTime + now.getTime());

        String token = JWT.create()
                // 自定义数据
                .withClaim("id", id)
                .withClaim("username", username)
                // 生成时间
                .withIssuedAt(now)
                // 过期时间
                .withExpiresAt(expireAt)
                .sign(Algorithm.HMAC256(SECRET_KEY));
        return token;
    }

    public boolean verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();

        try {
            verifier.verify(token);
            return true;
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return false;
        }
    }

    // token refresh
    public String refreshToken(String token) {
        // 解码
        DecodedJWT jwt = JWT.decode(token);
        // 获取id
        Long id = jwt.getClaim("id").asLong();
        String username = jwt.getClaim("username").asString();
        // 刷新token
        return createToken(id, username);

    }

    /**
     * over 80%, auto refresh token and do verify
     */
    public String autoRequire(String token) {
        boolean checkToken = verifyToken(token);
        if (checkToken) {
            // 解码
            DecodedJWT jwt = JWT.decode(token);
            // 获取当前时间 转化为秒
            long current = System.currentTimeMillis() / 1000;
            // 获取开始时间
            Long start = jwt.getClaim("iat").asLong();
            // 获取结束时间
            Long end = jwt.getClaim("exp").asLong();
            // 时间超过80%返回新的token， 否则返回原来的token
            if ((current - start) * 1.0 / (end - start) > 0.8) {
                return refreshToken(token);
            } else {
                return token;
            }
        } else {
            throw new JWTVerificationException("token不合法");
        }
    }
}
