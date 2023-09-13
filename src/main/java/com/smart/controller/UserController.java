package com.smart.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactsRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactsRepository contactRepository;
	
	@ModelAttribute
	public void commonData(Model model, Principal principal)
	{
		String userName= principal.getName();
		
		User user= userRepository.loadUserDetailsByUserName(userName);
		
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String dashboard(Principal principal, User user, Model model)
	{
		
		//commenting below code because commonData() method will be automatically available to all controller due to @ModelAttribute Annotation
		
		/*
		String userName= principal.getName();
		
		user= userRepository.loadUserDetailsByUserName(userName);
		
		model.addAttribute("user", user);
		
		*/
		
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add_contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title", "Add Contact Form");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
	@PostMapping("/process_contact")
	public String getContact(@ModelAttribute("contact") Contact contact, @RequestParam("profileImg") MultipartFile file,@RequestParam("phone") Long phone, Principal principal, HttpSession session)
	{

		try {
			
			String userName= principal.getName();
			
			User user= userRepository.loadUserDetailsByUserName(userName);

			contact.setUser(user);
			
			//contact.setImage("contact.png");
			
			user.getContacts().add(contact);
				
			this.userRepository.save(user);
			
			Integer id= this.contactRepository.getContactIdByPhone(phone);
			Contact existingContact= this.contactRepository.getReferenceById(id);
			
			//Setting profile Image
			if(file.isEmpty())
			{
				System.out.println("file is empty");
				existingContact.setImage("contact.png");
				
			}
			else
			{
				
			String fileName= id + file.getOriginalFilename();	//appended image file name with contact id for uniqueness
			
			System.out.println("********"+ fileName);
			
			existingContact.setImage(fileName);	
			//contact.setImage(fileName);
			System.out.println("**********");
			//File saveFile= new ClassPathResource("/static/img").getFile();	//get img folder location
			//InputStream saveFile=this.getClass().getResourceAsStream("static/img/");
			System.out.println("**********");
			//System.out.println(saveFile);
				System.out.println("**********");
			//Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);	//generate path to img folder
			//Path path= Paths.get(saveFile+fileName);
			//Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			/*
			Path path = new File(getClass().getClassLoader().getResource("static/img/")
					.getFile()).toPath();
			Path path2= Paths.get(path+"/"+fileName);
			*/
			Path path4= Paths.get("file:/app.jar/BOOT-INF/classes/static/img/"+ fileName);
			
			System.out.println(path4);
			Files.copy(file.getInputStream(),path4, StandardCopyOption.REPLACE_EXISTING);


				
//			InputStream is = this.getClass().getClassLoader().getResourceAsStream("BOOT-INF/classes/static/img");
//			
//			Path path= Paths.get(is+File.separator+fileName);	//generate path to img folder
//			
//			Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			
			//StreamUtils.copy(file.getInputStream(),(OutputStream) path);
			
			System.out.println("Image id uploaded");
			
			//existingContact.setImage(id + file.getOriginalFilename());
			
			
			}
			
			//Saving user again to save image
			this.userRepository.save(user);
			
			System.out.println("Contact is Saved!");
			
			session.setAttribute("message", new Message("Contact is saved Successfully! Add More...","alert-success"));
		}
		catch(Exception e)
		{
			//e.getMessage();
			
			System.out.println(e);
			
			//session.setAttribute("message", new Message("Something went wrong!","alert-danger"));
			session.setAttribute("message", new Message(e.getMessage(),"alert-danger"));
		}
			
		return "normal/add_contact_form";	
		
		
	}
	
	@GetMapping("viewContacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Principal principal, Model model, Pageable pageable)
	{
		
		String userName= principal.getName();
		
		User user= this.userRepository.loadUserDetailsByUserName(userName);
		
		pageable= PageRequest.of(page, 7);
		
		Page<Contact> Contacts= this.contactRepository.getContactByUserId(user.getId(), pageable);

		model.addAttribute("contacts",Contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", Contacts.getTotalPages());
		
		return "normal/view_Contacts";
	}
	@GetMapping("/viewContacts/{page}/CM{cId}")
	public String openContactRecord(@PathVariable("cId") Integer cId, Model model)
	{
		Contact contact = this.contactRepository.getReferenceById(cId);
		model.addAttribute(contact);
		return "normal/openContactRecord";
	}

	@GetMapping("delete/{page}/{id}")
	public String deleteContact(@PathVariable("id") Integer id,@PathVariable("page") Integer page, HttpSession session)
	{
		this.contactRepository.deleteContact(id);
		
		session.setAttribute("message", new Message("Contact is deleted Successfully", "alert-success"));	
		
		return "redirect:/user/viewContacts/"+ page;
	}
	
	
	//Open Update Form 
	@PostMapping("/update/{id}")
	public String updateContact(@PathVariable("id") Integer cId, Model model)
	{
		Contact contact =this.contactRepository.findById(cId).get();
		model.addAttribute("contact", contact);
		return "normal/update_contact_form";
	}
	
}


