package com.ait.grindsregister;

public class Tutor {
	@Override
	public String toString() {
		return "Tutor [tut_id=" + tut_id + ", tut_name=" + tut_name + ", email=" + email
				+ ", phone=" + phone + ", username=" + username + ", password="
				+ password + "]";
	}

	private int tut_id;
	private String tut_name, email, phone, username, password ;
	
	public Tutor(int tut_id, String tut_name, String email, String phone, String username, String password) {
		this.tut_id = tut_id;
		this.tut_name = tut_name;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
	}
	
	public Tutor() { }

	public int getId() {
		return tut_id;
	}

	public void setId(int id) {
		this.tut_id = id;
	}

	public String getName() {
		return tut_name;
	}

	public void setName(String name) {
		this.tut_name = name;
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
	
}
