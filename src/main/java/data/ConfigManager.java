package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import robot.DesktopRobot;
import robot.MobileRobot;
import robot.Robot;

public class ConfigManager {
	private static Robot robot;
	private static User autotestUser = null;
	private static User currentUser = null;
	private static String baseUrl = null;
	private static String platform = null;
	private static String mode = null;
	private static String timeoutSecond = null;
	private static String timeoutLongSecond = null;

	public static void readConfig() {
		String autotestEmail = null;
		String autotestPassword = null;
		String autotestOtp = null;

		// read from config.properties
		try (FileInputStream fileInput = new FileInputStream(new File("config.properties"))) {
			Properties prop = new Properties();
			prop.load(fileInput);

			mode = prop.getProperty("mode", "chrome");
			platform = prop.getProperty("platform", Robot.TestMode.Desktop.toString());
			baseUrl = prop.getProperty("baseUrl", null);
			autotestEmail = prop.getProperty("autotestEmail", null);
			autotestPassword = prop.getProperty("autotestPassword", null);
			autotestOtp = prop.getProperty("autotestOtp", null);
			timeoutSecond = prop.getProperty("timeoutSecond", null);
			timeoutLongSecond = prop.getProperty("timeoutLongSecond", null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		baseUrl = System.getProperty("baseUrl", baseUrl);
		timeoutSecond = System.getProperty("timeoutSecond", timeoutSecond);
		timeoutLongSecond = System.getProperty("timeoutLongSecond", timeoutLongSecond);
		platform = System.getProperty("platform", platform);

		autotestEmail = System.getProperty("autotestEmail", autotestEmail);
		autotestPassword = System.getProperty("autotestPassword", autotestPassword);
		autotestOtp = System.getProperty("autotestOtp", autotestOtp);

		autotestUser = new User(autotestEmail, autotestPassword, autotestOtp);

		mode = System.getProperty("mode", mode);

		System.out.println(String.format("Platform: %s", platform));
		System.out.println(String.format("Mode: %s", mode));
	}

	public static void setup() throws MalformedURLException {
		readConfig();
		Robot.TestMode emode = Robot.TestMode.Desktop;
		try {
			emode = Robot.TestMode.valueOf(mode);
		} catch (IllegalArgumentException e) {
		}

		switch (emode) {
		case Mobile:
			robot = new MobileRobot(platform);
			break;
		case Desktop:
		default:
			robot = new DesktopRobot(platform);
		}

		robot.setBaseUrl(baseUrl);
		try {
			robot.setShortTimeout(new Integer(timeoutSecond));
		} catch (NumberFormatException e1) {
		}

		try {
			robot.setLongTimeout(new Integer(timeoutLongSecond));
		} catch (NumberFormatException e1) {
		}
		robot.setWaits();
		robot.setSessionId();
		robot.navigateTo("");
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static Robot getRobot() {
		return robot;
	}

	public static User getAutotestUser() {
		return autotestUser;
	}

	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	public static User getCurrentUser() {
		return currentUser;
	}
}
