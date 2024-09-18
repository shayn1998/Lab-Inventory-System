package com.contactBook.model;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;


@Component
@Entity
@DynamicUpdate
@Cacheable
public class ContactList {
	
//	@OneToOne(fetch = FetchType.EAGER, mappedBy = "UserProfile")
	
	private String name;
	@Id
	private String phoneNumber;
	private String email;
		
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone_number() {
		return phoneNumber;
	}
	public void setPhone_number(String phone_number) {
		this.phoneNumber = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "ContactList [name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

}
