package org.training.pos.junit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.training.pos.PosService;
import org.training.pos.dto.CartDTO;
import org.training.pos.dto.ReceiptDTO;
import org.training.pos.dto.ReceiptItemDTO;

public class PosServiceTest extends PosBaseTest {

  PosService posService = new PosService();

  @Test
  public void should_get_normal_price_when_bought_single_item_without_promotion() {
    final CartDTO cartDTO = selectedSingleItem();

    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);
    final BigDecimal payment = new BigDecimal(receiptDTO.getTotalPrice());

    assertThat(BigDecimal.valueOf(9.00).compareTo(payment), Is.is(0));
  }

  @Test
  public void should_get_normal_price_when_bought_multiple_item_without_promotion() {
    final CartDTO cartDTO = selectedMultipleItems();

    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);
    final BigDecimal payment = new BigDecimal(receiptDTO.getTotalPrice());

    assertThat(BigDecimal.valueOf(49).compareTo(payment), is(0));
  }

  @Test
  public void should_not_get_discount_when_bought_single_item_without_promotion() {
    final CartDTO cartDTO = selectedSingleItem();

    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);
    final BigDecimal discount = new BigDecimal(receiptDTO.getDiscount());

    assertThat(BigDecimal.valueOf(0.00).compareTo(discount), is(0));
  }

  @Test
  public void should_not_get_discount_when_bought_multiple_item_without_promotion() {
    final CartDTO cartDTO = selectedMultipleItems();

    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);
    final BigDecimal discount = new BigDecimal(receiptDTO.getDiscount());

    assertThat(BigDecimal.valueOf(0.00).compareTo(discount), is(0));
  }

  @Test
  public void should_not_get_discount_when_bought_multiple_item_with_promotion_but_count_less_than_3() {
    promoteItem("苹果");

    final CartDTO cartDTO = selectedMultipleItems();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal discount = new BigDecimal(receiptDTO.getDiscount());
    assertThat(BigDecimal.valueOf(0.00).compareTo(discount), is(0));
  }

  @Test
  public void should_get_discount_when_bought_single_item_with_promotion_and_count_greater_or_equal_than_3() {
    promoteItem("可乐");

    final CartDTO cartDTO = selectedSingleItem();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal discount = new BigDecimal(receiptDTO.getDiscount());
    assertThat(BigDecimal.valueOf(3.00).compareTo(discount), is(0));
  }

  @Test
  public void should_get_discount_when_bought_multiple_item_with_promotion_and_count_greater_or_equal_than_5() {
    promoteItem("圆珠笔");

    final CartDTO cartDTO = selectedMultipleItems();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal discount = new BigDecimal(receiptDTO.getDiscount());
    assertThat(BigDecimal.valueOf(2.00).compareTo(discount), is(0));
  }

  @Test
  public void should_get_discount_when_bought_multiple_item_with_promotion_and_count_greater_or_equal_than_7() {
    promoteItem("杯子");

    final CartDTO cartDTO = selectedMultipleItems();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal discount = new BigDecimal(receiptDTO.getDiscount());
    assertThat(BigDecimal.valueOf(8.00).compareTo(discount), is(0));
  }

  @Test
  public void should_get_discount_price_when_bought_single_item_with_promotion_and_count_greater_or_equal_than_3() {
    promoteItem("可乐");

    final CartDTO cartDTO = selectedSingleItem();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal payment = new BigDecimal(receiptDTO.getTotalPrice());
    assertThat(BigDecimal.valueOf(6.00).compareTo(payment), is(0));
  }

  @Test
  public void should_get_discount_price_when_bought_multiple_item_with_promotion_and_count_greater_or_equal_than_5() {
    promoteItem("圆珠笔");

    final CartDTO cartDTO = selectedMultipleItems();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal payment = new BigDecimal(receiptDTO.getTotalPrice());
    assertThat(BigDecimal.valueOf(47.00).compareTo(payment), is(0));
  }

  @Test
  public void should_get_discount_price_when_bought_multiple_item_with_promotion_and_count_greater_or_equal_than_7() {
    promoteItem("杯子");

    final CartDTO cartDTO = selectedMultipleItems();
    final ReceiptDTO receiptDTO = posService.buyItems(cartDTO);

    final BigDecimal payment = new BigDecimal(receiptDTO.getTotalPrice());
    assertThat(BigDecimal.valueOf(41.00).compareTo(payment), is(0));
  }

  @Test
  public void should_print_receipt_when_bought_multiple_items_given_none_item_under_promotion() {
    final CartDTO cartDTO = selectedMultipleItems();
    ReceiptDTO expectedReceiptDTO = buildExpectedReceiptDTO();

    final ReceiptDTO actualReceiptDTO = posService.buyItems(cartDTO);

    assertThat(expectedReceiptDTO.equals(actualReceiptDTO), is(true));
  }

  private ReceiptDTO buildExpectedReceiptDTO() {
    ReceiptItemDTO colaReceiptDTO = new ReceiptItemDTO("可乐", "3瓶", "3.00",  "9.00");
    ReceiptItemDTO appleReceiptDTO = new ReceiptItemDTO("苹果", "2斤", "1.00",  "2.00");
    ReceiptItemDTO penReceiptDTO = new ReceiptItemDTO("圆珠笔", "5支", "2.00",  "10.00");
    ReceiptItemDTO cupReceiptDTO = new ReceiptItemDTO("杯子", "7个", "4.00",  "28.00");
    final List<ReceiptItemDTO> receiptItems = Arrays.asList(colaReceiptDTO,
        appleReceiptDTO,
        penReceiptDTO,
        cupReceiptDTO);

    return new ReceiptDTO("49.00", "0.00", receiptItems);
  }
}
