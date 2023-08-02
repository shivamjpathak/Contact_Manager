package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class MyConfig implements WebSecurityConfigurer{

	@Override
	public void init(SecurityBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(SecurityBuilder builder) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	@Bean
	public UserDetailsService getUserDetailsService()
	{
			return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(authenticationProvider());
	}

	/*
	  @Bean
      public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
		  http.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER")
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.requestMatchers("/**").permitAll()
			.and().formLogin().loginPage("/signin")
			.loginProcessingUrl("/Processing_login")
		  	.defaultSuccessUrl("/user/index")
			.and().csrf().disable();
              
		  return http.build();
      }
      */
	  
	  @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  http
		  .authorizeHttpRequests((authorize) -> authorize
				  .requestMatchers("/user/**").hasRole("USER")
				  .requestMatchers("/admin/**").hasRole("ADMIN")
				  .requestMatchers("/**").permitAll())
		  .csrf(csrf -> csrf.disable())
		  .formLogin(formLogin ->formLogin.loginPage("/signin")
		  .loginProcessingUrl("/Processing_login")
		  .defaultSuccessUrl("/user/index", true));
			    
              
		  return http.build();
      }
      

	
}
