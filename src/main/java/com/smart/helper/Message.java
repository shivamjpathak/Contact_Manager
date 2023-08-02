package com.smart.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component("Message")
public class Message{

	public String content;
	public String type;
	
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void removeMessage() {
        try {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
//            /HttpSession session = request.getSession();
            session.removeAttribute("message");
        } catch (RuntimeException e) {
            e.getStackTrace();
        }
    }
}
