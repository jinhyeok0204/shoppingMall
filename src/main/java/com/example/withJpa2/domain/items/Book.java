package com.example.withJpa2.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{
    private String author;
    private String isbn;

    public Book(String name, int price, int stockQuantity, String author, String isbn){
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public Book(Long id, String name, int price, int stockQuantity, String author, String isbn){
        super(id, name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
