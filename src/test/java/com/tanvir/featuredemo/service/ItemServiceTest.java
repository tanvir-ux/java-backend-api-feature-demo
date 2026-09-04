package com.tanvir.featuredemo.service;

import com.tanvir.featuredemo.domain.Item;
import com.tanvir.featuredemo.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item sample;

    @BeforeEach
    void setUp() {
        sample = new Item();
        sample.setId(1L);
        sample.setName("Demo item");
        sample.setDetails("Unit test payload");
        sample.setStatus(Item.ItemStatus.PENDING);
    }

    @Test
    void findAllReturnsRepositoryResults() {
        when(itemRepository.findAll()).thenReturn(List.of(sample));

        List<Item> result = itemService.findAll();

        assertEquals(1, result.size());
        assertEquals("Demo item", result.get(0).getName());
        verify(itemRepository).findAll();
    }

    @Test
    void createClearsIdAndDefaultsStatus() {
        Item incoming = new Item();
        incoming.setId(99L);
        incoming.setName("Submitted item");
        incoming.setDetails("From POST /api/items");
        incoming.setStatus(null);

        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> {
            Item toSave = invocation.getArgument(0);
            Item persisted = new Item();
            persisted.setId(2L);
            persisted.setName(toSave.getName());
            persisted.setDetails(toSave.getDetails());
            persisted.setStatus(toSave.getStatus());
            return persisted;
        });

        Item created = itemService.create(incoming);

        ArgumentCaptor<Item> captor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(captor.capture());
        assertNull(captor.getValue().getId());
        assertEquals(Item.ItemStatus.PENDING, captor.getValue().getStatus());
        assertEquals("Submitted item", created.getName());
        assertEquals(2L, created.getId());
        assertEquals(Item.ItemStatus.PENDING, created.getStatus());
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(itemRepository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> itemService.findById(42L));
    }
}
