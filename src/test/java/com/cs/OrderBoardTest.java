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

        Order user1 = Order.builder().userId("user1").type(BUY).quantity(5.5).price(new BigDecimal("55.00")).build();
        List<Order> list = Arrays.asList(
                user1,
                Order.builder().userId("user2").type(BUY).quantity(4.5).price(new BigDecimal("45.00")).build(),
                Order.builder().userId("user3").type(BUY).quantity(5.5).price(new BigDecimal("55.00")).build(),
                Order.builder().userId("user4").type(BUY).quantity(6.5).price(new BigDecimal("65.00")).build(),

                Order.builder().userId("user8").type(SELL).quantity(15.5).price(new BigDecimal("155.00")).build(),
                Order.builder().userId("user5").type(SELL).quantity(14.5).price(new BigDecimal("145.00")).build(),
                Order.builder().userId("user6").type(SELL).quantity(15.5).price(new BigDecimal("155.00")).build(),
                Order.builder().userId("user7").type(SELL).quantity(16.5).price(new BigDecimal("165.00")).build(),

                Order.builder().userId("user9").type(SELL).quantity(33.5).price(new BigDecimal("16.00")).build(),
                Order.builder().userId("user10").type(SELL).quantity(4d).price(new BigDecimal("200.00")).build(),
                Order.builder().userId("user11").type(BUY).quantity((double)200).price(new BigDecimal("8.00")).build(),
                Order.builder().userId("user12").type(BUY).quantity((double)3).price(new BigDecimal("800.00")).build()

        );

        list.forEach(o -> board.registerOrder(o));

        Collection<Order> summary = board.getSummary();
        Assert.assertEquals(12, summary.size());

        List<String> sortedValues = summary.stream().map(order -> order.getType() + ":" + order.getPrice()).collect(Collectors.toList());
        log.info("sorted values" + sortedValues);
        assertEquals("[SELL:16.00, SELL:145.00, SELL:155.00, SELL:155.00, SELL:165.00, SELL:200.00, " +
                        "BUY:800.00, BUY:65.00, BUY:55.00, BUY:55.00, BUY:45.00, BUY:8.00]",
                sortedValues.toString());
        summary.remove(user1);
        Assert.assertEquals(11, summary.size());
        Assert.assertEquals(12, board.getSummary().size());

    }
}