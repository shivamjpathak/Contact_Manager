package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String home(Model model)
	{
		
		model.addAttribute("title", "Home- Smart Contact Manager");
		return "home";
	}
	

	@GetMapping("/about")
	public String about(Model model)
	{
		
		model.addAttribute("title", "About- Smart Contact Manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		
		model.addAttribute("title", "Register- Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value="/do_Registration" ,method= RequestMethod.POST)
	public String registration(@ Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="agreement", defaultValue="false") boolean agreement, Model model, HttpSession session) throws Exception
	{
		try {
		System.out.println("Agreement" + agreement);
		System.out.println("User" + user);
		
		

		if(agreement==false)
		{
			System.out.println("Please Accept the Terms and Condition");
			throw new Exception();
		}
		
		if(result.hasErrors())
		{
			model.addAttribute("user", user);
			return "signup";
		}
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		this.userRepository.save(user);
		
		model.addAttribute("user", new User());
		
		session.setAttribute("message", new Message("Registered Successfully!", "alert-success"));
		
		return "signup";
		}
		
		
		catch(Exception e)
		{
			e.getStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong!","alert-danger"));	
			return "signup";
		}
		
		
	}
	
	@GetMapping("/signin")
	public String login(@ModelAttribute("user") User user, Model model, Principal principal)
	{
		
		
		return "login";
	}
	
}
