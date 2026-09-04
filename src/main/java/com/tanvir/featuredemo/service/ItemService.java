package com.tanvir.featuredemo.service;

import com.tanvir.featuredemo.domain.Item;
import com.tanvir.featuredemo.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /** Fetch — list all items (mirrors a client "fetch" API feature). */
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id));
    }

    /** Submit — create a new item (mirrors a client "submit" API feature). */
    public Item create(Item item) {
        item.setId(null);
        if (item.getStatus() == null) {
            item.setStatus(Item.ItemStatus.PENDING);
        }
        return itemRepository.save(item);
    }

    public Item update(Long id, Item incoming) {
        Item existing = findById(id);
        existing.setName(incoming.getName());
        existing.setDetails(incoming.getDetails());
        if (incoming.getStatus() != null) {
            existing.setStatus(incoming.getStatus());
        }
        return itemRepository.save(existing);
    }

    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found: " + id);
        }
        itemRepository.deleteById(id);
    }
}
