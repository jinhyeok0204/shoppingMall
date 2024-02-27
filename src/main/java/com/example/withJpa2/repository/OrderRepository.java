package com.example.withJpa2.repository;

import com.example.withJpa2.domain.*;
import com.example.withJpa2.domain.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.example.withJpa2.domain.QMember.member;
import static com.example.withJpa2.domain.QOrder.order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch){
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPAQueryFactory query = new JPAQueryFactory(em);
        return query.select(order)
                    .from(order)
                    .join(order.member, member)
                    .where(statusEq(orderSearch.getOrderStatus()),
                            nameLike(orderSearch.getMemberName()))
                    .limit(1000)
                    .fetch();
    }

    private BooleanExpression statusEq(OrderStatus status){
        if(status == null){
            return null;
        }
        return order.status.eq(status);
    }

    private BooleanExpression nameLike(String name){
        if(!StringUtils.hasText(name)){
            return null;
        }
        return member.name.like(name);
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }

    public List<OrderSimpleQueryDto> findOrderDtos(){
        return em.createQuery(
                "select new com.example.withJpa2.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o " +
                        " join o.member m" +
                        " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }

    public List<Order> findAllWithItem() {
        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d"+
                        " join fetch o.orderItemList oi" +
                        " join fetch oi.item i", Order.class)
                .getResultList();
    }
}
