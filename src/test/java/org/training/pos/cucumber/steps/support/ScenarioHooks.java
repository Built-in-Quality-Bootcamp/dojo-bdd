package org.training.pos.cucumber.steps.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.math.BigDecimal;
import java.util.Arrays;
import org.training.pos.domain.Item;
import org.training.pos.repository.ItemRepository;

public class ScenarioHooks {

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
    ItemRepository.deleteAll();
  }
}
