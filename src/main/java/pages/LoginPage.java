package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import locator.RelativeUrl;
import locator.RequiredElement;

@RelativeUrl("/sign-in")
public class LoginPage extends BasePage {
	@RequiredElement
	@FindBy(id = "email")
	public WebElement tfEmail;
	
	@RequiredElement
	@FindBy(id = "password")
	public WebElement tfPassword;
	
	@FindBy(xpath = "//button[contains(@class, 'LoginForm__submit_1hDJs')]")
	public WebElement btnSignin;
	
	@FindBy(xpath = "//input[contains(@class, 'MFACodeInput__input_3nU9t')]")
	public WebElement tfOtp;
	
	@FindBy(css = ".ConnectedErrorModal__heading_3tm02")
	public WebElement lbErrorHeading;
	
	@FindBy(css = ".ConnectedErrorModal__text_VKMS4")
	public WebElement lbErrorMessage;
	
	@FindBy(css = ".FormFieldError__isVisible_31Uea")
	public WebElement lbFieldValidation;
}
