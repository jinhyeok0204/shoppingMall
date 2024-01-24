package com.example.withJpa2.domain;

import com.example.withJpa2.domain.items.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_item_id")
    private CategoryItem categoryItem;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name=" parent_id")
    private Category parent;

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
