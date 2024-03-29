package com.example.withJpa2.service;

import com.example.withJpa2.domain.*;
import com.example.withJpa2.domain.items.Item;
import com.example.withJpa2.repository.ItemRepository;
import com.example.withJpa2.repository.MemberRepository;
import com.example.withJpa2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = Delivery.createDelivery(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member,delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
    //검색

    public List<Order> searchOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
