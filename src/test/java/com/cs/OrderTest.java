package com.cs;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.cs.OrderType.BUY;
import static com.cs.OrderType.SELL;
import static org.junit.Assert.*;

@Slf4j //TODO sort logging. it is not picking up the logback.xml config
public class OrderTest {

//    private static final Logger log = LoggerFactory.getLogger(OrderTest.class);
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void standard() {
        //test lombok
        Order order = new Order();
        order.setUserId("user1");

        Order order2 = new Order();
        order2.setUserId("user2");
        assertEquals(order, order2);

        log.info(order.toString());
        log.info(""+order.hashCode());
        order.setId(UUID.randomUUID().toString());
        log.info("order1 ="+order.hashCode());
        assertEquals("user1", order.getUserId());

        order2.setId(UUID.randomUUID().toString());
        log.info("order2 ="+order2.hashCode());

        assertNotEquals(order, order2);

    }

    @Test
    public void compareTo() {
        //test lombok
        List<Order> list = Arrays.asList(
                Order.builder().userId("user1").type(BUY).quantity(5.5).build(),
                Order.builder().userId("user2").type(BUY).quantity(4.5).build(),
                Order.builder().userId("user3").type(BUY).quantity(5.5).build(),
                Order.builder().userId("user4").type(BUY).quantity(6.5).build(),

                Order.builder().userId("user8").type(SELL).quantity(15.5).build(),
                Order.builder().userId("user5").type(SELL).quantity(14.5).build(),
                Order.builder().userId("user6").type(SELL).quantity(15.5).build(),
                Order.builder().userId("user7").type(SELL).quantity(16.5).build()

        );
        Collections.sort(list, new OrderComparator());
        list.forEach(o -> {
            o.setId(UUID.randomUUID().toString());
            log.info(o.getUserId() + ", hash" + o.hashCode());
        });
//        System.out.println(list);
        log.info(list.toString());
        //TODO test the order of orders is right
    }
}