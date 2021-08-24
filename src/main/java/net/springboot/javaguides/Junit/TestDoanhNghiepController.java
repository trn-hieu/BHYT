package net.springboot.javaguides.Junit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import net.springboot.javaguides.controller.DoanhNghiepController;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class) 
@WebMvcTest(DoanhNghiepController.class)
public class TestDoanhNghiepController {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DoanhNghiepRepository doanhNghiepRepository;
	@MockBean
	private UserService userService;
	@MockBean
	private NhanVienRepository nhanVienRepository;

	
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test1() throws Exception {
		// truong hop doanhNghiepRepository.findbyUserId(user_id)==null
		User user=new User();
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(null);
		mockMvc.perform(get("/doanhnghiep")).andExpect(view().name("dki-doanhNghiep-form"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test2() throws Exception {
		User user=new User();
		DoanhNghiep doanhNghiep=new DoanhNghiep();
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(doanhNghiep);
		mockMvc.perform(get("/doanhnghiep")).andExpect(status().isOk());
	}
	
	
	
}
