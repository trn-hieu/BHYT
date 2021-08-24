package net.springboot.javaguides.Junit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.springboot.javaguides.controller.NhanVienController;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.NhanVien;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class) 
@WebMvcTest(NhanVienController.class)
@AutoConfigureMockMvc
public class TestNhanVienController {
	@Autowired
    private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private UserService userService;
	@MockBean
	private NhanVienRepository nhanVienRepository;
	@MockBean
	private DoanhNghiepRepository doanhNghiepRepository;
	@MockBean
	private CaNhanRepository caNhanRepository;
	
//	@BeforeEach
//	public void setup() {
//		mockMvc=MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
//	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testShowForm() throws Exception {
		HttpSession session=mock(HttpSession.class); 
		HttpServletRequest request=mock(HttpServletRequest.class);
		NhanVien nhanVien=new NhanVien();  //nhan vien ko null
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute(any())).thenReturn(nhanVien);
		mockMvc.perform(get("/nhanvien")).andExpect(view().name("add-nhan-vien"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testShowForm2() throws Exception {
		NhanVien nhanVien=null;                  // nhan vien null
		HttpSession session =mock(HttpSession.class);
		HttpServletRequest request=mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute(any())).thenReturn(nhanVien);
		mockMvc.perform(get("/nhanvien")).andExpect(view().name("add-nhan-vien"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testNewNhanVien() throws Exception { //checkDuplicate  != null
		NhanVien nhanVien=new NhanVien(); 			// trùng mã BHXH với nhân viên khác
		Mockito.when(nhanVienRepository.findByMaBHXH(any())).thenReturn(new NhanVien());
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nhanVien))).andExpect(redirectedUrl("/nhanvien?duplicate"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void testNewNhanVien2() throws Exception { // checkDuplicate2 != null
		NhanVien nhanVien=new NhanVien(); 			// trùng mã BHXH với cá nhân khác
		Mockito.when(caNhanRepository.findByMaBHXH(any())).thenReturn(new CaNhan());
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nhanVien))).andExpect(redirectedUrl("/nhanvien?duplicate"));
	}
	
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test_luong_NV_nho_hon_luong_toi_thieu_Vung_1() throws Exception {
		NhanVien nhanVien=new NhanVien(3000000L,1000000L);  // luong nhan vien < lương tối thiếu (4729400)
		DoanhNghiep doanhNghiep=new DoanhNghiep("CTY A","423425235","434234","26 Hàng Bài","11/2 Lò Đúc","Cty TNHH","01365189","ctyA@gmai.com","5435345","Tp.Hà Nội","03 Tháng");
		User user=new User();
		Mockito.when(nhanVienRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(caNhanRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(doanhNghiep);
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nhanVien))).andExpect(redirectedUrl("/nhanvien?error=01"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test_luong_NV_nho_hon_luong_toi_thieu_Vung_2() throws Exception {
		NhanVien nhanVien=new NhanVien(3000000L,1000000L);  // luong nhan vien < lương tối thiếu (4194100)
		DoanhNghiep doanhNghiep=new DoanhNghiep("CTY A","423425235","434234","26 Hàng Bài","11/2 Lò Đúc","Cty TNHH","01365189","ctyA@gmai.com","5435345","Hưng Yên","03 Tháng");
		User user=new User();
		Mockito.when(nhanVienRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(caNhanRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(doanhNghiep);
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nhanVien))).andExpect(redirectedUrl("/nhanvien?error=02"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test_luong_NV_nho_hon_luong_toi_thieu_Vung_3() throws Exception {
		NhanVien nhanVien=new NhanVien(3000000L,1000000L);  // luong nhan vien < lương tối thiếu (3670100)
		DoanhNghiep doanhNghiep=new DoanhNghiep("CTY A","423425235","434234","26 Hàng Bài","11/2 Lò Đúc","Cty TNHH","01365189","ctyA@gmai.com","5435345","Hải Dương","03 Tháng");
		User user=new User();
		Mockito.when(nhanVienRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(caNhanRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(doanhNghiep);
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nhanVien))).andExpect(redirectedUrl("/nhanvien?error=03"));
	}
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test_luong_NV_nho_hon_luong_toi_thieu_Vung_4() throws Exception {
		NhanVien nhanVien=new NhanVien(3000000L,1000000L);  // luong nhan vien < lương tối thiếu (3284900)
		DoanhNghiep doanhNghiep=new DoanhNghiep("CTY A","423425235","434234","26 Hàng Bài","11/2 Lò Đúc","Cty TNHH","01365189","ctyA@gmai.com","5435345","Bắc Giang","03 Tháng");
		User user=new User();
		Mockito.when(nhanVienRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(caNhanRepository.findByMaBHXH(any())).thenReturn(null);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(doanhNghiep);
		ObjectMapper objectMapper=new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(nhanVien))).andExpect(redirectedUrl("/nhanvien?error=04"));
	}
	 @Before
	    public void init() throws Exception {
	        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	    }
	
	@WithMockUser(username = "user",password = "123456",roles = "USER")
	@Test
	public void Test_luong_NV_lon_hon_luong_toi_thieu_Vung_1() throws Exception {
		NhanVien nhanVien=new NhanVien(50000000l,1000000l);  // luong nhan vien > lương tối thiếu (4729400)
		DoanhNghiep doanhNghiep=new DoanhNghiep("CTY A","423425235","434234","26 Hàng Bài","11/2 Lò Đúc","Cty TNHH","01365189","ctyA@gmai.com","5435345","Tp.Hải Phòng","03 Tháng");
		User user=new User();
		Mockito.when(nhanVienRepository.findByMaBHXH(anyString())).thenReturn(null);
		Mockito.when(caNhanRepository.findByMaBHXH(anyString())).thenReturn(null);
		Mockito.when(userService.getUser(anyString())).thenReturn(user);
		Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(doanhNghiep); 
		
		//ObjectMapper objectMapper=new ObjectMapper();
		HttpServletRequest request=mock(HttpServletRequest.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/nhanvien").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(objectMapper.writeValueAsString(new NhanVien(5000000L,1000000L)))).andExpect(redirectedUrl("/doanhnghiep?success"));
	}
	
//	@Test
//	public void testMucLuongToiThieu() {
//		long result =DoanhNghiep.getMucLuongToiThieu("Tp.Hải Phòng");
//		assertEquals(4729400L, result);
//	}
}
