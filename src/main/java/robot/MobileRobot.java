package robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MobileRobot extends Robot {
	public MobileRobot(String platform) {
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", System.getProperty("mobileEmulation", "Nexus 5X"));

		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

		switch (platform.toLowerCase()) {
		// case "firefox":
		// System.setProperty("webdriver.gecko.driver", "./geckodriver");
		// driver = new FirefoxDriver();
		// break;
		// case "safari":
		// driver = new SafariDriver();
		// break;
		// case "opera":
		// System.setProperty("webdriver.opera.driver", "./operadriver");
		// driver = new OperaDriver();
		// break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("chromedriverPath", "./chromedriver"));
			driver = new ChromeDriver(capabilities);
			break;
		case "headless-chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("chromedriverPath", "./chromedriver"));

			List<String> args = new ArrayList<String>();
			args.add("--headless");
			args.add("--disable-gpu");

			chromeOptions.put("args", args);
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			driver = new ChromeDriver(capabilities);
			break;
		default:
			System.setProperty("webdriver.chrome.driver", System.getProperty("chromedriverPath", "./chromedriver"));
			System.out.println("Unsupported platform: " + platform);
			System.out.println("Setting default platform to chrome");
			driver = new ChromeDriver(capabilities);
			break;
		}
	}

	@Override
	public TestMode getMode() {
		return TestMode.Mobile;
	}
}
