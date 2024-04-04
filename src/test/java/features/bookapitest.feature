Feature: Validating Book APIs

  Scenario Outline: Verify that user is able to submit order using post api
    Given user add payload to request with "<bookid>" and "<name>"
    When user calls "submitOrderAPI" with "POST" http request
    Then api call is succesful with status code 201
    And "created" in response is "true"
    And verify order_Id created maps to "<name>" using "getOrderAPI"

    Examples: 
      | bookid | name |
      |      1 | John |
      |      1 | Tom |
