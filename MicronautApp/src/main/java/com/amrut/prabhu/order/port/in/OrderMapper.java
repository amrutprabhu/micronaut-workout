package com.amrut.prabhu.order.port.in;

import com.amrut.prabhu.order.domain.Order;
import org.mapstruct.Mapper;

import javax.inject.Singleton;

@Singleton
@Mapper(componentModel = "jsr330")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);

    OrderDTO toOrderDto(Order order);
}
