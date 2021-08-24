package net.springboot.javaguides.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.UserRepository;
import net.springboot.javaguides.service.UserService;

@Controller
@RequestMapping("canhan")
public class CaNhanController {
	@Autowired
	private CaNhanRepository caNhanRepository;
	@Autowired
	private UserService userService;
	
	
	@GetMapping("form")
	public String CNform(CaNhan caNhan,Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
		
		Long user_id=u.getId();
		
		if(caNhanRepository.findbyUserId(user_id)!=null)  {
			
			 
			  model.addAttribute("canhan",caNhanRepository.findbyUserId(user_id));
			 
			return"in4-canhan";}
		 else 
			 return"dki-canhan-form";
	}
	
	@PostMapping("add")
	public String addCaNhan(CaNhan caNhan) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        caNhan.setUser(u);
		this.caNhanRepository.save(caNhan);
		return "redirect:/canhan/form?success";
	}
	
	
}
