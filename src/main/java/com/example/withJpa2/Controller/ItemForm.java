package com.example.withJpa2.Controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
