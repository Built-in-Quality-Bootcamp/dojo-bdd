Feature: pos could be used to settle account for items in cart with promotions

  Background: items is sold by pos
    Given items is sold as following
      | id           | name    | unit   | unitPrice  | promotion
      | ITEM000001   | 可乐     | 瓶     | 3          | false
      | ITEM000002   | 苹果     | 斤     | 1          | false

  @batch
  Scenario Outline: items with promotion should get discount
    Given <item> is sold <promotion> promotion

    When I bought <item> <count> <unit>

    Then I should pay <payment> 元
    And I should save <discount> 元
    Examples:
      | item  |promotion | count | unit  | payment  | discount|
      | 可乐   | with     | 2     | 瓶    | 6       | 0        |
      | 可乐   | with     | 3     | 瓶    | 6       | 3        |
      | 可乐   | with     | 5     | 瓶    | 12      | 3        |
      | 可乐   | with     | 7     | 瓶    | 15      | 6        |
      | 苹果   | without  | 3     | 斤    | 3       | 0        |



