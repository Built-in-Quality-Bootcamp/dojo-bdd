package org.training.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CartItemDTO {

  private String name;
  private String count;
  private String unit;
  private String id;

}
