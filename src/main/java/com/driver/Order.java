package com.driver;

public class Order {

    private String id;
    private String deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }
}
