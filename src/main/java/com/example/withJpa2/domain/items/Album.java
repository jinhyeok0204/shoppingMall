package com.example.withJpa2.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("A")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{

    private String artist;
    private String etc;

    public Album(String name, int price, int stockQuantity, String artist, String etc){
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }
}
