package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	public UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user= userRepo.loadUserDetailsByUserName(username);
		
		if(user== null)
		{
			throw new UsernameNotFoundException("User Not Found!");
		}
		
		CustomUserDetails userDetails= new CustomUserDetails(user);
		
		return userDetails;
	}

	
	
}
