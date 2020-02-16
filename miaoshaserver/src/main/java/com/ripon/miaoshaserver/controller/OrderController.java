package com.ripon.miaoshaserver.controller;

import com.ripon.miaoshaserver.domain.Order;
import com.ripon.miaoshaserver.result.Result;
import com.ripon.miaoshaserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public Result getOrder (@RequestParam Long orderId){
        Order order = orderService.getOrderById(orderId);
        return Result.success(order);
    }
}
