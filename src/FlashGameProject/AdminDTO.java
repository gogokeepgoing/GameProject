package FlashGameProject;

public class AdminDTO {
	private String id, username, password, passwordre, email, phone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordre() {
		return passwordre;
	}

	public void setPasswordre(String passwordre) {
		this.passwordre = passwordre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "AdminDTO [id=" + id + ", username=" + username + ", password=" + password + ", passwordre=" + passwordre
				+ ", email=" + email + ", phone=" + phone + "]";
	}

	
}
