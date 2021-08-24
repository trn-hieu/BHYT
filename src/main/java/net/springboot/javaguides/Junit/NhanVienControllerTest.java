package net.springboot.javaguides.Junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import net.springboot.javaguides.controller.NhanVienController;
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
class NhanVienControllerTest {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private NhanVienController nhanVienController;
	@MockBean
	Model model;
	@MockBean
	HttpServletRequest request;
//	@MockBean
//	private DoanhNghiepRepository doanhNghiepRepository;
	
	//test để hiển thị form thêm nhân viên
	@Test
	void testShowForm() {
		NhanVien n = new NhanVien();
		HttpSession session=mock(HttpSession.class); 
		Mockito.when(request.getSession()).thenReturn(session);
		request.getSession().setAttribute("nhanVien", n);
		String s = nhanVienController.showForm(request, model);
		assertEquals("add-nhan-vien", s);
	}
	
	//test thêm mới một nhân viên
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_ThanhCong() {
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
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(5000000);
        n.setPhuCap(100000);
     //   Mockito.when(doanhNghiepRepository.findbyUserId(any())).thenReturn(d);
        String s = nhanVienController.newNhanVien(n, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/doanhnghiep?success", s);
        assertEquals(1, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}
	
	//test thêm mới một nhân viên có mức lương nhỏ hơn mức lương tối thiểu vùng 1 tại nơi công ty đăng ký
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_NhanVienCoLuongNhoHonLuongToiThieuVung1() {
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
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(4500000);
        n.setPhuCap(100000);
        HttpSession session=mock(HttpSession.class); 
		Mockito.when(request.getSession()).thenReturn(session);
        String s = nhanVienController.newNhanVien(n, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/nhanvien?error=01", s);
        assertEquals(0, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}
	
	//test thêm mới một nhân viên có mức lương nhỏ hơn mức lương tối thiểu vùng 2 tại nơi công ty đăng ký
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_NhanVienCoLuongNhoHonLuongToiThieuVung2() {
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
		d.setNoiCap("Hưng Yên");
		d.setPhuongThucDong("03 Thang");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(4000000);
        n.setPhuCap(100000);
        HttpSession session=mock(HttpSession.class); 
		Mockito.when(request.getSession()).thenReturn(session);
        String s = nhanVienController.newNhanVien(n, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/nhanvien?error=02", s);
        assertEquals(0, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}
	
	//test thêm mới một nhân viên có mức lương nhỏ hơn mức lương tối thiểu vùng 3 tại nơi công ty đăng ký
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_NhanVienCoLuongNhoHonLuongToiThieuVung3() {
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
		d.setNoiCap("Hải Dương");
		d.setPhuongThucDong("03 Thang");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(3500000);
        n.setPhuCap(100000);
        HttpSession session=mock(HttpSession.class); 
		Mockito.when(request.getSession()).thenReturn(session);
        String s = nhanVienController.newNhanVien(n, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/nhanvien?error=03", s);
        assertEquals(0, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}
	
	//test thêm mới một nhân viên có mức lương nhỏ hơn mức lương tối thiểu vùng 4 tại nơi công ty đăng ký
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_NhanVienCoLuongNhoHonLuongToiThieuVung4() {
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
		d.setNoiCap("Bắc Giang");
		d.setPhuongThucDong("03 Thang");
		
		User u = userService.getUser(user.getEmail());
		d.setUserDN(u);
        doanhNghiepRepository.save(d);
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(3000000);
        n.setPhuCap(100000);
        HttpSession session=mock(HttpSession.class); 
		Mockito.when(request.getSession()).thenReturn(session);
        String s = nhanVienController.newNhanVien(n, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/nhanvien?error=04", s);
        assertEquals(0, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}

	//test thêm mới một nhân viên có mã BHXH trùng với một nhân viên khác
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_TrungBHXHTrongDN() {
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
		d.setNoiCap("Ha Noi");
		d.setPhuongThucDong("03 Thang");
		
		User u = userService.getUser(user.getEmail());
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
        
        NhanVien n1 = new NhanVien();
        n1.setTen("NVA");
        n1.setGioiTinh("Nam");
        n1.setNgaySinh(new Date());
        n1.setDiaChi("Ha Noi");
        n1.setMaBHXH("0123456789");
        n1.setChucVu("Nhan vien");
        n1.setMucluong(1000000);
        n1.setPhuCap(100000);

        String s = nhanVienController.newNhanVien(n1, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/nhanvien?duplicate", s);
        assertEquals(1, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}
	
	//test thêm mới một nhân viên có mã BHXH trùng với một cá nhân trong hộ gia đình đã khai báo
	@WithMockUser(username = "test2")
	@Test
	void testNewNhanVien_TrungBHXHTrongHGD() {
		CaNhanRepository caNhanRepository = context.getBean(CaNhanRepository.class);
		HoGiaDinhRepository hoGiaDinhRepository = context.getBean(HoGiaDinhRepository.class);
		NhanVienRepository nhanVienRepository = context.getBean(NhanVienRepository.class);
		DoanhNghiepRepository doanhNghiepRepository = context.getBean(DoanhNghiepRepository.class);
		UserService userService = context.getBean(UserService.class);
		
		UserRegistrationDto user = new UserRegistrationDto();
		user.setFirstName("Hieu");
		user.setLastName("Nguyen");
		user.setEmail("test2");
		user.setPassword("123456");
		userService.save(user);
		
		User u = userService.getUser(user.getEmail());
		
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
		c1.setMaBHXH("0123456789");
		c1.setNoiKCB("Phòng khám 107 Tôn Đức Thắng");
		c1.setUser(u);
		caNhanRepository.save(c1);
		
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
    	hoGiaDinhRepository.save(hoGiaDinh);
    	c1.setHoGiaDinh(hoGiaDinh);
    	caNhanRepository.save(c1);
    	
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

        String s = nhanVienController.newNhanVien(n, request);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        assertEquals("redirect:/nhanvien?duplicate", s);
        assertEquals(0, nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId()).size());
	}

	// xóa một nhân viên
	@WithMockUser(username = "test2")
	@Test
	void testDeleteNhanVien() {
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
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(5000000);
        n.setPhuCap(100000);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        n.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n);
        
        NhanVien n2 = new NhanVien();
        n2.setTen("NVB");
        n2.setGioiTinh("Nu");
        n2.setNgaySinh(new Date());
        n2.setDiaChi("Ha Noi");
        n2.setMaBHXH("1234567890");
        n2.setChucVu("Nhan vien");
        n2.setMucluong(6000000);
        n2.setPhuCap(100000);
        
        n2.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n2);
        
        List<NhanVien> list = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        NhanVien nv = list.get(list.size() - 1);
        String s = nhanVienController.deleteNhanVien(nv.getId());
        assertEquals("redirect:/doanhnghiep?delete", s);
        List<NhanVien> r = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        assertEquals(1, r.size());
	}
	
	// xóa một nhân viên không tồn tại
	@WithMockUser(username = "test2")
	@Test
	void testDeleteNhanVien_Error() {
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
        
        NhanVien n = new NhanVien();
        n.setTen("NVA");
        n.setGioiTinh("Nam");
        n.setNgaySinh(new Date());
        n.setDiaChi("Ha Noi");
        n.setMaBHXH("0123456789");
        n.setChucVu("Nhan vien");
        n.setMucluong(5000000);
        n.setPhuCap(100000);
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(u.getId());
        n.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n);
        
        NhanVien n2 = new NhanVien();
        n2.setTen("NVB");
        n2.setGioiTinh("Nu");
        n2.setNgaySinh(new Date());
        n2.setDiaChi("Ha Noi");
        n2.setMaBHXH("1234567890");
        n2.setChucVu("Nhan vien");
        n2.setMucluong(6000000);
        n2.setPhuCap(100000);
        
        n2.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(n2);
        
        List<NhanVien> list = nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        NhanVien nv = list.get(list.size()-1);
        assertThrows(NoSuchElementException.class, ()->{nhanVienController.deleteNhanVien(nv.getId()+1);});
	}
}
