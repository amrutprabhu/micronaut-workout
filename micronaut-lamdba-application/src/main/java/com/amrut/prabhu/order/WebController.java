package com.amrut.prabhu.order;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@Controller
public class WebController {

    final private OrderRepository orderRepository;

    public WebController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Get("/{id}")
    public HttpResponse<Order> getOrder(@PathVariable("id") Long id) {

        Optional<Order> mayBeOrder = this.orderRepository.findById(id);
        if (mayBeOrder.isPresent()) {
            return HttpResponse.created(mayBeOrder.get());
        }
        return HttpResponse.notFound();
    }

    @Get
    public HttpResponse<List<Order>> getAllOrders() {
        return HttpResponse.ok(this.orderRepository.getAllOrders());
    }

    @Put
    public HttpResponse<Order> addOrder(@Body @Valid Order order) {
        Order savedOrder = orderRepository.save(order);
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
