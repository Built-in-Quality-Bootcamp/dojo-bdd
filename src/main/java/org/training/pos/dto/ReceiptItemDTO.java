package org.training.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptItemDTO {

  private String name;
  private String count;
  private String unitPrice;
  private String subTotal;
}
