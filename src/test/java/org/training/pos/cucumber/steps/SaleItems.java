package org.training.pos.cucumber.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.math.BigDecimal;
import java.util.List;
import org.training.pos.PosService;
import org.training.pos.domain.Item;
import org.training.pos.dto.CartDTO;
import org.training.pos.dto.CartItemDTO;
import org.training.pos.dto.ReceiptDTO;
import org.training.pos.repository.ItemRepository;

public class SaleItems {

  private CartDTO cartDTO = new CartDTO();
  private ReceiptDTO receiptDTO;

  private PosService posService = new PosService();

  @Given("^items is sold as following")
  public void items_is_sold_as_following(List<Item> items) {
    ItemRepository.addItems(items);
  }

  @Given("^([^\\s]+) is sold (with|without) promotion$")
  public void item_is_sold_with_promotion_or_not(String name, String promotion) {
    ItemRepository.findByName(name).ifPresent(item -> item.setPromotion("with".equals(promotion)));
  }

  @When("^I bought ([^\\s]+) ([^\\s]+) ([^\\s]+)$")
  public void i_bought(String name, String count, String unit) {
    ItemRepository.findByName(name).ifPresent(item -> {
          final CartItemDTO cartItemDTO = new CartItemDTO(name, count, unit, item.getId());
          cartDTO.getCartItemDTOs().add(cartItemDTO);

          receiptDTO = posService.buyItems(cartDTO);
        });
  }

  @Then("^I should pay ([^\\s]+) 元$")
  public void i_should_pay(String payment) {
    BigDecimal actualPayment = new BigDecimal(receiptDTO.getTotalPrice());
    final BigDecimal expectedPayment = new BigDecimal(payment);
    assertThat(expectedPayment.compareTo(actualPayment), is(0));
  }

  @Then("^I should save ([^\\s]+) 元$")
  public void i_should_save(String discount) {
    BigDecimal actualDiscount = new BigDecimal(receiptDTO.getDiscount());
    final BigDecimal expectedDiscount = new BigDecimal(discount);
    assertThat(expectedDiscount.compareTo(actualDiscount), is(0));
  }

  @Then("^I should get receipt$")
  public void i_should_get_receipt(String receiptData) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    final ReceiptDTO expectedReceiptDTO = objectMapper.readValue(receiptData, ReceiptDTO.class);

    final ReceiptDTO actualReceiptDTO = receiptDTO;

    assertThat(expectedReceiptDTO.equals(actualReceiptDTO), is(true));
  }
}
