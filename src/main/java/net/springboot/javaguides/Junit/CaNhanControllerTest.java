package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import net.springboot.javaguides.controller.CaNhanController;
import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CaNhanControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private CaNhanController caNhanController;
	@MockBean
	private Model model;
	
	//test đã đăng ký tài khoản đã khai thông tin
	@WithMockUser(username = "admin")
	@Test
	void testCNform_DaKhaiTT() {
		CaNhan caNhan = new CaNhan();
		String s = caNhanController.CNform(caNhan, model);
		assertEquals("in4-canhan", s);
	}
	
	//test đã đăng ký tài khoản nhưng chưa khai thông tin
	@WithMockUser(username = "test2")
	@Test
	void testCNform_ChuaKhaiTT() {
		UserService userService = this.context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		CaNhan caNhan = new CaNhan();
		String s = caNhanController.CNform(caNhan, model);
		assertEquals("dki-canhan-form", s);
	}
	
	// thêm cá nhân khi chưa khai báo thông tin
	@WithMockUser(username = "test")
	@Test
	void testaAddCaNhan() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		
		CaNhan c = new CaNhan();
		c.setName("test1");
		c.setNgaySinh(new Date());
		c.setGioiTinh("Nam");
		c.setSoCMND("12345678901");
		c.setDiaChi("Ha Noi");
		c.setEmail("test@gmail.com");
		c.setSoDT("0123456789");
		c.setThanhPho("Ha Noi");
		c.setQuan("Ha Noi");
		c.setMaBHXH("1234567890");
		c.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		
		String s = caNhanController.addCaNhan(c);
		assertEquals("redirect:/canhan/form?success", s);
		
		User u = userService.getUser("test");
		CaNhan caNhan = caNhanRepository.findbyUserId(u.getId());
		assertEquals(c, caNhan);
	}
}
