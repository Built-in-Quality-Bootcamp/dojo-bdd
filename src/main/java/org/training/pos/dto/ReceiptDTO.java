package org.training.pos.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDTO {

  private String totalPrice;
  private String discount;
  private List<ReceiptItemDTO> receiptItems;

}
