package com.cs;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode//(onlyExplicitlyIncluded = true)
public class Order {

    //TODO check and remove this bit - we presume we are going to meaningfully use only object with ID generated
//    @EqualsAndHashCode.Include
    String id;

    String userId;
    OrderType type;

    /** in kg */
    Double quantity;

    /** in GBP */
    BigDecimal price;

}
