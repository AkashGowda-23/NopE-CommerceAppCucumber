Feature: Login 

Scenario: Successful login with valid credentails 
    Given user Launch chrome browser 
    When user opens URL "https://admin-demo.nopcommerce.com/login"
    And user enters Email as "admin@yourstore.com" and Password as "admin"
    And Click on the login 
    Then the page title should be "Dashboard"
    When the user click on the logout link 
    Then page title should be "Admin area demo"
    And close the browser
      
    

