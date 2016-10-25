package com.ait.grindsregister;

public class Grind {
	private int g_id, tut_id, sub_id;
	private String tut_name, email, phone, price, description;
	
	public Grind(){}
	
	public Grind(int g_id, int tut_id, int sub_id, String tut_name, String email, String phone, String price, String description){
		this.g_id = g_id;
		this.tut_id = tut_id;
		this.sub_id = sub_id;
		this.tut_name = tut_name;
		this.email = email;
		this.phone = phone;
		this.price = price;
		this.description = description;
		
	}

	public String getTut_name() {
		return tut_name;
	}

	public void setTut_name(String tut_name) {
		this.tut_name = tut_name;
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

	public int getGrindId() {
		return g_id;
	}

	public void setGrindId(int grindId) {
		this.g_id = grindId;
	}

	public int getTutorId() {
		return tut_id;
	}

	public void setTutorId(int tutorId) {
		this.tut_id = tutorId;
	}

	public int getSubjectId() {
		return sub_id;
	}

	public void setSubjectId(int subjectId) {
		this.sub_id = subjectId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}