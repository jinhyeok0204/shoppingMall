package com.example.withJpa2.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("M")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item{
    private String director;

    private String actor;
    private String genre;

    public Movie(String name, int price, int stockQuantity, String director, String actor, String genre){
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
        this.genre = genre;
    }
}
