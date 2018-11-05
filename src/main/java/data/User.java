package data;

public class User {
	private String username;
	private String password;
	private String otp;

	public User(String username, String password, String otp) {
		this.username = username;
		this.password = password;
		this.otp = otp;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getOtp() {
		return otp;
	}
}
