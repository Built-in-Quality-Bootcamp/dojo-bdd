package org.training.pos.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettleAccountItem {

  private String id;
  private String name;
  private BigDecimal count;
  private String unit;
  private BigDecimal unitPrice;
  private boolean promotion;

  public BigDecimal getDiscount() {
    if (promotion) {
      return shouldDiscount() ? calculateDiscountForPresentOneWhenBuyTwo() : BigDecimal.ZERO;
    }

    return BigDecimal.ZERO;
  }

  public BigDecimal getActualPrice() {
    BigDecimal normalPrice = unitPrice.multiply(count);
    return normalPrice.subtract(getDiscount());
  }

  private boolean shouldDiscount() {
    return count.compareTo(BigDecimal.valueOf(3)) >= 0;
  }

  private BigDecimal calculateDiscountForPresentOneWhenBuyTwo() {
    BigDecimal discount;
    final BigDecimal promoteCount =
                                  count.divide(BigDecimal.valueOf(3), 0, RoundingMode.FLOOR);
    discount = promoteCount.multiply(unitPrice);
    return discount;
  }
}
