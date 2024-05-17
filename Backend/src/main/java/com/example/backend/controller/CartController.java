package com.example.backend.controller;

import com.example.backend.entity.Cart;
import com.example.backend.entity.Item;
import com.example.backend.repository.CartRepository;
import com.example.backend.repository.ItemRepository;
import com.example.backend.service.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    JwtServiceImpl jwtService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ItemRepository itemRepository;

    //어떤 사용자의 카트에 담긴 물건 불러오는 기능
    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isVaild(token)) { //토큰이 유효하지 않다면...
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        //토큰이 유효하다면..
        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId); //memberId를 인자로 넣어서 카트 정보를 가져온 다음
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList(); //ItemId를 list화 해서 추출한 다음에
        List<Item> items = itemRepository.findByIdIn(itemIds); //이것을 다시 인자에 넣어서 items 정보를 가져온 것

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    //특정 아이템을 장바구니에 담는 기능
    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isVaild(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if (cart == null) { //cart가 null일 땐 새로운 카트를 추가
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
