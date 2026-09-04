package com.tanvir.featuredemo.api;

import com.tanvir.featuredemo.domain.Item;
import com.tanvir.featuredemo.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Demo REST feature: GET list (fetch) + POST create (submit), plus get/update/delete.
 * Real client work would map these to their tables and business rules.
 */
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> list() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@Valid @RequestBody Item item) {
        return itemService.create(item);
    }

    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @Valid @RequestBody Item item) {
        return itemService.update(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
