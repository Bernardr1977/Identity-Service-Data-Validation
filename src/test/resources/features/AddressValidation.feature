Feature: Validate Address Field

  As a user,
  I want to ensure that the system properly validates the address field
  So that I can submit a valid address while being notified of any errors

  # Happy Path Scenarios
  Scenario Outline: Successfully validate address with various fields
    Given the user provides a "<line1>"
    And the user provides a "<building_number>"
    And the user provides a "<street_name>"
    And the user provides a "<town>"
    And the user provides a "<postcode>"
    When the system validates the address
    Then the system should accept the input

    Examples:
      | line1           | building_number | street_name | town   | postcode |
      | 123 Main Street |                 |             |        | AB12 3CD |
      |                 | 45              | High Street | London | XY99 1ZZ |

  # Non-Happy Path Scenarios
  Scenario Outline: Validate missing address fields
    Given the user provides "<line1>"
    And the user provides "<building_number>"
    And the user provides "<street_name>"
    And the user provides "<town>"
    And the user provides "<postcode>"
    When the system validates the address
    Then the system should display an error "<error_message>"

    Examples:
      | line1          | building_number | street_name | town       | postcode | error_message                 |
      |                |                 |             |            |          | "Address is required"         |
      | 456 Elm Street |                 |             |            |          | "Postcode is required"        |
      |                |                 | High Street | Manchester | M1 2AB   | "Building number is required" |
      | 78             |                 |             | Manchester | M1 2AB   | "Street name is required"     |
      | 78             | High Street     |             |            | M1 2AB   | "Town is required"            |
      |                |                 |             |            |          | "Address is required"         |
