Feature: Validate Date of Birth Field

  As a user,
  I want to ensure that the system properly validates the date of birth field
  So that I can submit a valid date of birth while being notified of any errors

  # Happy Path Scenarios
  Scenario Outline: Successfully validate a valid date of birth
    Given the user provides a date of birth "<dob>"
    When the system validates the date of birth
    Then the system should accept the input

    Examples:
      | dob          |
      | 1977-06-20   |
      | 2006-02-10   |
      | 1985-06-20   |

  # Non-Happy Path Scenarios
  Scenario Outline: Validate invalid date of birth
    Given the user provides a date of birth "<dob>"
    When the system validates the date of birth
    Then the system should display an error "<error_message>"

    Examples:
      | dob          | error_message                               |
      | ""           | "Date of birth is required"                 |
      | 2010-06-20   | "Person must be at least 18 years old"      |
