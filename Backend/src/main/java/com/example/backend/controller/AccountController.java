package com.example.backend.controller;

import com.example.backend.entity.Item;
import com.example.backend.entity.Member;
import com.example.backend.repository.ItemRepository;
import com.example.backend.repository.MemberRepository;
import com.example.backend.service.JwtService;
import com.example.backend.service.JwtServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    MemberRepository memberRepository;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> params,
                                HttpServletResponse res) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));

        if (member != null) {
            JwtService jwtService = new JwtServiceImpl();
            int id = member.getId();
            String token = jwtService.getToken("id", id);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true); //JavaScript로 접근할 수 없도록
            cookie.setPath("/");

            res.addCookie(cookie); //cookie값 추가
            System.out.println("id 맞나 " + id);
            //return ResponseEntity.ok().build(); //라고만 하면 로그인 해도 로그아웃으로 변하지 않은....
            return new ResponseEntity<>(id, HttpStatus.OK); //라고 하면 응답 값으로 id값을 줄 수 있음....
        }

        //로그인 실패했을 때
        //return 0;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
