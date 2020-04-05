package com.overwall.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

public class TokenTest {
    public static final String SECRET_KEY = "Aj56sk128he19";
    @Test
    public void createToken() {
        String token = JWT.create()
                // 自定义数据
                .withClaim("id", 1)
                .withClaim("username", "admin")
                // 生成时间
                .withIssuedAt(new Date())
                // 过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + 100000))
                .sign(Algorithm.HMAC256(SECRET_KEY));
        System.out.println(token);
    }

    @Test
    public void require() {
        // 生成校验对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();

        try {
            DecodedJWT jwt = verifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNTg2MDM2OTUzLCJpYXQiOjE1ODYwMzY4NTMsInVzZXJuYW1lIjoiYWRtaW4ifQ.2nQUg5HSQK8xAGGgSQE3TuUIm_bs3SJsGeyraAYONVo");
            System.out.println(jwt.getHeader());
            System.out.println(jwt.getPayload());
        } catch (TokenExpiredException e) {
            System.out.println("已过期");
        } catch (SignatureVerificationException e){
            System.out.println("不合法");
        } catch (Exception e) {
            System.out.println("认证失败");
        }


    }
}
