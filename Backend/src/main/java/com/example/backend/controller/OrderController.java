package com.example.backend.controller;

import com.example.backend.dto.OrderDto;
import com.example.backend.entity.Order;
import com.example.backend.repository.OrderRepository;
import com.example.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JwtServiceImpl jwtService;

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isVaild(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        List<Order> orders = orderRepository.findAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isVaild(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Order newOrder = new Order();
        newOrder.setMemberId(jwtService.getId(token));
        newOrder.setName(dto.getName()); //비어있을수도 있으니 유효성 검사 해주는 게 좋음
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());

        orderRepository.save(newOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
