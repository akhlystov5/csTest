package com.cs;

import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.type == o2.type) {
            if (o1.type == OrderType.BUY) {
                return o2.price.compareTo(o1.price);
            } else {
                return o1.price.compareTo(o2.price);
            }
        } else {
            return o1.type == OrderType.BUY ? 1 : -1;
        }
    }
}
