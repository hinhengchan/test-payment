package robot;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.ConfigManager;

public class RobotUtil {
	
	private static String getRandomString(int maxLength) {
		String str = getRandomString(maxLength, false);
		return str;
	}
	
	private static String getRandomNumber(int maxLength) {
		String str = getRandomString(maxLength, true);
		return str;
	}

	private static String getRandomString(int maxLength, boolean isNumber) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		if (isNumber) {
			SALTCHARS = "1234567890";
		}
		
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < maxLength) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static String getString(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}

		s = s.replaceAll("\\{autotestUser\\}", ConfigManager.getAutotestUser().getUsername());
		s = s.replaceAll("\\{autotestPassword\\}", ConfigManager.getAutotestUser().getPassword());
		s = s.replaceAll("\\{autotestOtp\\}", ConfigManager.getAutotestUser().getOtp());
		s = s.replaceAll("\\(optional\\)", "");
		if (ConfigManager.getCurrentUser() != null) {
			s = s.replaceAll("\\{CurrentUsername\\}", ConfigManager.getCurrentUser().getUsername());
		}
		
		if (s.contains("{RandomEmail}")) {
			String prefix = ConfigManager.getAutotestUser().getUsername().replaceAll("[+@].*", "");
			String postfix = ConfigManager.getAutotestUser().getUsername().replaceAll(".*@", "");
			String email = prefix + "+{Random}.{Timestamp}@" + postfix;
			s = s.replaceAll("\\{RandomEmail\\}", email);
		}

		if (s.contains("{RandomInt}")) {
			s = s.replaceAll("\\{RandomInt\\}", new Integer(new Random().nextInt(100000)).toString());
		}
		
		if (s.contains("{RandomNumber}")) {
			s = s.replaceAll("\\{RandomNumber\\}", getRandomNumber(12));
		}
		
		if (s.contains("{SystemDate}")) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate localDate = LocalDate.now();
			s = s.replaceAll("\\{SystemDate\\}", dtf.format(localDate));
		}

		Pattern p = Pattern.compile("\\{Timestamp\\}", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(s);
		if (m.find()) {
			String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			s = m.replaceAll(timestamp);
		}

		p = Pattern.compile("\\{Random:?(.*)?\\}", Pattern.CASE_INSENSITIVE);
		m = p.matcher(s);
		if (m.find()) {
			String replacedString = "";

			if (m.groupCount() > 0) {
				String option = m.group(1);
				if (option.contains(",")) {
					String[] options = option.split(",");
					replacedString = options[new Random().nextInt(options.length)];
				} else {
					try {
						replacedString = getRandomString(Integer.parseInt(option));
					} catch (NumberFormatException e) {
					}
				}

				if (replacedString.isEmpty()) {
					replacedString = getRandomString(6);
				}
			}
			s = m.replaceAll(replacedString);
		}
		return s;
	}
}
