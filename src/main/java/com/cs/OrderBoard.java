package com.cs;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * I presume we can't have orders with the same user, price, type and quantity (for simplicity)
 */
@Slf4j
public class OrderBoard {

//    private ConcurrentHashMap<String, Order> map;

    /** optimised collection for concurrent reads
     * another option is CopyOnWriteArrayList - it depends on usage pattern
     * */
//    private final ConcurrentSkipListSet<Order> collection = new ConcurrentSkipListSet<Order>();
//    private final Set<Order> collection = new TreeSet<>(new OrderComparator());
    private final Set<Order> collection = new HashSet<Order>();

    /**
     *
     * @param newOrder, id is generated inside
     * @return new order id
     */
    public String registerOrder(Order newOrder) {
        //presume UUID is unique
        newOrder.setId(UUID.randomUUID().toString());
        collection.add(newOrder);
        log.info("registered " + newOrder);
        return newOrder.getId();
    }

    public void cancelOrder(Order existingOrder) {
        collection.remove(existingOrder);
    }

    //depending on usage/contract we want to provide to the users we can clone orginal set
    //or do deep clone of the objects in it as well
    //TODO make sure objects don't escape. clone them or something
    public Set<Order> getSummary() {
        return collection;
    }
}
