package com.contactBook.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class SpamTable {
	
	@Id
	private String phoneNumber;
	private int spamReports = 0;
	
	
	
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}





	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}





	public int getSpamReports() {
		return spamReports;
	}





	public void setSpamReports(int spamReports) {
		this.spamReports = spamReports;
	}





	@Override
	public String toString() {
		return "SpamTable [phoneNumber=" + phoneNumber + ", spamReports=" + spamReports + "]";
	}
	

}
