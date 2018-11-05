package robot;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class DesktopRobot extends Robot {

	public DesktopRobot(String platform) {
		switch (platform.toLowerCase()) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "./geckodriver");
			driver = new FirefoxDriver();
			break;
		// case "safari":
		// driver = new SafariDriver();
		// break;
		case "opera":
			System.setProperty("webdriver.opera.driver", "./operadriver");
			driver = new OperaDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("chromedriverPath", "./chromedriver"));
			driver = new ChromeDriver();
			break;
		case "headless-chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("chromedriverPath", "./chromedriver"));

			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-gpu");

			driver = new ChromeDriver(chromeOptions);
			break;
		default:
			System.setProperty("webdriver.chrome.driver", System.getProperty("chromedriverPath", "./chromedriver"));
			System.out.println("Unsupported platform: " + platform);
			System.out.println("Setting default platform to chrome");
			driver = new ChromeDriver();
		}

		driver.manage().window().setSize(new Dimension(1400, 900));
	}

	@Override
	public TestMode getMode() {
		return TestMode.Desktop;
	}
}
