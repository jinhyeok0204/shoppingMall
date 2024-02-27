package com.example.withJpa2.api;

import com.example.withJpa2.domain.Address;
import com.example.withJpa2.domain.Order;
import com.example.withJpa2.domain.OrderSearch;
import com.example.withJpa2.domain.OrderStatus;
import com.example.withJpa2.repository.OrderRepository;
import com.example.withJpa2.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/*
* xToOne (ManyToOne, OneToOne
* Order -> Member
* Order -> Delivery
* */

@RestController
@RequiredArgsConstructor
public class OrderSimpleAPiController {
    private final OrderRepository orderRepository;

    // 문제 1. 무한 루프에 빠진다.. Order -> Member -> Order -> Member ....
    // -> 해결 : 따라서 양방향 연관관계에서 한쪽은 @JsonIgnore을 해줘야한다.

    //문제 2. 객체들이 지연로딩으로 설정되었기 때문에, 객체들이 초기화되지않은 프록시 상태(ByteBuddy~~)이고, jackson라이브러리가 이를 인식하지 못해 오류 발생
    // -> 해결 : Hibernate5JakarataModule 모듈 설치

    // 근데 이렇게 하지마라 API외부 유출하기 때문... + 필요없는 것 까지 다 긁어온다 -> 성능낭비 엄청나다
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        return orderRepository.findAll(new OrderSearch());
    }

    // 엔티티를 DTO로 변환하는 일반적인 방 ->
    // n+1 문제 발생함 .. 지연로딩 때문
    // Order 2개
    // N + 1 -> 1(orders가져온 쿼리) + 회원 N번(2) + 배송 N번(2) => 쿼리 5번 나가버림 !! -> 성능문제..
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){

        return orderRepository.findAll(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .toList();
    }

    // v4는 성능이 제일 좋다 그렇지만 요즘 네트워크 성능에 큰 영향은 주지않는다.
    // 코드 재사용성이 줄어든다 ( 특정한 API )에 맞춰져 있기때문에.. API에 종속적인 코드가 레퍼지토리에 들어감
    // V3와 트레이드 오프
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderRepository.findOrderDtos();
    }
    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        SimpleOrderDto(Order o){
            this.orderId = o.getId();
            this.name = o.getMember().getName(); // 멤버 프록시 초기화
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getDelivery().getAddress(); // 딜리버리 프록시 초기화
        }
    }
}
