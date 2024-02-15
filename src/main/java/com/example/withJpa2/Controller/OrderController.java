package com.example.withJpa2.Controller;

import com.example.withJpa2.domain.Member;
import com.example.withJpa2.domain.Order;
import com.example.withJpa2.domain.OrderSearch;
import com.example.withJpa2.domain.items.Item;
import com.example.withJpa2.service.ItemService;
import com.example.withJpa2.service.MemberService;
import com.example.withJpa2.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> memberList = memberService.findAll();
        List<Item> items = itemService.findItem();

        model.addAttribute("members", memberList);
        model.addAttribute("items", items);
        return "orders/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId")Long memberId, @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.searchOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId")Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
