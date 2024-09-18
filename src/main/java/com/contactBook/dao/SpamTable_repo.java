package com.contactBook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactBook.model.SpamTable;

public interface SpamTable_repo extends JpaRepository<SpamTable, String> {

	
}
