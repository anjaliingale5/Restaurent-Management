package com.Arjunagi.ResturantManagementApp.services;

import com.Arjunagi.ResturantManagementApp.models.FoodItem;
import com.Arjunagi.ResturantManagementApp.models.UserAuthToken;
import com.Arjunagi.ResturantManagementApp.models.dto.AuthInpDto;
import com.Arjunagi.ResturantManagementApp.models.order.FoodOrder;
import com.Arjunagi.ResturantManagementApp.models.order.OrderStatus;
import com.Arjunagi.ResturantManagementApp.reposotories.IOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    IOrderRepo orderRepo;
    @Autowired
    FoodItemService foodItemService;
    @Autowired
    UserAuthTokenService userAuthTokenService;
    @Autowired
    UserService userService;

    public String placeOrder(AuthInpDto authInpDto, List<Integer> foodIds) {
        UserAuthToken userAuthToken=userAuthTokenService.getUserAuthToken(authInpDto);
        if(userAuthToken==null)return "wrong credentials";
        List<FoodItem> foodItems=foodItemService.findByIds(foodIds);
        if(foodItems==null||foodItems.size()==0)return "please add food items";
        FoodOrder foodOrder=new FoodOrder(foodItems,userAuthToken.getUser());
        orderRepo.save(foodOrder);
        return "order placed";
    }

    public String updateOrderStatus(AuthInpDto authInpDto, Integer orderId, OrderStatus orderStatus) {
        if(userService.isAdmin(authInpDto)){
            FoodOrder foodOrder=orderRepo.findById(orderId).orElse(null);
            if(foodOrder==null)return "wrong order id";
            foodOrder.setOrderStatus(orderStatus);
            orderRepo.save(foodOrder);
            return "updated sucessfully";

        }
        return "wrong input";
    }

    public List<FoodOrder> getOrders(AuthInpDto authInpDto) {
        UserAuthToken userAuthToken=userAuthTokenService.getUserAuthToken(authInpDto);
        if(userAuthToken==null)return null;
        return orderRepo.findByUser(userAuthToken.getUser());
    }
}
