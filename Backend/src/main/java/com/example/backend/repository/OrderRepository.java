package com.example.backend.repository;

import com.example.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    //주문내역을 역순으로 출력하기 위해
    List<Order> findByMemberIdOrderByIdDesc(int memberId);

}
