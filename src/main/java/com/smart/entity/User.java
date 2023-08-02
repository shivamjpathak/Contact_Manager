package com.smart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USERS")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;
	
	@NotBlank(message="Name can not be blank.")
	@Size(min=3, max=20,message= "Minimum 2 and Maximum 20 characters are allowed.")
	private String userName;
	
	@Column(unique=true)
	//@Email(message="Please write correct email")
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotEmpty(message = "Email cannot be empty")
	private String email;
	
	@Size(min=8, message="Minimum 8 characters are required for password.")
	private String password;
	private String role;
	private boolean enabled;
	private String imgUrl;
	
	@Column(length=1000)
	@NotBlank(message = "About can not be empty")
	@Size(min=3, max=1000,message= "Minimum 3 and Maximum 1000 characters are allowed.")
	private String about;
	
	@OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY, mappedBy= "user")
	private List<Contact> contacts = new ArrayList<Contact>();
	
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", email=" + email + ", password=" + password + ", role="
				+ role + ", enabled=" + enabled + ", imgUrl=" + imgUrl + ", about=" + about + ", contacts=" + contacts
				+ "]";
	}
	
	
	
	
}
