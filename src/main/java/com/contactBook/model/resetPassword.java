package com.contactBook.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.stereotype.Component;


@Component
@Entity
public class resetPassword {
	
	@Id
	private String phoneNumber;
	
	private int securityKey;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(int securityKey) {
		this.securityKey = securityKey;
	}

	@Override
	public String toString() {
		return "resetPassword [phoneNumber=" + phoneNumber + ", securityKey=" + securityKey + "]";
	}
	
}
