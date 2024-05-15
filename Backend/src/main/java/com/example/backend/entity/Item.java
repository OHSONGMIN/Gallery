package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "items")
public class Item { //Table과 class를 연결
    //column들도 연결해야한다.
    @Id //primary key 라는 뜻
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가 값이다
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    //이 class가 데이터베이스의 items라고 하는 table과 맵핑이 되도록 하는 작업...
}
