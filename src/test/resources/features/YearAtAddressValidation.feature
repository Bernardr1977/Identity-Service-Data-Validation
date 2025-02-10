Feature: Validate years at address

  As a user,
  I want to ensure that a person must have lived at their address for at least 5 years
  So that the system correctly validates the address duration requirement

  # Happy Path Scenario
  Scenario Outline: Successfully validate years at address
    Given the person has lived at their address for "<years>"
    When they submit their address information
    Then the system should <result>

    Examples:
      | years | result             |
      | 6     | accept the address |

  # Non-Happy Path Scenarios
  Scenario Outline: Validate invalid years at address
    Given the person has lived at their address for "<years>"
    When they submit their address information
    Then the system should reject the address and display a message saying "<error_message>"

    Examples:
      | years | error_message                                              |
      | 4     | "You must have lived at this address for at least 5 years" |
      | ""    | "Years at address is required"                             |
