package com.example.withJpa2.service;

import com.example.withJpa2.domain.items.Book;
import com.example.withJpa2.domain.items.Item;
import com.example.withJpa2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    public List<Item> findItem(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity){
        Item item = itemRepository.findOne(id);
        item.setItemInfo(name, price, stockQuantity);
    }


}
