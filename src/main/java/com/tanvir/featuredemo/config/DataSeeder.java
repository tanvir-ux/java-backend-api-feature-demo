package com.tanvir.featuredemo.config;

import com.tanvir.featuredemo.domain.Item;
import com.tanvir.featuredemo.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedItems(ItemRepository itemRepository) {
        return args -> {
            if (itemRepository.count() > 0) {
                return;
            }
            Item welcome = new Item();
            welcome.setName("Sample item");
            welcome.setDetails("Demo fetch/submit resource — real feature work maps this to the client's tables.");
            welcome.setStatus(Item.ItemStatus.PENDING);
            itemRepository.save(welcome);
        };
    }
}
