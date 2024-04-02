Feature: Validating Book APIs

  Scenario Outline: Verify that user is able to submit order using post api
    Given user add payload to request with "<bookid>" and "<name>"
    When user calls submitOrder api with post http request
    Then api call is succesful with status code 201
    And Status in response is OK

    Examples: 
      | bookid | name |
      |      1 | John |
      |      1 | Tom |
