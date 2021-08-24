package net.springboot.javaguides.Junit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.springboot.javaguides.controller.HoGiaDinhController;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.HoGiaDinh;
import net.springboot.javaguides.entity.NhanVien;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.HoGiaDinhRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.repository.UserRepository;
import net.springboot.javaguides.service.UserService;
@RunWith(SpringRunner.class) 
@WebMvcTest(HoGiaDinhController.class)
public class TestHoGiaDinhController {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private UserService userService;
	@MockBean
	private UserDetails userDetails;
	@MockBean
	private CaNhanRepository caNhanRepository;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private HoGiaDinhRepository hoGiaDinhRepository;
	@MockBean
	private NhanVienRepository nhanVienRepository;
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test1() throws Exception { // Test truong hop user chua khai bao thong tin ca nhan
		//CaNhan caNhan=new CaNhan();		// caNhan==null
		User user=new User(1L);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		//Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(caNhan);
		mockMvc.perform(get("/hogiadinh/list")).andExpect(redirectedUrl("/canhan/form"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test2() throws Exception { 
		CaNhan caNhan=new CaNhan();			// nguoi dung da khai bao thong tin
		User user=new User(1L);				// nhung chua luu ho gia dinh trong CSDL
		Mockito.when(userService.getUser(anyString())).thenReturn(user);  //caNhan.getHoGiaDinh()==null
		Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(caNhan);
		mockMvc.perform(get("/hogiadinh/list")).andExpect(redirectedUrl("/hogiadinh/list"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test3() throws Exception {
		// user da dang ki thong tin ca nhan
		//user da cos ho gia dinh
		CaNhan caNhan=new CaNhan();	
		User user=new User(1L);
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(caNhan);
		caNhan.setHoGiaDinh(hoGiaDinh);
		mockMvc.perform(get("/hogiadinh/list")).andExpect(view().name("list-ca-nhan"));
	}
	
	//test /hogiadinh/delete/{id}
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test4() throws Exception {
		// truong hop canhan_id = @PathVariable id
		User user=new User();         
		CaNhan caNhan=new CaNhan(1L);	//long id =1 
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(caNhan);
		mockMvc.perform(get("/hogiadinh/delete/{id}",1L))
			.andExpect(redirectedUrl("/hogiadinh/list?error"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test5() throws Exception {
		User user=new User();         
		CaNhan caNhan=new CaNhan(2L);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(caNhan);
		mockMvc.perform(get("/hogiadinh/delete/{id}",1L))
			.andExpect(redirectedUrl("/hogiadinh/list?delete"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testAddThanhVien_Trung_maBHXH_NhanVien() throws Exception {
		CaNhan caNhan=new CaNhan();
		Mockito.when(nhanVienRepository.findByMaBHXH(any())).thenReturn(new NhanVien());
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/hogiadinh/add").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(caNhan))).andExpect(redirectedUrl("/hogiadinh/form?duplicate"));
		
	}
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testAddThanhVien_Trung_maBHXH_CaNhan() throws Exception {
		CaNhan caNhan=new CaNhan();
		Mockito.when(caNhanRepository.findByMaBHXH(any())).thenReturn(new CaNhan());
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/hogiadinh/add").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(caNhan))).andExpect(redirectedUrl("/hogiadinh/form?duplicate"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testAddThanhVien_Ko_Trung_maBHXH() throws Exception {
		CaNhan caNhan=new CaNhan();
		Mockito.when(userService.getUser(anyString())).thenReturn(new User());
		Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(new CaNhan());
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/hogiadinh/add").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(caNhan))).andExpect(redirectedUrl("/hogiadinh/list?success"));
	}
}
