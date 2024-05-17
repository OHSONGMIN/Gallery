package com.example.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);

    Claims getClaims(String token);

    //요청이 왔을 때 요청한 사용자가 올바른지 여부를 나타내주는
    //인자로 받은 토큰이 문제가 없는지
    boolean isVaild(String token);

    //토큰 정보에서 사용자의 id 값을 가져오는
    int getId(String token);
}
