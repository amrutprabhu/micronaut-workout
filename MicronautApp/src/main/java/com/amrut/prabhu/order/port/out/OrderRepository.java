package com.amrut.prabhu.order.port.out;

import com.amrut.prabhu.order.domain.Order;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("select o from Order as o")
    List<Order> getAllOrders();
}
