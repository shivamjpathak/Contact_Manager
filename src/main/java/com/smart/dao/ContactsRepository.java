package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.Contact;

public interface ContactsRepository extends JpaRepository<Contact, Integer>{

	
	@Query("from Contact as c where c.user.id = :userId")
	
	//Before Implementing pagination
	//public List<Contact> getContactByUserId(@Param("userId") int userId);
	
	//After Implementing pagination
	public Page<Contact> getContactByUserId(@Param("userId") int userId, Pageable pageable);
	
	
	
	@Query("select c.cId from Contact c where c.phone= :phone")
	public Integer getContactIdByPhone(@Param("phone") long phone);
	
	@Query("select c.cId from Contact c where c.phone= :phone")
	public Integer getContactByPhone(@Param("phone") long phone);
}
