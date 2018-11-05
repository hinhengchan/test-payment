@desktop @payment
Feature: Payment feature
  User is able to pay a new payee

  @bvt
  Scenario Outline: Pay successful with creating a new payee
    Given I am logged in
    When I proceed creating a new payee with valid 'true' country 'Hong Kong'
    And I fill in the new payee info with '<nickname>' '<payeeName>' '<payeeAddress>' '<payeeCity>' '<payeeState>' '<payeePostcode>' '<payeePhoneNumber>'
    And I fill in the bank info with '<bankCode>' '<branchCode>' '<accountNumber>'
    And I fill in amount with '<amount>' delivery method with '<deliveryMethod>' and confirm payment
    Then I should see 'PaymentSuccess label'
    
    Examples: 
      | nickname		| payeeName		| payeeAddress				| payeeCity	| payeeState	| payeePostcode	| payeePhoneNumber	| bankCode	| branchCode	| accountNumber	| amount	| deliveryMethod	|
      | auto{Random}	| auto {Random}	| {RandomInt} fake street	| Hong Kong	| Hong Kong		| 999077		| 22{RandomInt}		| HSBC BANK	| 001			| {RandomNumber}| 100		| Same Business Day	|
      | auto{Random}	| auto {Random}	| {RandomInt} fake street	| Hong Kong	| 				| 				| 					| HSBC BANK	| 001			| {RandomNumber}| 100		| Same Business Day	|
      
  @functional @low
  Scenario: Create new payee fail with non-existing country
    Given I am logged in
    When I proceed creating a new payee with valid 'false' country 'Hong Hong'
    Then I should see 'No results found' in 'CountryNoResult' label
    
  @functional @low
  Scenario Outline: Create new payee fail without partial required payee info
    Given I am logged in
    When I proceed creating a new payee with valid 'true' country 'Hong Kong'
    And I fill in the new payee info with '<nickname>' '<payeeName>' '<payeeAddress>' '<payeeCity>' '<payeeState>' '<payeePostcode>' '<payeePhoneNumber>'
    Then I should see 'Required' in 'FieldValidation' label
    
    Examples: 
      | nickname		| payeeName		| payeeAddress				| payeeCity	| payeeState	| payeePostcode	| payeePhoneNumber	|
      | 				| auto {Random}	| {RandomInt} fake street	| Hong Kong	| Hong Kong		| 999077		| 22{RandomInt}		|
      | auto{Random}	| 				| {RandomInt} fake street	| Hong Kong	| Hong Kong		| 999077		| 22{RandomInt}		|
      | auto{Random}	| auto {Random}	| 							| Hong Kong	| Hong Kong		| 999077		| 22{RandomInt}		|
      | auto{Random}	| auto {Random}	| {RandomInt} fake street	| 			| Hong Kong		| 999077		| 22{RandomInt}		|
      
  @functional @low
  Scenario Outline: Create new payee fail without partial required bank info
    Given I am logged in
    When I proceed creating a new payee with valid 'true' country 'Hong Kong'
    And I fill in the new payee info with 'auto{Random}' 'auto {Random}' '{RandomInt} fake street' 'Hong Kong' 'Hong Kong' '999077' '22{RandomInt}'
    And I fill in the bank info with '<bankCode>' '<branchCode>' '<accountNumber>'
    Then I should see 'Required' in 'FieldValidation' label
    
    Examples: 
      | bankCode	| branchCode	| accountNumber	|
      | 			| 				| {RandomNumber}|
      | HSBC BANK	| 				| {RandomNumber}|
      | HSBC BANK	| 001			| 				|
      
  @functional @low
  Scenario: Create new payee fail without any bank info
    Given I am logged in
    When I proceed creating a new payee with valid 'true' country 'Hong Kong'
    And I fill in the new payee info with 'auto{Random}' 'auto {Random}' '{RandomInt} fake street' 'Hong Kong' 'Hong Kong' '999077' '22{RandomInt}'
    And I fill in the bank info with '' '' ''
    Then I should see 'INVALID REQUEST ERROR' in 'ErrorHeading' label
    And I should see '[payee_card_number, bank_account_number] are missing, exactly one parameter is required' in 'ErrorMessage' label
    
  @functional @high
  Scenario: Create new payee fail with already existed payee
    Given I am logged in
    When I proceed creating a new payee with valid 'true' country 'Hong Kong'
    And I fill in the new payee info with 'autotest' 'auto test' '1234 fake street' 'Hong Kong' 'Hong Kong' '999077' '23456789'
    And I fill in the bank info with 'HSBC BANK' '001' '123456789012'
    Then I should see 'INVALID REQUEST ERROR' in 'ErrorHeading' label
    And I should see 'The payee already exists.' in 'ErrorMessage' label
    
  @functional @low
  Scenario Outline: Pay fail without required amount / delivery method info
    Given I am logged in
    When I proceed creating a new payee with valid 'true' country 'Hong Kong'
    And I fill in the new payee info with 'auto{Random}' 'auto {Random}' '{RandomInt} fake street' 'Hong Kong' 'Hong Kong' '999077' '22{RandomInt}'
    And I fill in the bank info with 'HSBC BANK' '001' '{RandomNumber}'
    And I fill in amount with '<amount>' delivery method with '<deliveryMethod>' and confirm payment
    Then I should see '<expectedErrorMessage>' in 'FieldValidation' label
    
    Examples: 
      | amount	| deliveryMethod	| expectedErrorMessage		|
      | 		| Same Business Day	| Required					|
      | 100		| 					| Select a delivery method	|
      