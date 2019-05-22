package com.cs;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * this is thread safe implementation
 * I presume we can't have orders with the same user, price, type and quantity (for simplicity)
 */
@Slf4j
public class OrderBoard {


    /**
     * optimised collection for concurrent use. other collections could benefit with other aspects like performance.
     */
    private final ConcurrentHashMap<String, Order> collection = new ConcurrentHashMap<>();

    /**
     * @param newOrder, id is generated inside
     * @return new order id
     */
    public String registerOrder(Order newOrder) {
        //I presume client is not modifying order after registered, otherwise we can clone it.
        // I presume UUID is unique
        newOrder.setId(UUID.randomUUID().toString());
        collection.put(newOrder.getId(), newOrder);
        log.info("registered " + newOrder);
        return newOrder.getId();
    }

    public void cancelOrder(Order existingOrder) {
        collection.remove(existingOrder.getId());
    }

    //*depending on usage/(contract we want to provide to the users)
    // we can clone the original set or do deep clone of the objects in it as well for thread-safe use.
    //* I am making sure objects don't escape by cloning them.
    public List<Order> getSummary() {
        final List<Order> list = new ArrayList<>();
        //deep copy of objects so clients have read-only version, can't break the master storage

        Map<BigDecimal, List<Order>> map = collection.values().stream().collect(groupingBy(Order::getPrice));
        map.forEach((bigDecimal, orders) -> {
            if (orders.size() == 1) {
                list.add(new Order(orders.get(0)));
            } else {
                list.add(new GroupedOrder(orders.stream().map(Order::new).collect(Collectors.toList())));
            }
        });

        list.sort(new OrderComparator());

        return list;
    }
    //trade off is that new collection is created and sorted every time, although it can be cached/made thread safe

    //Another option if clients are going to use orderBoard safely - is to use Guava collection or ConcurrentSkipListMap
    // that maintains ordered values in the map initially.

    //There should be a way to optimise current solution with Guava / other clever collections
}
