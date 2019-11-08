package com.prs.business;

import javax.persistence.*;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id; 
	private String userName;
	private String password;
	private String firstName; 
	private String lastName;
	private String phoneNumber;
	private String email; 
	private boolean isReviewer;
	private boolean isAdmin;
	
	public User() {
		super();
	}

	public User(int id, String userName, String password, String firstName, String lastName, String phoneNumber,
			String email, boolean isReviewer, boolean isAdmin) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isReviewer = isReviewer;
		this.isAdmin = isAdmin;
	}



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isReviewer() {
		return isReviewer;
	}

	public void setIsReviewer(boolean isReviewer) {
		this.isReviewer = isReviewer;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", lastName=" + lastName
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", isReviewer=" + isReviewer + ", isAdmin="
				+ isAdmin + "]";
	}

	
	
	
	
	
	

}
