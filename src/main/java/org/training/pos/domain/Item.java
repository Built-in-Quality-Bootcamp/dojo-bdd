package org.training.pos.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

  private String id;
  private String name;
  private String unit;
  private BigDecimal unitPrice;
  private boolean promotion;

}
