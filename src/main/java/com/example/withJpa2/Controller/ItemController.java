package com.example.withJpa2.Controller;

import com.example.withJpa2.domain.items.Book;
import com.example.withJpa2.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }

    /**
     *
     * @param itemId
     * @param model
     * 상품 수정 폼
     */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = BookForm.createBookForm(
                item.getId(), item.getName(), item.getPrice(), item.getStockQuantity(),
                item.getAuthor(), item.getIsbn()
        );

        model.addAttribute("form", form);
        return "items/upadteItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId ,@ModelAttribute("form") BookForm form){

        Book book = new Book(form.getId(), form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }
}
