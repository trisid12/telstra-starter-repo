Feature: SIM card activation

  Scenario: Successfully activate a SIM card
    Given the SIM card ICCID is "1255789453849037777" and email is "success@example.com"
    When I activate the SIM card
    Then the SIM card with ID 1 should be active

  Scenario: Fail to activate a SIM card
    Given the SIM card ICCID is "8944500102198304826" and email is "fail@example.com"
    When I activate the SIM card
    Then the SIM card with ID 2 should not be active
