package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public String addOrder(Order order){
        orderRepository.saveOrder(order);
        return "New order added successfully";
    }

    public String addPartner(String partnerId){
        orderRepository.savePartner(partnerId);
        return "New delivery partner added successfully";
    }

    public String createOrderPartnerPair(String orderId, String partnerId){
        orderRepository.saveOrderPartnerMap(orderId, partnerId);
        return "New order-partner pair added successfully";
    }

    public Order getOrderById(String orderId){
        return orderRepository.findOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.findPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId){
        return orderRepository.findOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.findOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders(){
        return orderRepository.findAllOrders();
    }

    public String deletePartner(String partnerId){
        orderRepository.deletePartner(partnerId);
        return partnerId + " removed successfully";
    }

    public String deleteOrder(String orderId){
        orderRepository.deleteOrder(orderId);
        return orderId + " removed successfully";
    }

    public Integer getCountOfUnassignedOrders(){
        return orderRepository.findCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        return orderRepository.findOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.findLastDeliveryTimeByPartnerId(partnerId);
    }
}
