package com.cs;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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

        list.forEach(o -> board.registerOrder(o));

        Assert.assertEquals(8, board.getSummary().size());
    }
}