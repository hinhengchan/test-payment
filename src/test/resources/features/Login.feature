@desktop @mobile @login
Feature: Login feature
  Existing user should be able to login
  While non-existing user should fail login

  @bvt
  Scenario: Login successful with valid email and password
    Given I go to the 'Login' page
    When I fill in login info with '{autotestUser}' '{autotestPassword}' '{autotestOtp}'
    Then I should be at the 'Account Dashboard' page
      
  @functional @high
  Scenario Outline: Login fail with invalid email or password
    Given I go to the 'Login' page
    When I fill in login info with '<email>' '<password>' ''
    Then I should be at the 'Login' page
    And I should see 'AUTHENTICATION ERROR' in 'ErrorHeading' label
    And I should see 'Invalid email or password.' in 'ErrorMessage' label

    Examples: 
      | email          	| password				|
      | {RandomEmail} 	| {autotestPassword}	|
      | {autotestUser} 	| {Random}				|
      
  @functional @high
  Scenario: Login fail with invalid otp
    Given I go to the 'Login' page
    When I fill in login info with '{autotestUser}' '{autotestPassword}' '{RandomNumber}'
    Then I should be at the 'Login' page
    And I should see 'AUTHENTICATION ERROR' in 'ErrorHeading' label
    And I should see 'Invalid verification code.' in 'ErrorMessage' label
      
  @functional @low
  Scenario: Login fail with invalid email
    Given I go to the 'Login' page
    When I fill in login info with '{Random}' '{autotestPassword}' ''
    Then I should be at the 'Login' page
    And I should see 'Invalid email' in 'FieldValidation' label
      
  @functional @low
  Scenario Outline: Login fail with empty email or password
    Given I go to the 'Login' page
    When I fill in login info with '<email>' '<password>' ''
    Then I should be at the 'Login' page
    And I should see 'Required' in 'FieldValidation' label

    Examples: 
      | email          	| password				|
      | 			 	| {autotestPassword}	|
      | {autotestUser} 	| 						|