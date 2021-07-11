package com.amrut.prabhu.order.adapter.in.web;

import com.amrut.prabhu.order.port.in.OrderDTO;
import com.amrut.prabhu.order.port.in.OrderService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Put;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class WebController {

    final private OrderService orderService;

    public WebController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Put
    public HttpResponse<OrderDTO> addOrder(@Body @Valid OrderDTO orderDTO) {
        System.out.println("--- Function called with :" + orderDTO.toString());
        OrderDTO savedOrder = orderService.addOrder(orderDTO);
        return HttpResponse.ok(savedOrder);
    }

    @Error(exception = RuntimeException.class, global = true)
    public HttpResponse<String> handleErrors(HttpRequest request, Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
        throwable.printStackTrace();
        return HttpResponse.serverError().body("Something Went wrong.");
    }

    @Error(exception = ConstraintViolationException.class, global = true)
    public HttpResponse<String> handleValidationErrors(HttpRequest request, ConstraintViolationException throwable) {
        Optional<String> errors = throwable.getConstraintViolations()
                .stream()
                .map(vol -> vol.getMessage())
                .reduce((a, b) -> a + b);
        System.err.println("Error: " + throwable.getMessage());
        return HttpResponse.badRequest(errors.get());
    }

}
