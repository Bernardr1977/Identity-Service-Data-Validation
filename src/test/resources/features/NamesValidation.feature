Feature: Validate First Name and Last Name Fields

  As a user,
  I want to ensure that the system properly validates the first and last name fields
  So that I can submit valid names while being notified of any errors

  # Happy Path Scenarios
  Scenario Outline: Successfully validate valid first and last name
    Given the user provides a first name "<first_name>"
    And the user provides a last name "<last_name>"
    When the system validates the name fields
    Then the system should accept the input

    Examples:
      | first_name | last_name   |
      | Johnny     | Depp        |
      | O'Connor   | Doe         |
      | John       | Smith-Jones |

  # Non-Happy Path Scenarios
  Scenario Outline: Validate missing first or last name
    Given the user provides a first name "<first_name>"
    And the user provides a last name "<last_name>"
    When the system validates the name fields
    Then the system should display an error "<error_message>"

    Examples:
      | first_name | last_name | error_message            |
      | ""         | Depp      | "First name is required" |
      | Johnny     | ""        | "Last name is required"  |

  Scenario Outline: Validate first or last name length exceeds 45 characters
    Given the user provides a first name "<first_name>"
    And the user provides a last name "<last_name>"
    When the system validates the name fields
    Then the system should display an error "<error_message>"

    Examples:
      | first_name                                                    | last_name                                                           | error_message                            |
      | pirates of the caribbean with johnny depp and keira knightley | Smith                                                               | "First name cannot exceed 45 characters" |
      | Barbossa                                                      | pirates of the caribbean along with johnny depp and keira knightley | "Last name cannot exceed 45 characters"  |

  Scenario Outline: Validate first or last name contains invalid characters
    Given the user provides a first name "<first_name>"
    And the user provides a last name "<last_name>"
    When the system validates the name fields
    Then the system should display an error "<error_message>"

    Examples:
      | first_name | last_name | error_message                            |
      | Pinte1     | Smith     | "First name contains invalid characters" |
      | Pintel     | Ragett1   | "Last name contains invalid characters"  |
