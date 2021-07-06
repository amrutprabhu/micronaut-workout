package com.amrut.prabhu.order.service;

import com.amrut.prabhu.order.domain.Order;
import com.amrut.prabhu.order.port.in.OrderDTO;
import com.amrut.prabhu.order.port.in.OrderService;
import com.amrut.prabhu.order.port.out.OrderReposittory;
import org.mapstruct.factory.Mappers;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class OrderServiceImpl implements OrderService {

    private final OrderReposittory orderReposittory;
    private final OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    public OrderServiceImpl(OrderReposittory orderReposittory) {
        this.orderReposittory = orderReposittory;
    }

    @Override
    public Optional<OrderDTO> getOrder(Long id) {
        Optional<Order> byId = orderReposittory.findById(id);
        return byId.map(mapper::toOrderDto);
    }

    @Override
    public List<OrderDTO> getOrders() {

        return orderReposittory.getAllOrders()
                .stream().map(mapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = mapper.toOrder(orderDTO);
        Order saved = orderReposittory.save(order);
        return mapper.toOrderDto(saved);
    }
}
