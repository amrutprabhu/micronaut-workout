package com.amrut.prabhu.order.service;

import com.amrut.prabhu.order.domain.Order;
import com.amrut.prabhu.order.port.in.OrderDTO;
import com.amrut.prabhu.order.port.in.OrderMapper;
import com.amrut.prabhu.order.port.in.OrderService;
import com.amrut.prabhu.order.port.out.OrderRepository;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<OrderDTO> getOrder(Long id) {
        Optional<Order> orderById = orderRepository.findById(id);
        return orderById.map(mapper::toOrderDto);
    }

    @Override
    public List<OrderDTO> getOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        return orderRepository.getAllOrders()
                .stream()
                .map(mapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        Order order = mapper.toOrder(orderDTO);
        order.setId(null);
        Order saved = orderRepository.save(order);
        return mapper.toOrderDto(saved);
    }
}
