package net.springboot.javaguides.controller;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.UserRepository;
import net.springboot.javaguides.service.UserService;
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		String email=registrationDto.getEmail();
		BigInteger a=this.userRepository.isEmailExist(email);
		if(a==BigInteger.ONE) {
			return"redirect:/registration?error";
		}else {
		userService.save(registrationDto);
		return "redirect:/registration?success";}
	}
}