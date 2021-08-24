package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.springboot.javaguides.controller.UserRegistrationController;
import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.UserRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public  class UserRegistrationControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserRegistrationController userRegistrationController;
	
	// Đăng ký tài khoản thành công
	@WithMockUser(username = "test2")
	@Test
	public void testRegisterUserAccount_Success() {
		UserRepository userRepository = this.context.getBean(UserRepository.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		
		String s = userRegistrationController.registerUserAccount(user);
		assertEquals("redirect:/registration?success", s);
		
		User test = userRepository.findByEmail(user.getEmail());
		assertEquals(test.getEmail(), user.getEmail());
	}
	
	//Đăng ký tài khoản thất bại do tên tài khoản đã tồn tại
	@WithMockUser(username = "test2")
	@Test
	public void testRegisterUserAccount_Fail() {
		UserService userService = this.context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		String s = userRegistrationController.registerUserAccount(user);
		assertEquals("redirect:/registration?error", s);
	}
	
	@Test
	public void testShowRegistrationForm() {
		assertEquals("registration", userRegistrationController.showRegistrationForm());
	}

}
