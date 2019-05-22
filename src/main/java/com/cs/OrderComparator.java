package com.cs;

import java.util.Comparator;
//TODO later
public class OrderComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.type == o2.type) {
            if (o1.type == OrderType.BUY) {
                return o2.quantity.compareTo(o1.quantity);
            } else {
                return o1.quantity.compareTo(o2.quantity);
            }
        } else {
            return o1.type == OrderType.BUY ? 1 : -1;
        }
    }
}
