package com.amrut.prabhu.order.adapter.out.persistence;

import com.amrut.prabhu.order.domain.Order;
import com.amrut.prabhu.order.port.out.OrderRepository;
import io.micronaut.data.annotation.Repository;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @ReadOnly
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    @Transactional
    public Order save(Order order) {
        Order pro = new Order(null, order.getName());
        entityManager.persist(pro);
        return pro;
    }

    @Override
    @ReadOnly
    public List<Order> getAllOrders() {
        String qlString = "SELECT o FROM Order as o";
        TypedQuery<Order> namedQuery = this.entityManager.createQuery(qlString, Order.class);
        List<Order> resultList = namedQuery.getResultList();
        return resultList;
    }
}
