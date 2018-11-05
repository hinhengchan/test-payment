package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonStep extends BaseStep {
	// ========================================================================
	// Generic actions below
	// ========================================================================

	// I click [element] [type](=[extraParam]( after a while)
	@When("^I click '([^']*)'( [^' ]*)?(?:='([^']*)')?( after a while)?$")
	public void base_i_click(String name, String type, String extraParam, String longWait) throws Throwable {
		super.i_click(name, type, extraParam, longWait);
	}

	@Then("^I should be at the '(.*)' page$")
	public void base_i_am_at_the_page(String pageName) throws Throwable {
		super.i_am_at_the_page(pageName);
	}

	@Given("^I go to the '([^']*)' page$")
	public void base_i_go_to(String pageName) throws Throwable {
		super.i_go_to(pageName);
	}

	@When("^I fill in '([^']*)'='(.*?)'$")
	public void base_i_fill_in(String elementName, String elementValue) throws Throwable {
		super.i_fill_in(elementName, elementValue);
	}

	@Given("^I am logged in$")
	public void base_i_login() throws Throwable {
		super.i_login(autotestUser.getUsername(), autotestUser.getPassword(), autotestUser.getOtp());
	}
	
	@When("^I fill in login info with '(.*?)' '(.*?)' '(.*?)'$")
	public void base_i_fill_in_login_info_with(String email, String password, String otp) throws Throwable {
		super.i_login(email, password, otp);
	}

	@Then("^I should see '([^']*) (.*?)'$")
	public void base_i_should_see(String elementName, String elementType) throws Throwable {
		super.i_should_see("", elementName, elementType, null, null);
	}
	
	@Then("^I should see '(.*)' in '(.*)' (.*)$")
	public void base_i_should_see(String message, String elementName, String elementType) throws Throwable {
		super.i_should_see(message, elementName, elementType, null, null);
	}
	
	@When("^I proceed creating a new payee with valid '(.*)' country '(.*)'$")
	public void base_i_proceed_creating_a_new_payee(Boolean isValid, String country) throws Throwable {
		super.i_proceed_creating_a_new_payee(isValid, country);
	}
	
	@When("^I fill in the new payee info with '(.*?)' '(.*?)' '(.*?)' '(.*?)' '(.*?)' '(.*?)' '(.*?)'$")
	public void base_i_fill_in_the_new_payee_info_with(String nickname, String payeeName, String payeeAddress, String payeeCity, String payeeState, String payeePostcode, String payeePhoneNumber) throws Throwable {
		super.i_fill_in_the_new_payee_info_with(nickname, payeeName, payeeAddress, payeeCity, payeeState, payeePostcode, payeePhoneNumber);
	}
	
	@When("^I fill in the bank info with '(.*?)' '(.*?)' '(.*?)'$")
	public void base_i_fill_in_the_bank_info_with(String bankCode, String branchCode, String accountNumber) throws Throwable {
		super.i_fill_in_the_bank_info_with(bankCode, branchCode, accountNumber);
	}
	
	@When("^I fill in amount with '(.*?)' delivery method with '(.*?)' and confirm payment$")
	public void base_i_fill_in_amount_and_confirm_payment(String amount, String deliveryMethod) throws Throwable {
		super.i_fill_in_amount_delivery_method_and_confirm_payment(amount, deliveryMethod);
	}
}
