package com.contactBook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactBook.model.ContactList;

@Repository
public interface ContactList_repo extends JpaRepository<ContactList, String>{
	
}
