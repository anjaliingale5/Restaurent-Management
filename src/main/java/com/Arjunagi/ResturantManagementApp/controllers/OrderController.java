package com.Arjunagi.ResturantManagementApp.controllers;

import com.Arjunagi.ResturantManagementApp.models.dto.AuthInpDto;
import com.Arjunagi.ResturantManagementApp.models.order.FoodOrder;
import com.Arjunagi.ResturantManagementApp.models.order.OrderStatus;
import com.Arjunagi.ResturantManagementApp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public String placeOrder(@RequestBody AuthInpDto authInpDto,@RequestParam List<Integer> foodIds){
        return orderService.placeOrder(authInpDto,foodIds);
    }

    @PutMapping("/order/{orderId}/status")
    public String updateOrderStatus(@RequestBody AuthInpDto authInpDto, @PathVariable Integer orderId, @RequestParam OrderStatus orderStatus){
        return orderService.updateOrderStatus(authInpDto,orderId,orderStatus);
    }

    @GetMapping("/order/user")
    public List<FoodOrder> getOrders(@RequestBody AuthInpDto authInpDto){
        return orderService.getOrders(authInpDto);
    }
}
