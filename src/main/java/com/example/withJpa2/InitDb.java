package com.example.withJpa2;

import com.example.withJpa2.domain.*;
import com.example.withJpa2.domain.items.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member1 = Member.createMember("userA", new Address("광주", "남구", "03421"));
            em.persist(member1);

            Book book1 = new Book("JPA1 BOOK", 10000, 100, "abc", "11111-11111");
            em.persist(book1);
            Book book2 = new Book("JPA2 BOOK", 20000, 100, "bfe", "22222-22222");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = Delivery.createDelivery(member1.getAddress());

            Order order1 = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order1);
        }

        public void dbInit2() {
            Member member2 = Member.createMember("userB", new Address("부산", "금정구", "16521"));
            em.persist(member2);

            Book book1 = new Book("SPRING1 BOOK", 20000, 200, "kotlin", "33333-33333");
            em.persist(book1);
            Book book2 = new Book("SPRING2 BOOK", 40000, 300, "java", "44444-55555");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = Delivery.createDelivery(member2.getAddress());

            Order order1 = Order.createOrder(member2, delivery, orderItem1, orderItem2);
            em.persist(order1);
        }
    }
}
