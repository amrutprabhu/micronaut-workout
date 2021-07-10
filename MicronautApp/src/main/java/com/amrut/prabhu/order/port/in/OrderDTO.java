package com.amrut.prabhu.order.port.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class OrderDTO {

    @JsonProperty("orderName")
    private String name;

    @JsonProperty("orderId")
    private Long id;

    public OrderDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
