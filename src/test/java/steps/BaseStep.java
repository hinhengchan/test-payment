package steps;

import java.lang.reflect.Constructor;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import data.ConfigManager;
import data.User;
import pages.BasePage;
import robot.Robot;
import robot.RobotUtil;

public class BaseStep {
	private static BasePage currentPage = null;
	protected Robot robot = ConfigManager.getRobot();
	protected User autotestUser = ConfigManager.getAutotestUser();

	public static void setCurrentPage(BasePage currentPage) {
		BaseStep.currentPage = currentPage;
	}

	public static BasePage getCurrentPage() {
		return currentPage;
	}
	
	protected void i_click(String name) throws Throwable {
		i_click(name, null, null, null);
	}

	protected void i_click(String name, String type, String extraParam, String longWait) throws Throwable {
		if (name.contains("(mobile)")) {
			if (robot.isMobile()) {
				name = name.replaceAll("\\(mobile\\)", "");
			} else {
				return;
			}
		}

		name = RobotUtil.getString(name);
		extraParam = RobotUtil.getString(extraParam);
		System.out.print("> (" + robot.getSessionId() + ") Click: ");
		WebElement e = getElementByString(name, type, extraParam);
		if (longWait != null && !longWait.isEmpty()) {
			robot.waitFor(e, robot.getLongWait());
		}
		robot.click(e);
	}

	private WebElement getElementByString(String name, String type, String extraParam) throws Throwable {
		name = name.replaceAll(" ", "");
		type = (type == null) ? "" : type.replaceAll(" ", "");

		if (type.isEmpty() || type.equalsIgnoreCase("button")) {
			type = "btn";
		} else if (type.equalsIgnoreCase("message")) {
			type = "msg";
		} else if (type.equalsIgnoreCase("label")) {
			type = "lb";
		} else if (type.equalsIgnoreCase("pdf")) {
			type = "lb";
		}

		name = type + name;
		System.out.println(getCurrentPage().getClass().getName().toString() + "." + name);
		if (extraParam == null) {
			return getCurrentPage().getElementByName(name);
		}
		return getCurrentPage().getElementByMethod(name, extraParam);
	}

	public void i_am_at_the_page(String pageName) throws Throwable {
		setCurrentPageWithPageName(pageName);
		getCurrentPage().waitUntilLoaded();
	}

	public void i_go_to(String pageName) throws Throwable {
		setCurrentPageWithPageName(pageName);
		getCurrentPage().navigateTo();
		getCurrentPage().waitUntilLoaded();
	}
	
	public void i_should_see(String message, String elementName, String elementType, String extraParam,
			String longerWait) throws Throwable {
		System.out.print("> (" + robot.getSessionId() + ") Check: ");
		WebElement e = getElementByString(elementName, elementType, extraParam);

		if (longerWait != null && !longerWait.isEmpty()) {
			robot.waitFor(e, robot.getLongWait());
		}

		if (message != null && !message.isEmpty()) {
			message = RobotUtil.getString(message);
			robot.checkMsg(e, message);
		}
	}
	
	public void i_login(String username, String password, String otp) throws Throwable {
		i_am_at_the_page("Login");

		i_fill_in("Email", username);
		i_fill_in("Password", password);

		if (ConfigManager.getCurrentUser() == null) {
			User user = new User(username, password, otp);
			ConfigManager.setCurrentUser(user);
		}

		i_click("Signin");
		
		if (!otp.isEmpty()) {
			i_fill_in("Otp", otp);
		}
	}
	
	public void i_proceed_creating_a_new_payee(Boolean isValid, String country) throws Throwable {
		i_am_at_the_page("Account Dashboard");
		
		i_click("Make A Payment");
		i_click("Create A New Payee");
		i_click("Bank Account");
		i_click("Individual");
		
		i_fill_in("Country", country);
		
		if (isValid && !country.isEmpty()) {
			i_click("Country First Match");
			i_click("Country Next");
		}
	}
	
	public void i_fill_in_the_new_payee_info_with(String nickname, String payeeName, String payeeAddress, String payeeCity, String payeeState, String payeePostcode, String payeePhoneNumber) throws Throwable {
		i_am_at_the_page("Account Dashboard");
		
		i_fill_in("Nickname", nickname);
		i_fill_in("PayeeName", payeeName);
		i_fill_in("PayeeAddress", payeeAddress);
		i_fill_in("PayeeCity", payeeCity);
		i_fill_in("PayeeState", payeeState);
		i_fill_in("PayeePostcode", payeePostcode);
		i_fill_in("PayeePhoneNumber", payeePhoneNumber);
		
		i_click("Payee Next");
	}
	
	public void i_fill_in_the_bank_info_with(String bankCode, String branchCode, String accountNumber) throws Throwable {
		i_am_at_the_page("Account Dashboard");
		
		if (!bankCode.isEmpty()) {
			i_fill_in("Bank Code", bankCode);
			i_click("Bank Code First Match");
		}
		
		if (!branchCode.isEmpty()) {
			i_fill_in("Branch Code", branchCode);
			i_click("Branch Code First Match");
		}
		
		i_fill_in("Account Number", accountNumber);
		
		i_click("Bank Next");
		
		try {
			i_click("Payee Create");
		} catch (Exception e1) {
			
		}
	}
	
	public void i_fill_in_amount_delivery_method_and_confirm_payment(String amount, String deliveryMethod) throws Throwable {
i_am_at_the_page("Account Dashboard");
		
		i_fill_in("Amount", amount);
		
		if (!deliveryMethod.isEmpty()) {
			i_click(deliveryMethod);
		}
		
		i_click("Payment Create");
		try {
			i_click("Payment Confirm");
		} catch (Exception e1) {
			
		}
	}

	public boolean i_fill_in(String elementName, String elementValue) throws Throwable {
		return i_fill_in(elementName, elementValue, null);
	}

	public boolean i_fill_in(String elementName, String elementValue, String extraParam) throws Throwable {
		if (elementValue == null || elementValue.isEmpty()) {
			return false;
		}

		System.out.print("> (" + robot.getSessionId() + ") Type: ");
		WebElement e = getElementByString(elementName, "tf", extraParam);
		boolean isOptional = elementValue.contains("(optional)");

		if (isOptional) {
			try {
				if (!e.isDisplayed()) {
					return false;
				}
			} catch (NoSuchElementException nse) {
				return false;
			}
		}

		try {
			robot.clear(e);
			robot.type(e, RobotUtil.getString(elementValue));
			return true;
		} catch (Exception e1) {
			if (isOptional) {
				return false;
			}

			// try to first click on it
			try {
				robot.click(e);
				robot.clear(e);
				robot.type(e, RobotUtil.getString(elementValue));
				return true;
			} catch (Exception e2) {
				throw e1;
			}
		}
	}
	
	private void setCurrentPageWithPageName(String pageName) throws Throwable {
		String pageClass = BasePage.class.getPackage().getName() + "." + pageName.replaceAll(" ", "") + "Page";
		System.out.println("> (" + robot.getSessionId() + ") Check: " + pageClass);
		Class<?> clazz = Class.forName(pageClass);
		Constructor<?> constructor = clazz.getConstructor();
		setCurrentPage((BasePage) constructor.newInstance());
	}
}
