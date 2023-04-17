package net.springboot.javaguides.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.springboot.javaguides.service.MailService;

@Controller
public class MainController {
	
	@Autowired
	private MailService mailService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home() {
		mailService.sendEmail();
		return "index";
	}
}