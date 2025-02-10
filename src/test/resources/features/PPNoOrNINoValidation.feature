Feature: Validate Passport Number and National Insurance Number

  As a user,
  I want to ensure that the system properly validates the passport number and national insurance number
  So that I can submit the correct and required information based on the conditions

  # Happy Path Scenarios
  Scenario: Successfully validate when both passport number and national insurance number are provided
    Given the user provides a passport number "DD583497"
    And the user provides a national insurance number "QQ123456C"
    When the system validates the passport and national insurance numbers
    Then the system should accept the input

  Scenario: Successfully validate when only passport number is provided (national insurance number is absent)
    Given the user provides a passport number "DD583497"
    And the user provides no national insurance number
    When the system validates the passport number
    Then the system should accept the input

  Scenario: Successfully validate when only national insurance number is provided (passport number is absent)
    Given the user provides no passport number
    And the user provides a national insurance number "QQ123456C"
    When the system validates the national insurance number
    Then the system should accept the input

  # Non-Happy Path Scenarios
  Scenario: Validate missing passport number and national insurance number (both are absent)
    Given the user provides no passport number
    And the user provides no national insurance number
    When the system validates the passport and national insurance numbers
    Then the system should display an error "Either passport number or national insurance number is required"

  Scenario: Validate missing national insurance number when passport number is absent
    Given the user provides no passport number
    And the user provides no national insurance number
    When the system validates the passport and national insurance numbers
    Then the system should display an error "National insurance number is required when passport number is absent"

  Scenario: Validate missing passport number when national insurance number is absent
    Given the user provides no passport number
    And the user provides no national insurance number
    When the system validates the passport and national insurance numbers
    Then the system should display an error "Passport number is required when national insurance number is absent"

  Scenario Outline: Validate the passport number and national insurance number have valid characters
    Given the user provides a passport number "<passport_number>"
    And the user provides a national insurance number "<ni_number>"
    When the system validates the passport number and national insurance number
    Then the system should display an error "<error_message>"

    Examples:
      | passport_number | ni_number | error_message                                                             |
      | A1234!567       |           | "Passport number contains invalid characters"                             |
      |                 | QQ1234AB@ | "National insurance number contains invalid characters"                   |
      | A1234!567       | QQ1234AB@ | "Passport number & national insurance number contains invalid characters" |
