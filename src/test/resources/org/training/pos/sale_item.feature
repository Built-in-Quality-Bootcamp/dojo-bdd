@ignore
Feature: pos could be used to settle account for items in cart with promotions

  Background: items is sold by pos
    Given items is sold as following
      | id           | name    | unit   | unitPrice  | promotion
      | ITEM000001   | 可乐     | 瓶     | 3          | false
      | ITEM000002   | 苹果     | 斤     | 1          | false


  Scenario: items with promotion should get discount
    Given 可乐 is sold with promotion

    When I bought 可乐 3 瓶

    Then I should pay 6 元
     And I should save 3 元

  Scenario: items without promotion should not get discount
    Given 苹果 is sold without promotion

    When I bought 苹果 3 斤

    Then I should pay 3 元
    And I should save 0 元


