package com.amrut.prabhu;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;

import javax.inject.Inject;

@Introspected
public class OrderRequestHandler extends MicronautRequestHandler<Order, Order> {

    @Inject
    private OrderRepository orderRepository;

    @Override
    public Order execute(Order input) {
        System.out.println("-- input is here"+ input.toString());
        Order save = orderRepository.save(input);
        System.out.println("-- output is here"+ save.toString());
        return save;
    }
}
