package com.amrut.prabhu.order.adapter.in.web;

import com.amrut.prabhu.order.port.in.OrderDTO;
import com.amrut.prabhu.order.port.in.OrderService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("/order")
public class WebController {

    private final OrderService orderService;

    public WebController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Get("/{id}")
    public HttpResponse<OrderDTO> getOrder(@PathVariable("id") Long id) {

        Optional<OrderDTO> mayBeOrder = orderService.getOrder(id);
        if (mayBeOrder.isPresent()) {
            return HttpResponse.created(mayBeOrder.get());
        }
        return HttpResponse.notFound();
    }

    @Get
    public HttpResponse<List<OrderDTO>> getAllOrders() {
        return HttpResponse.ok(this.orderService.getOrders());
    }

    @Put
    public HttpResponse<OrderDTO> addOrder(@Body OrderDTO orderDTO) {
        OrderDTO savedOrder = orderService.addOrder(orderDTO);
        return HttpResponse.ok(savedOrder);
    }

    @Error(exception = RuntimeException.class, global = true)
    public HttpResponse<String> handleErrors(HttpRequest request) {
        return HttpResponse.serverError().body("Something Went wrong.");

    }

}
