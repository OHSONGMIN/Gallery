package com.example.backend.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtServiceImpl implements JwtService {

    private String secretKey = "dhaiisdjhk!@#2135d@@@jdjskal%%%skdj123155djkdjl2123jkd0e80";

    @Override
    public String getToken(String key, Object value) {
/*
 * 입력 받은 key와 value를 secretKey를 이용해서
 * 만들어주는 메서드
 */
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 5);
        //Jave 8까지만 지원
        //byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] secretByteKey = Base64.getDecoder().decode(base64EncodedSecretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT"); //thpe은 JWT
        headerMap.put("alg", "HS256"); //algorithm은 HS256

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }
}
