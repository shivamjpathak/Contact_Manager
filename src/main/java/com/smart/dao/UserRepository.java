package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	//@Query(value= "select u form User u where u.email = :email", nativeQuery=true)
	@Query("select u from User u where u.email = :email")
	public User loadUserDetailsByUserName(@Param("email") String email);
	

}
