package org.training.pos.junit;

import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.training.pos.domain.Item;
import org.training.pos.dto.CartDTO;
import org.training.pos.dto.CartItemDTO;
import org.training.pos.repository.ItemRepository;

public class PosBaseTest {

  @Before
  public void setUp() {
    Item cola = new Item("ITEM000001", "可乐", "瓶", BigDecimal.valueOf(3.00), false);
    Item apple = new Item("ITEM000002", "苹果", "斤", BigDecimal.valueOf(1.00), false);
    Item pen = new Item("ITEM000003", "圆珠笔", "支", BigDecimal.valueOf(2.00), false);
    Item cup = new Item("ITEM000004", "杯子", "个", BigDecimal.valueOf(4.00), false);

    ItemRepository.addItems(Arrays.asList(cola, apple, pen, cup));
  }

  @After
  public void tearDown() {
    ItemRepository.deleteAll();
  }

  protected CartDTO selectedMultipleItems() {
    final CartItemDTO colaItem = CartItemDTO.builder().id("ITEM000001").unit("瓶").count("3").name("可乐")
        .build();
    final CartItemDTO appleItem = CartItemDTO.builder().id("ITEM000002").unit("斤").count("2").name("苹果")
        .build();
    final CartItemDTO penItem = CartItemDTO.builder().id("ITEM000003").unit("支").count("5").name("圆珠笔")
        .build();
    final CartItemDTO cupItem = CartItemDTO.builder().id("ITEM000004").unit("个").count("7").name("杯子")
        .build();

    final CartDTO cartDTO = new CartDTO();
    cartDTO.getCartItemDTOs().add(colaItem);
    cartDTO.getCartItemDTOs().add(appleItem);
    cartDTO.getCartItemDTOs().add(penItem);
    cartDTO.getCartItemDTOs().add(cupItem);
    return cartDTO;
  }

  protected CartDTO selectedSingleItem() {
    final CartItemDTO colaItem = CartItemDTO.builder().id("ITEM000001").unit("瓶").count("3").name("可乐")
        .build();
    final CartDTO cartDTO = new CartDTO();
    cartDTO.getCartItemDTOs().add(colaItem);
    return cartDTO;
  }

  protected void promoteItem(String name) {
    ItemRepository.findByName(name).ifPresent(item -> item.setPromotion(true));
  }
}
