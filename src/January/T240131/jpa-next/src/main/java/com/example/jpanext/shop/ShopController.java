package com.example.jpanext.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {
    @GetMapping("create-order")
    public String createOrder() {
        return "done";
    }
}
