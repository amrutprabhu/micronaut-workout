package com.amrut.prabhu.order.service;

import com.amrut.prabhu.order.domain.Order;
import com.amrut.prabhu.order.port.in.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);

    OrderDTO toOrderDto(Order order);
}
