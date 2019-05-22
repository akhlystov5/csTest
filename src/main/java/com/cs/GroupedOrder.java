package com.cs;

import java.util.List;
import java.util.stream.Collectors;

public class GroupedOrder extends Order {

    List<Order> orders;

    public GroupedOrder(List<Order> orders) {
        this.orders = orders;
        setId(orders.stream().map(Order::getId).sorted().collect(Collectors.toList()).toString());
        setUserId(orders.stream().map(Order::getUserId).sorted().collect(Collectors.toList()).toString());
        setType(orders.get(0).getType());
        setPrice(orders.get(0).getPrice());
        double sum = orders.stream().mapToDouble(Order::getQuantity).sum();
        setQuantity(sum);
    }

    @Override
    public String toString() {
        return "GroupedOrder{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", type=" + type +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
