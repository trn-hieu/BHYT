package net.springboot.javaguides.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.User;


public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
	User getUser(String username);
}
