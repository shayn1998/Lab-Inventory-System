package com.contactBook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactBook.model.resetPassword;

@Repository
public interface ResetPassword_repo extends JpaRepository<resetPassword, String>{

}
