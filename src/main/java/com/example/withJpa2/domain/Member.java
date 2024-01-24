package com.example.withJpa2.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    /** 연관 관계 편의 메서드**/
    public Long takeOrder(Order order){
        order.setMember(this);
        this.orderList.add(order);
        return order.getId();
    }
    public static Member createMember(String name, Address address){
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        return member;
    }
}
