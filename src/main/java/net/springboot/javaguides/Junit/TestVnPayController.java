package net.springboot.javaguides.Junit;

import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.springboot.javaguides.controller.VnpayController;
import net.springboot.javaguides.entity.VnPay;
import net.springboot.javaguides.repository.ThanhToanRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class) 
@WebMvcTest(controllers = VnpayController.class)
public class TestVnPayController {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ThanhToanRepository thanhToanRepository;
	@MockBean
	private UserService userService;
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testCreatePayment() throws JsonProcessingException, Exception {
		VnPay vnPay=new VnPay();
		vnPay.setVnp_OrderInfo("thanhtoan");
		vnPay.setVnp_OrderType("topup");
		vnPay.setVnp_Locale("vn");
		vnPay.setVnp_BankCode("NCB");
		vnPay.setVnp_Amount(100000L);
		HttpServletRequest request=mock(HttpServletRequest.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/vnpay/make",42L).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(vnPay))).andExpect(status().isOk());
	}
}
