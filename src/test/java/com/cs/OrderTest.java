package com.cs;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cs.OrderType.BUY;
import static com.cs.OrderType.SELL;
import static org.junit.Assert.*;

@Slf4j
public class OrderTest {

    @Test
    public void testEqualsHashcode() {
        //test lombok
        Order order = new Order();
        order.setUserId("user2");

        Order order2 = new Order();
        order2.setUserId("user2");
        assertEquals(order, order2);

        log.info(order.toString());
        log.info(""+order.hashCode());
        order.setId(UUID.randomUUID().toString());
        log.info("order1 ="+order.hashCode());
        assertEquals("user2", order.getUserId());

        order2.setId(UUID.randomUUID().toString());
        log.info("order2 ="+order2.hashCode());

        assertEquals(order, order2);
    }

    @Test
    public void testComparator() {
        List<Order> list = Arrays.asList(
                Order.builder().userId("user1").type(BUY).quantity(5.5).price(new BigDecimal("55.00")).build(),
                Order.builder().userId("user2").type(BUY).quantity(4.5).price(new BigDecimal("45.00")).build(),
                Order.builder().userId("user3").type(BUY).quantity(5.5).price(new BigDecimal("55.00")).build(),
                Order.builder().userId("user4").type(BUY).quantity(6.5).price(new BigDecimal("65.00")).build(),

                Order.builder().userId("user8").type(SELL).quantity(15.5).price(new BigDecimal("155.00")).build(),
                Order.builder().userId("user5").type(SELL).quantity(14.5).price(new BigDecimal("145.00")).build(),
                Order.builder().userId("user6").type(SELL).quantity(15.5).price(new BigDecimal("155.00")).build(),
                Order.builder().userId("user7").type(SELL).quantity(16.5).price(new BigDecimal("165.00")).build()

        );
        list.sort(new OrderComparator());
        assertEquals("[user5, user8, user6, user7, user4, user1, user3, user2]",list.stream().map(Order::getUserId).collect(Collectors.toList()).toString());
        list.forEach(o -> {
            o.setId(UUID.randomUUID().toString());
            log.info(o.getUserId() + ", hash" + o.hashCode());
        });
        log.info(list.toString());
    }
}