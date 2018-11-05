package steps;

import java.net.MalformedURLException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import java.util.stream.Collectors;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import data.ConfigManager;

public class Hooks {
	@Before
	public void setup() throws MalformedURLException {
		ConfigManager.setup();
	}

	@After
	/**
	 * Embed a screenshot in test report if test is marked as failed
	 */
	public void embedScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			scenario.write("Current Page URL is " + ConfigManager.getRobot().getDriver().getCurrentUrl());
			
			try {
				byte[] screenshot = ((TakesScreenshot) ConfigManager.getRobot().getDriver())
						.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");				
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots.getMessage());
			}
			
			String htmlSrc = (ConfigManager.getRobot().getDriver()).getPageSource();
			scenario.embed(htmlSrc.getBytes(), "text/plain");

			LogEntries logs = (ConfigManager.getRobot().getDriver()).manage().logs().get("browser");
			scenario.embed(logs.getAll().stream().map(LogEntry::toString).collect(Collectors.joining("\n")).getBytes() , "text/plain");

			String countryCode = "countryCode = " + (ConfigManager.getRobot().getDriver()).manage().getCookieNamed("countryCode").getValue();
			scenario.embed(countryCode.getBytes(), "text/plain");
		}
		ConfigManager.getRobot().getDriver().quit();
	}
}
