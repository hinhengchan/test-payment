package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import locator.RelativeUrl;
import locator.RequiredElement;

@RelativeUrl("/payments")
public class AccountDashboardPage extends BasePage {
	@RequiredElement(longWait = true)
	@FindBy(css = ".MetricsPanel__inner_1SuXc")
	public WebElement lbDashboardPanel;
	
	@RequiredElement()
	@FindBy (xpath = "//button[contains(@class, 'sass__white_3rfbr')]")
	public WebElement btnMakeAPayment;
	
	@FindBy (xpath = "//button[contains(@class, 'PaymentWizardEntry__new_1vqk0')]")
	public WebElement btnCreateANewPayee;
	
	@FindBy (css = ".ChoosePayeeType__body_1xTAZ button:nth-child(2)")
	public WebElement btnBankAccount;
	
	@FindBy (css = ".ChooseBankAccountType__body_6xRhW button:nth-child(2)")
	public WebElement btnIndividual;
	
	@FindBy (id = "intlPaymentCountrySelector")
	public WebElement tfCountry;
	
	@FindBy (id = "react-select-intlPaymentCountrySelector--option-0")
	public WebElement btnCountryFirstMatch;
	
	@FindBy (css = ".Select-noresults")
	public WebElement lbCountryNoResult;
	
	@FindBy (css = ".ChoosePayeeCountry__footer_1-gaa button")
	public WebElement btnCountryNext;
	
	@FindBy (id = "nickname")
	public WebElement tfNickname;
	
	@FindBy (id = "bankAccountPayeeName")
	public WebElement tfPayeeName;
	
	@FindBy (id = "bankAccountPayeeAddress")
	public WebElement tfPayeeAddress;
	
	@FindBy (id = "bankAccountPayeeCity")
	public WebElement tfPayeeCity;
	
	@FindBy (id = "bankAccountPayeeState")
	public WebElement tfPayeeState;
	
	@FindBy (id = "bankAccountPayeePostcode")
	public WebElement tfPayeePostcode;
	
	@FindBy (id = "bankAccountPayeePhoneNumber")
	public WebElement tfPayeePhoneNumber;
	
	@FindBy(css = ".ConnectedErrorModal__heading_3tm02")
	public WebElement lbErrorHeading;
	
	@FindBy(css = ".ConnectedErrorModal__text_VKMS4")
	public WebElement lbErrorMessage;
	
	@FindBy(css = ".FormFieldError__isVisible_31Uea")
	public WebElement lbFieldValidation;
	
	@FindBy (css = ".CreateBankPayeeForm__footer_1hlNP button")
	public WebElement btnPayeeNext;
	
	@FindBy (id = "bankAccountBankCode")
	public WebElement tfBankCode;
	
	@FindBy (id = "react-select-connectedBankSelector--option-0")
	public WebElement btnBankCodeFirstMatch;
	
	@FindBy (id = "bankAccountBranchCode")
	public WebElement tfBranchCode;
	
	@FindBy (id = "react-select-connectedBranchCodeSelector--option-0")
	public WebElement btnBranchCodeFirstMatch;
	
	@FindBy (id = "bankAccountNumber")
	public WebElement tfAccountNumber;
	
	@FindBy (css = ".BankInfoForm__footer_6-wkx button")
	public WebElement btnBankNext;
	
	@FindBy (css = ".ReviewPayeeCreation__footer_1UYd7 button")
	public WebElement btnPayeeCreate;
	
	@FindBy (css = ".MonetaryInput__input_2Rivl")
	public WebElement tfAmount;
	
	@FindBy(css = "label[for=outgoingRtgs]")
	public WebElement btnSameBusinessDay;
	
	@FindBy (css = ".LocalPaymentForm__footer_2pCJj button")
	public WebElement btnPaymentCreate;
	
	@FindBy (css = ".ReviewPaymentDetails__footer_2Sa6z button")
	public WebElement btnPaymentConfirm;
	
	@FindBy(css = ".PaymentSuccessConfirmation__body_2dK6U")
	public WebElement lbPaymentSuccess;
}
