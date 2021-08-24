package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import net.springboot.javaguides.controller.CaNhanController;
import net.springboot.javaguides.controller.ThanhToanController;
import net.springboot.javaguides.controller.dto.UserRegistrationDto;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.HoGiaDinh;
import net.springboot.javaguides.entity.NhanVien;
import net.springboot.javaguides.entity.ThanhToan;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.HoGiaDinhRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.repository.ThanhToanRepository;
import net.springboot.javaguides.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ThanhToanControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private ThanhToanController thanhToanController;
	@MockBean
	Model model;
//	@MockBean
//	private DoanhNghiepRepository doanhNghiepRepository;
	@Test
	void testSelectMethod() {
		assertEquals("thanh-toan", thanhToanController.selectMethod());
	}
	
	// Test thanh toán tiền bảo hiểm 1 tháng cho doanh nghiệp
	@WithMockUser(username = "test2")
	@Test
	void testFormVnPayDN_ThanhToan1Thang() {
		NhanVienRepository nhanVienRepository = context.getBean(NhanVienRepository.class);
		DoanhNghiepRepository doanhNghiepRepository = context.getBean(DoanhNghiepRepository.class);
		UserService userService = context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep d = new DoanhNghiep();
		d.setTen("DNABC");
		d.setMaDonVi("9999");
		d.setMaThue("123456");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setDiaChiLienHe("Ha Noi");
		d.setSoDT("0123456789");
		d.setNoiCap("Tp.Hà Nội");
		d.setPhuongThucDong("01 Tháng");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(5000000);
        n.setPhuCap(100000);
        n.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n);
        
        NhanVien n2 = new NhanVien();
        n2.setTen("NVB");
        n2.setGioiTinh("Nu");
        n2.setNgaySinh(new Date());
        n2.setDiaChi("Ha Noi");
        n2.setMaBHXH("1234567890");
        n2.setChucVu("Nhan vien");
        n2.setMucluong(5000000);
        n2.setPhuCap(100000);
        
        n2.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n2);
        
        List<NhanVien> list = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        assertEquals(2, list.size());
        
        long total = DoanhNghiep.CalCulate(list);
        assertEquals(459000, total);
        
        String s = thanhToanController.formVnPayDN(model);
        assertEquals("VnPay", s);
	}
	
	// Test thanh toán tiền bảo hiểm 3 tháng cho doanh nghiệp
	@WithMockUser(username = "test2")
	@Test
	void testFormVnPayDN_ThanhToan3Thang() {
		NhanVienRepository nhanVienRepository = context.getBean(NhanVienRepository.class);
		DoanhNghiepRepository doanhNghiepRepository = context.getBean(DoanhNghiepRepository.class);
		UserService userService = context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep d = new DoanhNghiep();
		
		d.setTen("DNABC");
		d.setMaDonVi("9999");
		d.setMaThue("123456");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setDiaChiLienHe("Ha Noi");
		d.setSoDT("0123456789");
		d.setNoiCap("Tp.Hà Nội");
		d.setPhuongThucDong("03 Tháng");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
      
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(5000000);
        n.setPhuCap(100000);
        n.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n);
        
        NhanVien n2 = new NhanVien();
        n2.setTen("NVB");
        n2.setGioiTinh("Nu");
        n2.setNgaySinh(new Date());
        n2.setDiaChi("Ha Noi");
        n2.setMaBHXH("1234567890");
        n2.setChucVu("Nhan vien");
        n2.setMucluong(5000000);
        n2.setPhuCap(100000);
        
        n2.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n2);
        
        List<NhanVien> list = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        assertEquals(2, list.size());
        
        long total = DoanhNghiep.CalCulate(list);
        assertEquals(459000,total );
       // Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(d);
        String s = thanhToanController.formVnPayDN(model);
        assertEquals("VnPay", s);
	}
	
	// Test thanh toán tiền bảo hiểm 6 tháng cho doanh nghiệp
	@WithMockUser(username = "test2")
	@Test
	void testFormVnPayDN_ThanhToan6Thang() {
		NhanVienRepository nhanVienRepository = context.getBean(NhanVienRepository.class);
		DoanhNghiepRepository doanhNghiepRepository = context.getBean(DoanhNghiepRepository.class);
		UserService userService = context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep d = new DoanhNghiep();
		d.setTen("DNABC");
		d.setMaDonVi("9999");
		d.setMaThue("123456");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setDiaChiLienHe("Ha Noi");
		d.setSoDT("0123456789");
		d.setNoiCap("Tp.Hà Nội");
		d.setPhuongThucDong("06 Tháng");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(5000000);
        n.setPhuCap(100000);
        n.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n);
        
        NhanVien n2 = new NhanVien();
        n2.setTen("NVB");
        n2.setGioiTinh("Nu");
        n2.setNgaySinh(new Date());
        n2.setDiaChi("Ha Noi");
        n2.setMaBHXH("1234567890");
        n2.setChucVu("Nhan vien");
        n2.setMucluong(5000000);
        n2.setPhuCap(100000);
        
        n2.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n2);
        
        List<NhanVien> list = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        assertEquals(2, list.size());
        
        long total = DoanhNghiep.CalCulate(list);
        assertEquals(459000,total);
        String s = thanhToanController.formVnPayDN(model);
        assertEquals("VnPay", s);
	}
	
	// Test thanh toán tiền bảo hiểm cho doanh nghiệp nhưng doanh nghiệp không có nhân viên
	@WithMockUser(username = "test2")
	@Test
	void testFormVnPayDN_ChuaCoNhanVien() {
		NhanVienRepository nhanVienRepository = context.getBean(NhanVienRepository.class);
		DoanhNghiepRepository doanhNghiepRepository = context.getBean(DoanhNghiepRepository.class);
		UserService userService = context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		DoanhNghiep d = new DoanhNghiep();
		d.setTen("DNABC");
		d.setMaDonVi("9999");
		d.setMaThue("123456");
		d.setDiaChiKinhDoanh("Ha Noi");
		d.setLoaiHinhKinhDoanh("Công ty TNHH một thành viên");
		d.setDiaChiLienHe("Ha Noi");
		d.setSoDT("0123456789");
		d.setNoiCap("Tp.Hà Nội");
		d.setPhuongThucDong("03 Thang");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        List<NhanVien> list = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        assertEquals(0, list.size());
        
        long total = DoanhNghiep.CalCulate(list);
        assertEquals(0, total);
        
        String s = thanhToanController.formVnPayDN(model);
        assertEquals("redirect:/doanhnghiep?error", s);
	}
	
	// Test thanh toán tiền bảo hiểm cho hộ gia đình
	@WithMockUser(username = "test2")
	@Test
	void testFormVnPayHDG() {
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
		c2.setMaBHXH("1234567890");
		c2.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		c2.setHoGiaDinh(hoGiaDinh);
		caNhanRepository.save(c2);
		
		User u = userService.getUser("test2");
		List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(u.getId());
		assertEquals(2, list.size());
		
		long total = HoGiaDinh.Calculate(list);
		
		assertEquals(1367820, total);
		
		String s = thanhToanController.formVnPayHDG(model);
		assertEquals("VnPay", s);
	}

	// Test xem lịch sử thanh toán tiền
	@WithMockUser(username = "test2")
	@Test
	void testLichSuThanhToan() {
		UserService userService = this.context.getBean(UserService.class);
		ThanhToanRepository thanhToanRepository = context.getBean(ThanhToanRepository.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		User u = userService.getUser(user.getEmail());
		List<ThanhToan> listTT1 =thanhToanRepository.findbyUserId(u.getId());
		assertEquals(0, listTT1.size());
		
		String vnp_OrderInfo = "Thanh toan BHYT theo ho gia dinh";
		Integer vnp_Amount = 136782000;
		String vnp_PayDate = "20210518165050";
		String vnp_TransactionNo = "13512148";
		String vnp_TxnRef = "12345678";
		
		List<User> list=new ArrayList<>();
        list.add(u);
        
		ThanhToan thanhToan=new ThanhToan();
		thanhToan.setUserTT(list);
		thanhToan.setSoTien(vnp_Amount/100);
		thanhToan.setMaGD(vnp_TransactionNo);
		thanhToan.setSoHoaDon(vnp_TxnRef);
		
		String date=vnp_PayDate.substring(6, 8)+"/"+vnp_PayDate.substring(4,6)
		+"/"+vnp_PayDate.substring(0, 4)+" "+vnp_PayDate.substring(8, 10)+":"
		+vnp_PayDate.substring(10, 12)+":"+vnp_PayDate.substring(12, 14);
		vnp_PayDate=date;
		
		thanhToan.setThoiGian(vnp_PayDate);
		thanhToan.setNoiDung(vnp_OrderInfo);
		thanhToan.setPhuongThuc("VnPay");
		thanhToanRepository.save(thanhToan);
		
		List<ThanhToan> listTT2 =thanhToanRepository.findbyUserId(u.getId());
		assertEquals(1, listTT2.size());
		
		String s = thanhToanController.LichSuThanhToan(model);
		assertEquals("lich-su-thanh-toan", s);
	}
}
