package model;

import org.mindrot.jbcrypt.BCrypt;
public class User {
	// Email
	private String email;
	// Password
	private String passowrd;

	public User() {
	}

	public User(String email, String password) {
		this.email = email;
		this.passowrd = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	

	public String getPassowrd() {
		return passowrd;
	}

	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		User temp = (User) obj;
		if (temp.getEmail().equals(email)) {
			return true;
		} else {
			return false;
		}
	}

    public String toString() {
		return "{email:" + email + ",password:+" + passowrd + "}";
	}
}
