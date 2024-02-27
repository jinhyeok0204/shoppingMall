package com.example.withJpa2.service;

import com.example.withJpa2.domain.*;
import com.example.withJpa2.domain.exceptions.NotEnoughStockException;
import com.example.withJpa2.domain.items.Album;
import com.example.withJpa2.domain.items.Book;
import com.example.withJpa2.domain.items.Item;
import com.example.withJpa2.repository.OrderRepository;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Test
    void 상품주문() throws Exception{
        //given

        Member member = Member.createMember("Jeon", new Address("Gwangju", "namgu", "1000"));
        em.persist(member);

        Item item = new Book("열혈 자바", 20000, 10, "윤성우", "1234-1234");
        em.persist(item);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(item.getStockQuantity()).isEqualTo(10-orderCount);
    }

    @Test
    void 상품재고_수량초과() throws NotEnoughStockException{

        //given
        Member member = Member.createMember("Jeon", new Address("Gwangju", "namgu", "1000"));

        em.persist(member);

        Item item = new Book("열혈 자바", 20000, 10, "윤성우", "1234-1234");
        em.persist(item);

        int orderCount = 12;
        //then
        assertThrows(NotEnoughStockException.class,
                () -> {orderService.order(member.getId(), item.getId(), orderCount);
        });

    }
    @Test
    void 주문취소() throws Exception{
        //given
        Member member = Member.createMember("Jeon", new Address("Gwangju", "namgu", "1000"));
        em.persist(member);

        Delivery delivery = Delivery.createDelivery(member.getAddress());

        Item item = new Album("newJeans 1st", 15000, 5, "newJeans", "r&b");
        em.persist(item);
        int orderCount = 3;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findOne(orderId);
        //then
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(5);
    }
}