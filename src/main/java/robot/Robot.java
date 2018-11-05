package robot;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract public class Robot {

	public enum TestMode {
		Mobile, Desktop
	}

	protected WebDriver driver;
	protected String baseUrl = "https://neat-business-qa.ap-southeast-1.elasticbeanstalk.com";
	protected Integer shortTimeout = 10;
	protected Integer longTimeout = 60;
	protected int shortHaltMs = 500;
	protected int maxRetries = 20;
	protected WebDriverWait shortWait;
	protected WebDriverWait longWait;
	protected String sessionId;

	public WebDriver getDriver() {
		return driver;
	}

	public void setSessionId() {
		this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setWaits() {
		shortWait = new WebDriverWait(driver, shortTimeout);
		longWait = new WebDriverWait(driver, longTimeout);
	}

	public WebDriverWait getWait() {
		return shortWait;
	}

	public WebDriverWait getLongWait() {
		return longWait;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		if (baseUrl == null)
			return;

		this.baseUrl = baseUrl;
	}

	public Integer getShortTimeout() {
		return shortTimeout;
	}

	public void setShortTimeout(Integer shortTimeout) {
		this.shortTimeout = shortTimeout;
	}

	public Integer getLongTimeout() {
		return longTimeout;
	}

	public void setLongTimeout(Integer longTimeout) {
		this.longTimeout = longTimeout;
	}

	public void navigateTo(String relativeUrl) {
		driver.navigate().to(baseUrl + relativeUrl);
	}

	public boolean isMobile() {
		return getMode() == TestMode.Mobile;
	}

	public boolean isExist(WebElement e, WebDriverWait wdw) {
		try {
			WebElement element = wdw.until(ExpectedConditions.visibilityOf(e));

			if (element != null) {
				return true;
			}
		} catch (Exception e1) {
		}

		return false;
	}

	public WebElement waitFor(WebElement e, WebDriverWait wdw) {
		try {
			wdw.until(ExpectedConditions.visibilityOf(e));
		} catch (StaleElementReferenceException se) {
			// ignore stale element
		} catch (NoSuchElementException | TimeoutException ne) {
			throw ne;
		} catch (WebDriverException wde) {
			System.err.println(wde.getMessage());
		}
		return e;
	}

	// waits until a mobile element is found on the page
	public WebElement waitFor(WebElement e) {
		return waitFor(e, getWait());
	}

	private WebElement moveToElement(WebElement e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
		Actions actions = new Actions(driver);
		actions.moveToElement(e);
		actions.perform();
		return e;
	}

	public void scroll(boolean up) {
		if (up) {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-200)", "");
		} else {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)", "");
		}
	}

	private void waitForClickable(WebElement e) {
		shortHalt();
		getWait().until(ExpectedConditions.elementToBeClickable(e));
	}

	public void waitForInvisible(WebElement e) {
		try {
			getWait().until(ExpectedConditions.invisibilityOf(e));
		} catch (Exception err1) {
			System.out.println(err1.getMessage());
		}
	}

	public WebElement click(WebElement e) {
		waitForClickable(e);

		new RetryElementAction() {
			@Override
			public void doAction() {
				e.click();
			}

			@Override
			public void retryAction(int attempt) {
				moveToElement(e);

				switch (attempt % 4) {
				case 1:
					scroll(true);
					break;
				case 2:
					scroll(false);
					break;
				case 3:
					scrollToTop();
					break;
				default:
					break;
				}
			}

		}.execute(maxRetries);

		return e;
	}

	public void scrollToTop() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
	}

	public WebElement type(WebElement e, String string) {
		if (string.isEmpty()) {
			return e;
		}
		waitForClickable(e);
		e.sendKeys(string);
		return e;
	}

	public WebElement clear(WebElement e) {
		waitForClickable(e);
		e.clear();
		return e;
	}

	public WebElement select(WebElement e, String string) {
		waitForClickable(e);

		new RetryElementAction() {
			@Override
			public void doAction() {
				try {
					new Select(e).selectByValue(string);
				} catch (NoSuchElementException nse) {
					new Select(e).selectByVisibleText(string);
				}
			}
		}.execute(maxRetries);

		return e;
	}

	public String getText(WebElement e) {
		waitFor(e);
		return e.getText();
	}

	public void shortHalt() {
		halt(shortHaltMs);
	}

	private void halt(int millisSecond) {
		try {
			Thread.sleep(millisSecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void checkMsg(WebElement e, String... expected) {
		if (expected.length < 0) {
			return;
		}

		new RetryElementAction() {
			@Override
			public void doAction() {
				String format = "Actual: '%s', Expected: '%s'";
				String actual = getText(e).toLowerCase();
				String expectedMsg = String.join(" OR ", expected);

				for (String expectedString : expected) {
					if (actual.contains(expectedString.toLowerCase())) {
						// found match
						return;
					}
				}
				throw new WebDriverException(String.format(format, actual, expectedMsg));
			}
		}.execute(maxRetries);
	}

	public void switchToFrame(WebElement e) {
		waitFor(e);
		driver.switchTo().frame(e);
	}

	abstract public class RetryElementAction {
		abstract public void doAction();

		public void retryAction(int attempt) {
		}

		public void execute(int maxRetries) {
			int attempt = 0;
			do {
				try {
					doAction();
					break;
				} catch (WebDriverException wde) {
					// System.out.println(wde.getMessage());
					if (attempt++ >= maxRetries) {
						throw wde;
					}

					shortHalt();
					retryAction(attempt);
				}
			} while (true);
		}
	}

	public boolean hasClass(WebElement element, String className) {
		String classes = element.getAttribute("class");
		for (String c : classes.split(" ")) {
			if (c.equals(className)) {
				return true;
			}
		}
		return false;
	}

	public void hover(WebElement e) {
		Actions action = new Actions(driver);
		action.moveToElement(e).perform();
	}

	abstract public TestMode getMode();
}
