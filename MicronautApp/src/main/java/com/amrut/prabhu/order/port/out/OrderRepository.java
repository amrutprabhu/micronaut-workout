package com.amrut.prabhu.order.port.out;

import com.amrut.prabhu.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    Order save(Order order);

    List<Order> getAllOrders();
}
