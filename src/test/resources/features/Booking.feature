Feature: booking test scenarios

  Scenario Outline: Create a booking for a specific date
    Given i call authentication end-pint and create token
    When i post customer information "<Check_In_Date>" "<Check_Out_Dat>"
    Then the customer is listed on booking list
    And verify booking details
    Examples:
      | Check_In_Date | Check_Out_Dat |
      |   2022-01-20  |   2022-01-27  |


  Scenario: Create a booking for a specific date using DataTable
    Given i call authentication end-pint and create token
    When i post customer information
      | FirstName | LastName | Price | Deposit | CheckInDate  | CheckOutDate |
      | John      |     Doe  |  30   |  true   |  2022-02-01  |  2022-02-17  |
    Then the customer is listed on booking list
    And verify booking details

  Scenario Outline: Delete an individual booking successfully
    Given i call authentication end-pint and create token
    And i post customer information "<Check_In_Date>" "<Check_Out_Dat>"
    When i delete a booking
    Then the booking is not available in the booking list
    Examples:
      | Check_In_Date | Check_Out_Dat |
      |   2022-02-01  |   2022-02-17  |
