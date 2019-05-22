package com.cs;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.cs.OrderType.BUY;
import static com.cs.OrderType.SELL;
import static org.junit.Assert.*;

@Slf4j
public class OrderBoardTest {

    OrderBoard board = new OrderBoard();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        board = new OrderBoard();
    }

    @Test
    public void testAddRemove() throws Exception {
        //TODO check BigDecimal scale to be 2
        Order order = Order.builder().userId("user1").type(BUY).quantity(5.5).
                price(new BigDecimal("300.00")).build();
        Assert.assertEquals(0, board.getSummary().size());
        board.registerOrder(order);
        Assert.assertEquals(1, board.getSummary().size());

        log.info("added - "+board.getSummary());
        board.cancelOrder(order);
        log.info("removed - "+board.getSummary());

        Assert.assertEquals(0, board.getSummary().size());
    }

    @Test
    public void testMany() throws Exception {
        Assert.assertEquals(0, board.getSummary().size());

        Order user1 = Order.builder().userId("user1").type(BUY).quantity(6d).price(new BigDecimal("55.00")).build();
        Order user4 = Order.builder().userId("user4").type(BUY).quantity(6.5).price(new BigDecimal("65.00")).build();
        List<Order> list = Arrays.asList(
                user1,
                Order.builder().userId("user2").type(BUY).quantity(4.5).price(new BigDecimal("45.00")).build(),
                Order.builder().userId("user3").type(BUY).quantity(5d).price(new BigDecimal("55.00")).build(),
                user4,

                Order.builder().userId("user8").type(SELL).quantity(15d).price(new BigDecimal("155.00")).build(),
                Order.builder().userId("user5").type(SELL).quantity(14.5).price(new BigDecimal("145.00")).build(),
                Order.builder().userId("user6").type(SELL).quantity(16d).price(new BigDecimal("155.00")).build(),
                Order.builder().userId("user7").type(SELL).quantity(16.5).price(new BigDecimal("165.00")).build(),

                Order.builder().userId("user9").type(SELL).quantity(33.5).price(new BigDecimal("16.00")).build(),
                Order.builder().userId("user10").type(SELL).quantity(4d).price(new BigDecimal("200.00")).build(),
                Order.builder().userId("user11").type(BUY).quantity((double)200).price(new BigDecimal("8.00")).build(),
                Order.builder().userId("user12").type(BUY).quantity((double)3).price(new BigDecimal("800.00")).build()

        );

        list.forEach(o -> board.registerOrder(o));

        List<Order> summary = board.getSummary();
        log.info("summary=" + summary);
//        List<String> sortedValues = summary.stream().map(order -> order.getType() + ":" + order.getPrice()).collect(Collectors.toList());
        List<String> sortedValues = summary.stream().map(order -> order.getType() + ":" + order.getQuantity() + " kg for £"+ order.getPrice()
        + " // " + order.getUserId()).collect(Collectors.toList());

        log.info("sorted values= " + sortedValues);
        assertEquals("[SELL:33.5 kg for £16.00 // user9, " +
                        "SELL:14.5 kg for £145.00 // user5, " +
                        "SELL:31.0 kg for £155.00 // [user6, user8], " +
                        "SELL:16.5 kg for £165.00 // user7, " +
                        "SELL:4.0 kg for £200.00 // user10, " +
                        "BUY:3.0 kg for £800.00 // user12, " +
                        "BUY:6.5 kg for £65.00 // user4, " +
                        "BUY:11.0 kg for £55.00 // [user1, user3], " +
                        "BUY:4.5 kg for £45.00 // user2, " +
                        "BUY:200.0 kg for £8.00 // user11]",
                sortedValues.toString());

        //test deep copy. update
        List<Order> list1 = summary.stream().filter(order -> order.getUserId().equals("user4")).collect(Collectors.toList());
        assertEquals(1, list1.size());
        Order boardOrder4 = list1.get(0);
        boardOrder4.setPrice(new BigDecimal("24.00"));
        boardOrder4.setQuantity(19d);
        boardOrder4.setType(SELL);

        summary = board.getSummary();
        List<Order> list2 = summary.stream().filter(order -> order.getUserId().equals("user4")).collect(Collectors.toList());
        assertEquals(1, list2.size());
        Order boardOrder2 = list2.get(0);

        Assert.assertEquals(new BigDecimal("65.00"), boardOrder2.getPrice());
        Assert.assertEquals(6.5, boardOrder2.getQuantity(), 0.000002d);
        Assert.assertEquals(BUY, boardOrder2.getType());

        //test deep copy. removal
        Assert.assertEquals(10, summary.size());
        summary.remove(user4);
        Assert.assertEquals(9, summary.size());
        Assert.assertEquals(10, board.getSummary().size());

    }
}