package com.amrut.prabhu.order.port.in;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderDTO> getOrder(Long id);

    List<OrderDTO> getOrders();

    OrderDTO addOrder(OrderDTO orderDTO);
}
