package org.training.pos.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import org.training.pos.domain.Item;

@UtilityClass
public class ItemRepository {

  private static List<Item> itemsForSelling = new ArrayList<>();

  public void addItems(List<Item> items) {
    itemsForSelling.addAll(items);
  }

  public Optional<Item> findById(String id) {
    return findItemBy( item -> Objects.equals(item.getId(), id));
  }

  public Optional<Item> findByName(String name) {
    return findItemBy( item -> Objects.equals(item.getName(), name));
  }

  public void deleteAll() {
    itemsForSelling.clear();
  }

  private Optional<Item> findItemBy(Predicate<Item> itemPredicate) {
    return itemsForSelling.stream()
        .filter(itemPredicate)
        .findFirst();
  }
}
