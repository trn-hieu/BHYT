package net.springboot.javaguides.Junit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.springboot.javaguides.controller.CaNhanController;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.UserRepository;
import net.springboot.javaguides.service.UserService;
@RunWith(SpringRunner.class) 

@WebMvcTest(CaNhanController.class)
public class TestCaNhanController {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private UserService userService;
	@MockBean
	private CaNhanRepository caNhanRepository;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserDetails userDetails;
	
	
//	@BeforeEach
//	public void setup() {
//		mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
//	}
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test 
	public void testCa_Nhan_Da_DKi() {
		try {
			CaNhan caNhan=new CaNhan();
			//verify(userService).getUser(anyString());
			User user=new User(1L);
			Mockito.when(userService.getUser(anyString())).thenReturn(user);
			Mockito.when(caNhanRepository.findbyUserId(any())).thenReturn(caNhan);
			mockMvc.perform(get("/canhan/form")).andExpect(view().name("in4-canhan"));
			//.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void test_Ca_Nhan_Chua_DKi() {
		try {
			User user=new User(1L);
			Mockito.when(userService.getUser(anyString())).thenReturn(user);
			mockMvc.perform(get("/canhan/form")).andExpect(view().name("dki-canhan-form"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//@AutoConfigureMockMvc(addFilters = false)
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test_add_CaNhan() throws Exception {
		CaNhan caNhan=new CaNhan();
		User user=new User(6412L);
		ObjectMapper objectMapper=new ObjectMapper();
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/canhan/add").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(caNhan))).andExpect(redirectedUrl("/canhan/form?success"));
		//mockMvc.perform(post("/canhan/add").contentType(MediaType.APPLICATION_JSON).content(Json)).andExpect(status().isOk());
	}
	
	
}
