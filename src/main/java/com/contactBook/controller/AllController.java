package com.contactBook.controller;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import java.lang.Math;
import com.contactBook.dao.ContactCredentials_repo;
import com.contactBook.dao.SpamTable_repo;
import com.contactBook.dao.ContactList_repo;
import com.contactBook.dao.ResetPassword_repo;
import com.contactBook.model.ContactCredentials;
import com.contactBook.model.ContactList;
import com.contactBook.model.SpamTable;
import com.contactBook.model.resetPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllController {

	@Autowired
	ContactCredentials_repo credentialsRepo;
	@Autowired
	ContactList_repo contactsRepo;
	@Autowired
	SpamTable_repo spamRepo;
	@Autowired
	ResetPassword_repo rp_repo;

	
	
	@PostMapping("/")
	public String login() {
		String s = "loginPage";
		return s;
	}


	@PostMapping("/RegisterUser")
	public String registerUser(@RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber, @RequestParam(value = "email", required = false) String email, @RequestParam("password") String password) {
		String s = "";
		if(phoneNumber.length() == 10) {
			if(name.length() > 0) {
				if(password.length() > 5) {
					ContactList contact_list = new ContactList();
					contact_list.setName(name);
					contact_list.setPhone_number(phoneNumber);
					if(email != null){
						contact_list.setEmail(email);
					}
					
		
					contactsRepo.saveAndFlush(contact_list);
					ContactCredentials contact_credentials = new ContactCredentials();
					contact_credentials.setPhoneNumber(phoneNumber);
					contact_credentials.setPassword(password);
					contact_credentials.setContactlist(contact_list);
					credentialsRepo.saveAndFlush(contact_credentials);
					SpamTable spam_table = new SpamTable();
					spam_table.setPhoneNumber(phoneNumber);
					spamRepo.saveAndFlush(spam_table);
					s = "User Registered Successfully";
				}
				
				else {
					s = "Enter a Long Password";
				}
				
			}
				
			else {
				s = "Enter a valid name";
				}
			
		}
		
		else {
			s = "Enter Valid Number";
		}
		
		return s;
		
	}
		
		
		

	@PostMapping("/loginCredentials")
	public String loginUser(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password)
	{
		String s = "";
		if(credentialsRepo.existsById(phoneNumber)) {
			ContactCredentials cr = credentialsRepo.findById(phoneNumber).orElse(null);
			if ((cr.getPassword()).equals(password)){
				s = "User Home Page";
			}
			else {
				s = "Invalid Password";
			}	
			}
		else {
			s = "User Doesn't Exists";
		}
		return s;
		}
	
	
	// generates unique key
	public void generateOTP(String phoneNumber) {	
		int min = 1000000;
		int max = 9999999;
		int key = (int)(Math.random()*(max-min+1)+min);
		if(rp_repo.existsById(phoneNumber)) {
			resetPassword rp = rp_repo.findById(phoneNumber).orElse(null);
			rp.setSecurityKey(key);
			rp_repo.saveAndFlush(rp);
		}
		else {
			resetPassword rp = new resetPassword();
			rp.setPhoneNumber(phoneNumber);
			rp.setSecurityKey(key);
			rp_repo.saveAndFlush(rp);
		}
	}

	
	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam("phoneNumber") String phoneNumber) {
		String s = "";
		if(credentialsRepo.existsById(phoneNumber)) {
			generateOTP(phoneNumber);
			s = "Sending an otp on "+phoneNumber;
		}
		return s;
	}
	
	
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("otp") int otp, @RequestParam("newPassword") String newPassword) {
		String s = "";
		if(credentialsRepo.existsById(phoneNumber)) {
			if(rp_repo.existsById(phoneNumber)) {
				resetPassword rp1 = rp_repo.getById(phoneNumber);
				if(rp1.getSecurityKey() == otp) {
					ContactCredentials contact_credentials = credentialsRepo.getById(phoneNumber);
					contact_credentials.setPassword(newPassword);
					credentialsRepo.save(contact_credentials);
					s = "Password Changed Successfully";
			}
				else {
					s = "wrong otp";
				}
		}
			
			else {
				s = "Wrong phone Number";
			}
			
		}
		else {
			s = "Invalid Contact Number";
		}
		
		
	return s;
	}
	

	@PostMapping("/reportSpam")
	@Transactional
	public String reportSpam(@RequestParam("phoneNumber") String phoneNumber) {
		String s = "";
		if(spamRepo.existsById(phoneNumber)) {
			SpamTable spam_table = spamRepo.findById(phoneNumber).orElse(null);
			int spam_report = spam_table.getSpamReports();
			spam_report += 1;
			spam_table.setSpamReports(spam_report);
			spamRepo.saveAndFlush(spam_table);
			s = "Spam Reported Successfully";
		}
		else {	
			s = "Contacts doesn't exist in our directory";
		}
		return s;
	}
	
	
	@PostMapping("/searchContact")
	public String searchContact(@RequestParam("searchType") String searchType, @RequestParam("searchKey") String searchKey){
		String s = "";
		if (searchType.equals("phoneNumber")){
			if(searchKey.length() == 10) {
				if(contactsRepo.existsById(searchKey)){
					ContactList contact_list = contactsRepo.findById(searchKey).orElse(null);
					SpamTable spam_table = spamRepo.findById(searchKey).orElse(null);
					s = contact_list.getName()+", "+contact_list.getPhone_number()+", "+spam_table.getSpamReports();
				}
				else {	
					s = "Phone Number doesn't exists";
				}
			}	
			else {	
					int flag = 0;
					List<ContactList>  li = new ArrayList<>();
					li = contactsRepo.findAll();
					for(int i = 0; i < li.size(); i += 1) {
						ContactList contact_list = li.get(i);
						if((contact_list.getPhone_number()).contains(searchKey)) {
							flag = 1;
							if(i != li.size()-1) {
								s += contact_list.getName() + ", ";
							}
							else {	
								s += contact_list.getName();
							}
						}
					}
					if(flag == 0) {
						s += "No such contact exists";
					}
				}
			}
		
		else if(searchType.equals("name")){
			int flag = 0;
			List<ContactList> li2 = new ArrayList<>();
			li2 = contactsRepo.findAll();
			for(int j = 0; j < li2.size(); j += 1) {
				ContactList contact_list = li2.get(j);
				if((contact_list.getName()).contains(searchKey)) {
					if(j != li2.size()-1) {
						s += contact_list.getName() + ", ";
						flag = 1;
					}
					else {	
						s += contact_list.getName();
						flag = 1;
					}
				}
			}
			if(flag == 0) {	
				s = "No such user exists";
			}
		}
		return s;
	}
}