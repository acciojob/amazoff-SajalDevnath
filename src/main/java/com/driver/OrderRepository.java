package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<>();
        this.partnerMap = new HashMap<>();
        this.partnerToOrderMap = new HashMap<>();
        this.orderToPartnerMap = new HashMap<>();
    }

    public void saveOrder(Order order){
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            partnerToOrderMap.computeIfAbsent(partnerId, k -> new HashSet<>()).add(orderId);
            orderToPartnerMap.put(orderId, partnerId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders() + 1);
        }
    }

    public Order findOrderById(String orderId){
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        return partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        return new ArrayList<>(partnerToOrderMap.getOrDefault(partnerId, new HashSet<>()));
    }

    public List<String> findAllOrders(){
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        partnerMap.remove(partnerId);
        Set<String> orders = partnerToOrderMap.remove(partnerId);
        if (orders != null) {
            orders.forEach(orderId -> orderToPartnerMap.remove(orderId));
        }
    }

    public void deleteOrder(String orderId){
        String partnerId = orderToPartnerMap.remove(orderId);
        if (partnerId != null) {
            partnerToOrderMap.get(partnerId).remove(orderId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders() - 1);
        }
        orderMap.remove(orderId);
    }

    public Integer findCountOfUnassignedOrders(){
        int unassignedCount = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderToPartnerMap.containsKey(orderId)) {
                unassignedCount++;
            }
        }
        return unassignedCount;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        int count = 0;
        int time = Integer.parseInt(timeString.split(":")[0]) * 60 + Integer.parseInt(timeString.split(":")[1]);
        Set<String> orders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
        for (String orderId : orders) {
            int deliveryTime = Integer.parseInt(orderMap.get(orderId).getDeliveryTime().split(":")[0]) * 60
                    + Integer.parseInt(orderMap.get(orderId).getDeliveryTime().split(":")[1]);
            if (deliveryTime > time) {
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        String lastTime = "00:00";
        Set<String> orders = partnerTo
