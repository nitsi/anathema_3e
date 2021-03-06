@Integration
Feature: Anathema determines the priority of attribute groups automatically.
  The tests use the rookie lawgiver template to avail of concrete numbers.

  Scenario: Anathema treats the higher groups as secondary, when two groups exceed the primary allowance
    Given a new Solar using rules for RookieLawgiver
    When she spends 8 points on Mental Attributes
    And she spends 9 points on Physical Attributes
    Then she has spent 12 bonus points

  Scenario: Anathema treats the higher groups as tertiary, when two groups exceed the secondary allowance
    Given a new Solar using rules for RookieLawgiver
    When she spends 8 points on Mental Attributes
    And she spends 6 points on Physical Attributes
    And she spends 7 points on Social Attributes
    Then she has spent 9 bonus points

  Scenario: Anathema treats the highest groups as tertiary, when two groups exceed the primary allowance and the secondary allowance is spent
    Given a new Solar using rules for RookieLawgiver
    When she spends 8 points on Mental Attributes
    And she spends 6 points on Physical Attributes
    And she spends 9 points on Social Attributes
    Then she has spent 15 bonus points
