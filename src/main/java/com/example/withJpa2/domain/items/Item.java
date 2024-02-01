package com.example.withJpa2.domain.items;

import com.example.withJpa2.domain.Category;
import com.example.withJpa2.domain.CategoryItem;
import com.example.withJpa2.domain.OrderItem;
import com.example.withJpa2.domain.exceptions.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter(value = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@DiscriminatorColumn(name="dtype")
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item")
    private OrderItem orderItem;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItemList = new ArrayList<>();

    protected Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    protected Item(Long id, String name, int price, int stockQuantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void setItemInfo(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    //==비즈니스 로직==//

    // 재고 늘리기
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    // 재고 줄이기
    public void removeStock(int quantity){
        if (this.stockQuantity < quantity) {
            throw new NotEnoughStockException("need More Stock");
        }
        this.stockQuantity -= quantity;
    }
}
