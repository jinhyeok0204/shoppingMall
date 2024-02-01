package com.example.withJpa2.Controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookForm extends ItemForm{

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public static BookForm createBookForm(Long id, String name, int price, int stockQuantity, String author, String isbn){
        BookForm bookForm = new BookForm();
        bookForm.setId(id);
        bookForm.setName(name);
        bookForm.setPrice(price);
        bookForm.setStockQuantity(stockQuantity);
        bookForm.setAuthor(author);
        bookForm.setAuthor(isbn);

        return bookForm;
    }

}
