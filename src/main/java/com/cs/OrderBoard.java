package com.cs;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * I presume we can't have orders with the same user, price, type and quantity (for simplicity)
 */
@Slf4j
public class OrderBoard {


    /** optimised collection for concurrent reads
     * another option is CopyOnWriteArrayList - it depends on usage pattern
     * */
//    private final ConcurrentSkipListMap<String, Order> collection = new ConcurrentSkipListMap<>();
//    private final ConcurrentSkipListSet<Order> collection = new ConcurrentSkipListSet<Order>();
//    private final Set<Order> collection = new TreeSet<>(new OrderComparator());
//    private final Set<Order> collection = new HashSet<Order>();
//    private final Map<String, Order> collection = new HashMap<>();
    private ConcurrentHashMap<String, Order> collection = new ConcurrentHashMap<>();

    /**
     *
     * @param newOrder, id is generated inside
     * @return new order id
     */
    public String registerOrder(Order newOrder) {
        //I presume client is not modifying order after registered, otherwise we can clone it
        //presume UUID is unique
        newOrder.setId(UUID.randomUUID().toString());
        collection.put(newOrder.getId(), newOrder);
        log.info("registered " + newOrder);
        return newOrder.getId();
    }

    public void cancelOrder(Order existingOrder) {
        collection.remove(existingOrder.getId());
    }

    //*depending on usage/(contract we want to provide to the users)
    // we can clone the original set or do deep clone of the objects in it as well
    //* make sure objects don't escape. clone them or something
    public List<Order> getSummary() {
        List<Order> list = new ArrayList<>();
        //deep copy of objects so clients have read-only version, can't break the master storage
        collection.values().forEach(order -> list.add(new Order(order)));
        list.sort(new OrderComparator());

//        List<Order> list = collection.values().stream().map(Order::new).collect(Collectors.toList());

        return list;
//        return collection.values();
    }
}
