package com.amrut.prabhu;

import com.amrut.prabhu.order.domain.Order;
import com.amrut.prabhu.order.port.in.OrderDTO;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MicronautAppTest implements TestPropertyProvider {

    @Inject
    EntityManager entityManager;

    @Container
    static GenericContainer container = new GenericContainer("mysql:5.7")
            .withEnv("MYSQL_ROOT_PASSWORD", "root")
            .withEnv("MYSQL_DATABASE", "ORDER")
            .withExposedPorts(3306);

    @Inject
    @Client("/")
    HttpClient client;

    @BeforeEach
    void beforeEach() {
        this.entityManager.createQuery("delete from Order");
    }

    @Test
    void testGetOrder() {
        List response = client.toBlocking().retrieve(HttpRequest.GET("/order"), Argument.of(List.class, Order.class));
        assertThat(response).isEmpty();
    }

    @Test
    void testPersistAndGetAllOrders() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setName("OrderName");
        HttpRequest<OrderDTO> orderHttpRequest = HttpRequest.PUT("/order", orderDTO);
        OrderDTO savedOrder = client.toBlocking().retrieve(orderHttpRequest, OrderDTO.class);

        assertThat(savedOrder.getId()).isNotNull();

        List response = client.toBlocking().retrieve(HttpRequest.GET("/order"), Argument.of(List.class, Order.class));
        assertThat(response).isNotEmpty();
    }

    @Test
    void testPersistAndGetOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setName("OrderName");
        HttpRequest<OrderDTO> orderHttpRequest = HttpRequest.PUT("/order", orderDTO);
        OrderDTO savedOrder = client.toBlocking().retrieve(orderHttpRequest, OrderDTO.class);

        assertThat(savedOrder.getId()).isNotNull();

        OrderDTO response = client.toBlocking().retrieve(HttpRequest.GET("/order/"+savedOrder.getId()), OrderDTO.class);
        assertThat(response).isNotNull();
    }

    @Nonnull
    @Override
    public Map<String, String> getProperties() {
        return Map.of("datasources.default.url", "jdbc:mysql://" + container.getHost() + ":" + container.getMappedPort(3306) + "/ORDER");
    }
}
