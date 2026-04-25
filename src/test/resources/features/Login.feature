@smoke @regression
Feature: Admin Login
  As an admin user
  I want to be able to log in and log out of the NopCommerce admin portal
  So that I can manage the store securely

  Background:
    Given user Launch chrome browser

  @smoke
  Scenario: Successful login with valid credentials
    When user opens URL "https://admin-demo.nopcommerce.com/login"
    And user enters Email as "$admin.email" and Password as "$admin.password"
    And Click on the login
    Then the page title should be "Dashboard"
    When the user click on the logout link
    Then page title should be "Admin area demo"
    And close the browser

  @negative @regression
  Scenario Outline: Login fails with invalid credentials
    When user opens URL "https://admin-demo.nopcommerce.com/login"
    And user enters Email as "<email>" and Password as "<password>"
    And Click on the login
    Then the page title should be "Login"
    And close the browser

    Examples:
      | email                    | password     |
      | wrong@example.com        | wrongpass    |
      | admin@yourstore.com      | badpassword  |
