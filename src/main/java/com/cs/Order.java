package com.cs;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode//(onlyExplicitlyIncluded = true)
public class Order {

    //we presume we are going to meaningfully use only object with ID generated
    @EqualsAndHashCode.Exclude
    String id;

    String userId;
    OrderType type;

    /** in kg */
    Double quantity;

    //TODO check BigDecimal scale to be 2
    /** in GBP */
    BigDecimal price;

    //clonning constructor
    public Order(Order order) {
        id = order.getId();
        userId = order.getUserId();
        type = order.getType();
        quantity = order.getQuantity();
        price = order.getPrice();
    }
}
