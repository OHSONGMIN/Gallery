package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "members")
public class Member { //Table과 class를 연결

    @Id //primary key 라는 뜻
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가 값이다
    private int id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;
}
