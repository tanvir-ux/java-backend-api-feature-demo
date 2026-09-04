package com.tanvir.featuredemo.repository;

import com.tanvir.featuredemo.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
