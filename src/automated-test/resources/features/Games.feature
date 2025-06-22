Feature: Games

  Scenario: Find All
    Given url baseUrl + '/games'
    When method GET
    Then status 200