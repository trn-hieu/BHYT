package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

import net.springboot.javaguides.controller.DoanhNghiepController;
import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.entity.VnPay;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class DoanhNghiepControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private DoanhNghiepController doanhNghiepController;
	@MockBean
	Model model;
	
	//test hiển thị form cho doanh nghiệp chưa đăng ký
	@WithMockUser(username = "test")
	@Test
	void testShowForm_ChuaDangKyDoanhNghiep() {
		UserService userService = this.context.getBean(UserService.class);
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep doanhNghiep = new DoanhNghiep();
		String s = doanhNghiepController.showForm(doanhNghiep, model);
		assertEquals("dki-doanhNghiep-form", s);
	}
	
	////test hiển thị form cho doanh nghiệp đã đăng ký
	@WithMockUser(username = "test")
	@Test
	void testShowForm_DaDangKyDoanhNghiep() {
		UserService userService = this.context.getBean(UserService.class);
		DoanhNghiepRepository doanhNghiepRepository = this.context.getBean(DoanhNghiepRepository.class);
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep doanhNghiep = new DoanhNghiep();
		User u = userService.getUser("test");
		doanhNghiep.setUserDN(u);
        doanhNghiepRepository.save(doanhNghiep);
		String s = doanhNghiepController.showForm(doanhNghiep, model);
		assertEquals("list-nhan-vien", s);
	}
	
	//Đăng ký mới cho một doanh nghiệp
	@WithMockUser(username = "test")
	@Test
	void testNewDoanhNghiep() {
		UserService userService = this.context.getBean(UserService.class);
		DoanhNghiepRepository doanhNghiepRepository = this.context.getBean(DoanhNghiepRepository.class);
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep d = new DoanhNghiep();
		d.setTen("TestABC");
		d.setMaDonVi("9999");
		d.setMaThue("123456");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setDiaChiLienHe("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setSoDT("0123456789");
		d.setNoiCap("Ha Noi");
		d.setPhuongThucDong("03 Thang");
		String s = doanhNghiepController.newDoanhNghiep(d, model);
		User u = userService.getUser("test");
		DoanhNghiep dv = doanhNghiepRepository.findbyUserId(u.getId());
		assertEquals(d, dv);
		assertEquals("list-nhan-vien", s);
	}
	
	//tự thêm doanh nghiệp từ bên ngoài
	//Đăng ký mới cho một doanh nghiệp nhưng có mã thuế trùng với 1 doanh nghiệp đã đăng ký
	@WithMockUser(username = "test1")
	@Test
	void testNewDoanhNghiep_TrungMaThue() {
		UserService userService = this.context.getBean(UserService.class);
		DoanhNghiepRepository doanhNghiepRepository = this.context.getBean(DoanhNghiepRepository.class);
		
		UserRegistrationDto user2 = new UserRegistrationDto();
		user2.setFirstName("Hieu");
		user2.setLastName("Nguyen");
		user2.setEmail("test1");
		user2.setPassword("123456");
		userService.save(user2);
		
		DoanhNghiep d = new DoanhNghiep();
		d.setTen("TestABC");
		d.setMaDonVi("9999");
		d.setMaThue("234567");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setDiaChiLienHe("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setSoDT("0123456789");
		d.setNoiCap("Ha Noi");
		d.setPhuongThucDong("03 Thang");
		doanhNghiepController.newDoanhNghiep(d, model);
		
		List<DoanhNghiep> list = doanhNghiepRepository.findbyMaThue("234567");
		assertEquals(1, list.size());
	}
	
	@Test
	void testShowForm() {
		VnPay v = new VnPay();
		String s = doanhNghiepController.form(v);
		assertEquals("VNpay", s);
	}

}
