package org.training.pos;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import org.training.pos.domain.Item;
import org.training.pos.domain.SettleAccountItem;
import org.training.pos.dto.CartDTO;
import org.training.pos.dto.CartItemDTO;
import org.training.pos.dto.ReceiptDTO;
import org.training.pos.dto.ReceiptItemDTO;
import org.training.pos.repository.ItemRepository;

public class PosService {

  public ReceiptDTO buyItems(CartDTO cartDTO) {
    final List<SettleAccountItem> settleAccountItems = generateSettleAccountItems(cartDTO);
    BigDecimal totalPrice = calculateTotalPrice(settleAccountItems);
    BigDecimal discount = calculateDiscount(settleAccountItems);

    return new ReceiptDTO(scalePrice(totalPrice),
                          scalePrice(discount),
                          buildReceiptItems(settleAccountItems));
  }

  private BigDecimal calculateDiscount(List<SettleAccountItem> settleAccountItems) {
    return settleAccountItems.stream().map(SettleAccountItem::getDiscount)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalPrice(List<SettleAccountItem> settleAccountItems) {
    return settleAccountItems.stream().map(SettleAccountItem::getActualPrice)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private List<ReceiptItemDTO> buildReceiptItems(List<SettleAccountItem> settleAccountItems) {
    return settleAccountItems.stream().map(this::buildReceiptItem)
                                      .collect(Collectors.toList());
  }

  private ReceiptItemDTO buildReceiptItem(SettleAccountItem settleAccountItem) {
    final String count = settleAccountItem.getCount() + settleAccountItem.getUnit();
    return new ReceiptItemDTO(settleAccountItem.getName(),
                              count,
                              scalePrice(settleAccountItem.getUnitPrice()),
                              scalePrice(settleAccountItem.getActualPrice()));
  }

  private String scalePrice(BigDecimal price) {
    return price.setScale(2, RoundingMode.FLOOR).toString();
  }

  private List<SettleAccountItem> generateSettleAccountItems(CartDTO cartDTO) {
    return cartDTO.getCartItemDTOs().stream()
        .map(this::generateSettleAccountItem)
        .collect(toList());
  }

  private SettleAccountItem generateSettleAccountItem(CartItemDTO cartItemDTO) {
    Item item = ItemRepository.findById(cartItemDTO.getId())
        .orElseThrow(() -> new RuntimeException("item not found"));

    return SettleAccountItem.builder().id(item.getId())
                                                     .name(item.getName())
                                                     .unit(item.getUnit())
                                                     .unitPrice(item.getUnitPrice())
                                                     .count(new BigDecimal(cartItemDTO.getCount()))
                                                     .promotion(item.isPromotion())
                                                     .build();
  }
}
