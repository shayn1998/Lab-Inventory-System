package com.contactBook.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.contactBook.model.ContactCredentials;


@Repository
public interface ContactCredentials_repo extends JpaRepository<ContactCredentials, String> {

}
