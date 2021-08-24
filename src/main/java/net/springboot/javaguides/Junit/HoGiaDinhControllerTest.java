package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
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

import net.springboot.javaguides.controller.CaNhanController;
import net.springboot.javaguides.controller.HoGiaDinhController;
import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.HoGiaDinh;
import net.springboot.javaguides.entity.NhanVien;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.HoGiaDinhRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class HoGiaDinhControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private HoGiaDinhController hoGiaDinhController;
	@MockBean
	Model model;
	
	// test cho tài khoản chưa khai báo thông tin
	@WithMockUser(username = "test")
	@Test
	void testDSDangKi_ChuaKhaiTT() {
		UserService userService = this.context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		String s = hoGiaDinhController.dsDangKi(model);
		assertEquals("redirect:/canhan/form", s);
	}
	
	//Test tài khoản đã khai báo thông tin nhưng chưa có thêm vào Hộ gia đình
	@WithMockUser(username = "test")
	@Test
	void testDSDangKi_DaKhaiTT_ChuaCoHGD() {
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		
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
		caNhanController.addCaNhan(c);
		
		String s = hoGiaDinhController.dsDangKi(model);
		assertEquals("redirect:/hogiadinh/list", s);
	}
	
	// Test cho tài khoản đã khai báo thông tin và đã thêm vào hộ gia đình
	@WithMockUser(username = "test")
	@Test
	void testDSDangKi_DaKhaiTT_CoHGD() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = this.context.getBean(HoGiaDinhRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		CaNhan c1 = new CaNhan();
		c1.setName("test1");
		c1.setNgaySinh(new Date());
		c1.setGioiTinh("Nam");
		c1.setSoCMND("12345678901");
		c1.setDiaChi("Ha Noi");
		c1.setEmail("test@gmail.com");
		c1.setSoDT("0123456789");
		c1.setThanhPho("Ha Noi");
		c1.setQuan("Ha Noi");
		c1.setMaBHXH("1234567890");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		caNhanController.addCaNhan(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
		
		CaNhan c2 = new CaNhan();
		c2.setName("test2");
		c2.setNgaySinh(new Date());
		c2.setGioiTinh("Nam");
		c2.setSoCMND("4521578945");
		c2.setDiaChi("Ha Noi");
		c2.setEmail("test2@gmail.com");
		c2.setSoDT("0123456789");
		c2.setThanhPho("Ha Noi");
		c2.setQuan("Ha Noi");
		c2.setMaBHXH("1234567892");
		c2.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		hoGiaDinhController.addThanhvien(c2);
		
		String s = hoGiaDinhController.dsDangKi(model);
		User u = userService.getUser("test");
		List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(u.getId());
		assertEquals("list-ca-nhan", s);
		assertEquals(2, list.size());
	}
	
	//Thêm thành viên
	@WithMockUser(username = "test")
	@Test
	void testAddThanhvien_ThanhCong() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = this.context.getBean(HoGiaDinhRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		NhanVienRepository nhanVienRepository = this.context.getBean(NhanVienRepository.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		CaNhan c1 = new CaNhan();
		c1.setName("test1");
		c1.setNgaySinh(new Date());
		c1.setGioiTinh("Nam");
		c1.setSoCMND("12345678901");
		c1.setDiaChi("Ha Noi");
		c1.setEmail("test@gmail.com");
		c1.setSoDT("0123456789");
		c1.setThanhPho("Ha Noi");
		c1.setQuan("Ha Noi");
		c1.setMaBHXH("1234567890");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		caNhanController.addCaNhan(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
		
		CaNhan c2 = new CaNhan();
		c2.setName("test2");
		c2.setNgaySinh(new Date());
		c2.setGioiTinh("Nam");
		c2.setSoCMND("4521578945");
		c2.setDiaChi("Ha Noi");
		c2.setEmail("test2@gmail.com");
		c2.setSoDT("0123456789");
		c2.setThanhPho("Ha Noi");
		c2.setQuan("Ha Noi");
		c2.setMaBHXH("1234567892");
		c2.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		
		String s = hoGiaDinhController.addThanhvien(c2);
		User u = userService.getUser("test");
		List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(u.getId());
		assertEquals("redirect:/hogiadinh/list?success", s);
		assertEquals(2, list.size());
	}
	
	//thêm thành viên có mã BHXH trùng với một thành viên đã khai báo trong hộ gia đình
	@WithMockUser(username = "test")
	@Test
	void testAddThanhvien_ThemThanhVienCoBHXHDaCoTrongHGD() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = this.context.getBean(HoGiaDinhRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		NhanVienRepository nhanVienRepository = this.context.getBean(NhanVienRepository.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		CaNhan c1 = new CaNhan();
		c1.setName("test1");
		c1.setNgaySinh(new Date());
		c1.setGioiTinh("Nam");
		c1.setSoCMND("12345678901");
		c1.setDiaChi("Ha Noi");
		c1.setEmail("test@gmail.com");
		c1.setSoDT("0123456789");
		c1.setThanhPho("Ha Noi");
		c1.setQuan("Ha Noi");
		c1.setMaBHXH("1234567890");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		caNhanController.addCaNhan(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
		
		CaNhan c2 = new CaNhan();
		c2.setName("test2");
		c2.setNgaySinh(new Date());
		c2.setGioiTinh("Nam");
		c2.setSoCMND("4521578945");
		c2.setDiaChi("Ha Noi");
		c2.setEmail("test2@gmail.com");
		c2.setSoDT("0123456789");
		c2.setThanhPho("Ha Noi");
		c2.setQuan("Ha Noi");
		c2.setMaBHXH("1234567890");
		c2.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		
		String s = hoGiaDinhController.addThanhvien(c2);
		User u = userService.getUser("test");
		List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(u.getId());
		assertEquals("redirect:/hogiadinh/form?duplicate", s);
		assertEquals(1, list.size());
	}
	
	//Thêm thành viên có mã BHXH trùng với một nhân viên trong một công ty nào đó
	@WithMockUser(username = "test")
	@Test
	void testAddThanhvien_ThemThanhVienCoBHXHDaCoTrongDN() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = this.context.getBean(HoGiaDinhRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		NhanVienRepository nhanVienRepository = this.context.getBean(NhanVienRepository.class);
		DoanhNghiepRepository doanhNghiepRepository = context.getBean(DoanhNghiepRepository.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		User u = userService.getUser("test");
		
		DoanhNghiep d = new DoanhNghiep();
		d.setTen("DNABC");
		d.setMaDonVi("9999");
		d.setMaThue("123456");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setDiaChiLienHe("Ha Noi");
		d.setSoDT("0123456789");
		d.setNoiCap("Ha Noi");
		d.setPhuongThucDong("03 Thang");
		
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(1000000);
        n.setPhuCap(100000);
        n.setDoanhNghiep(d);
        nhanVienRepository.save(n);
        
		CaNhan c1 = new CaNhan();
		c1.setName("test1");
		c1.setNgaySinh(new Date());
		c1.setGioiTinh("Nam");
		c1.setSoCMND("12345678901");
		c1.setDiaChi("Ha Noi");
		c1.setEmail("test@gmail.com");
		c1.setSoDT("0123456789");
		c1.setThanhPho("Ha Noi");
		c1.setQuan("Ha Noi");
		c1.setMaBHXH("1234567890");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		caNhanController.addCaNhan(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
		
		CaNhan c2 = new CaNhan();
		c2.setName("test2");
		c2.setNgaySinh(new Date());
		c2.setGioiTinh("Nam");
		c2.setSoCMND("4521578945");
		c2.setDiaChi("Ha Noi");
		c2.setEmail("test2@gmail.com");
		c2.setSoDT("0123456789");
		c2.setThanhPho("Ha Noi");
		c2.setQuan("Ha Noi");
		c2.setMaBHXH("1234567890");
		c2.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		
		String s = hoGiaDinhController.addThanhvien(c2);
		
		List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(u.getId());
		assertEquals("redirect:/hogiadinh/form?duplicate", s);
		assertEquals(1, list.size());
	}

	//Xóa hộ gia đình chỉ có 1 thành viên
	@WithMockUser(username = "test")
	@Test
	void testDeleteHoGiaDinh_ChiCo1ThanhVien() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = this.context.getBean(HoGiaDinhRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		CaNhan c1 = new CaNhan();
		c1.setName("test1");
		c1.setNgaySinh(new Date());
		c1.setGioiTinh("Nam");
		c1.setSoCMND("12345678901");
		c1.setDiaChi("Ha Noi");
		c1.setEmail("test@gmail.com");
		c1.setSoDT("0123456789");
		c1.setThanhPho("Ha Noi");
		c1.setQuan("Ha Noi");
		c1.setMaBHXH("1234567890");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		caNhanController.addCaNhan(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
		User u = userService.getUser("test");
		CaNhan caNhan = caNhanRepository.findbyUserId(u.getId());
		String s = hoGiaDinhController.deleteHoGiaDinh(caNhan.getId(), model);
		assertEquals("redirect:/hogiadinh/list?error", s);
	}
	
	//xóa hộ gia đình có nhiều hơn 2 thành viên
	@WithMockUser(username = "test")
	@Test
	void testDeleteHoGiaDinh_CoNhieuHon1ThanhVien() {
		CaNhanRepository caNhanRepository = this.context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = this.context.getBean(HoGiaDinhRepository.class);
		UserService userService = this.context.getBean(UserService.class);
		CaNhanController caNhanController = this.context.getBean(CaNhanController.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		CaNhan c1 = new CaNhan();
		c1.setName("test1");
		c1.setNgaySinh(new Date());
		c1.setGioiTinh("Nam");
		c1.setSoCMND("12345678901");
		c1.setDiaChi("Ha Noi");
		c1.setEmail("test@gmail.com");
		c1.setSoDT("0123456789");
		c1.setThanhPho("Ha Noi");
		c1.setMaBHXH("1234567890");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		caNhanController.addCaNhan(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
		
		CaNhan c2 = new CaNhan();
		c2.setName("test2");
		c2.setNgaySinh(new Date());
		c2.setGioiTinh("Nam");
		c2.setSoCMND("4521578945");
		c2.setDiaChi("Ha Noi");
		c2.setEmail("test2@gmail.com");
		c2.setSoDT("0123456789");
		c2.setThanhPho("Ha Noi");
		c2.setQuan("Ha Noi");
		c2.setMaBHXH("1234567892");
		c2.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		hoGiaDinhController.addThanhvien(c2);
		
		User u = userService.getUser("test");
		List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(u.getId());
		CaNhan cv = list.get(list.size() - 1);
		String s = hoGiaDinhController.deleteHoGiaDinh(cv.getId(), model);
		List<CaNhan> lc =caNhanRepository.findAllHGDbyUserId(u.getId());
		assertEquals("redirect:/hogiadinh/list?delete", s);
		assertEquals(1, lc.size());
	}
	
	@Test
	void testShowForm() {
		CaNhan c = new CaNhan();
		assertEquals("add-thanhvien-hogiadinh", hoGiaDinhController.showForm(c));
	}
}
