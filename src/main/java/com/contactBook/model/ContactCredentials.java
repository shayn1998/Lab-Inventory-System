package com.contactBook.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;
@Component
@Entity
@DynamicUpdate
@Cacheable
public class ContactCredentials {
	
	@Id
	private String phoneNumber;
	private String password;
	
	@OneToOne()
	@MapsId
	private ContactList contactlist;
	
	
	@Override
	public String toString() {
		return "ContactCredentials [phoneNumber=" + phoneNumber + ", password=" + password + ", contactlist="
				+ contactlist + "]";
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public ContactList getContactlist() {
		return contactlist;
	}


	public void setContactlist(ContactList contactlist) {
		this.contactlist = contactlist;
	}

	
	
	
	
	
}
	
	
	
