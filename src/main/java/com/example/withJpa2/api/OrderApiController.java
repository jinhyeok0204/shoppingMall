package com.example.withJpa2.api;

import com.example.withJpa2.domain.*;
import com.example.withJpa2.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
   private final OrderRepository orderRepository;

   @GetMapping("api/v2/orders")
   public List<OrderDto> ordersV2(){
      List<Order> orders = orderRepository.findAll(new OrderSearch());
      return orders.stream().map(OrderDto::new).toList();
   }

   @GetMapping("api/v3/orders")
   public List<OrderDto> ordersV3(){
      List<Order> orders = orderRepository.findAllWithItem();
      return orders.stream().map(OrderDto::new).toList();
   }

   @GetMapping("api/v3.1/orders")
   public List<OrderDto> ordersV3_page(){
      List<Order> orders = orderRepository.findAllWithMemberDelivery();

   }
   @Data
   static class OrderDto{
      private Long orderId;
      private String name;
      private LocalDateTime orderDate;
      private OrderStatus orderStatus;
      private Address address;
      private List<OrderItemDto> orderItemList;

      public OrderDto(Order o){
         this.orderId = o.getId();
         this.name = o.getMember().getName();
         this.orderDate = o.getOrderDate();
         this.orderStatus = o.getStatus();
         this.address = o.getDelivery().getAddress();
         this.orderItemList = o.getOrderItemList()
                                 .stream()
                                 .map(OrderItemDto::new)
                                 .toList();
      }
   }

   @Data
   static class OrderItemDto{
      private String itemName;
      private int orderPrice;
      private int count;

      OrderItemDto(OrderItem orderItem){
         this.itemName = orderItem.getItem().getName();
         this.orderPrice = orderItem.getOrderPrice();
         this.count = orderItem.getCount();
      }
   }
}
