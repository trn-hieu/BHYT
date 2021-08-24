package net.springboot.javaguides.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.HoGiaDinh;
import net.springboot.javaguides.entity.NhanVien;

import net.springboot.javaguides.entity.ThanhToan;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.entity.VnPay;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.repository.ThanhToanRepository;
import net.springboot.javaguides.service.UserService;

@Controller
@RequestMapping("thanhtoan")
public class ThanhToanController {
	@Autowired
	private NhanVienRepository nhanVienRepository;
	@Autowired
	private DoanhNghiepRepository doanhNghiepRepository;
	@Autowired 
	private UserService userService;
	@Autowired
	private CaNhanRepository caNhanRepository;
	@Autowired
	private ThanhToanRepository thanhToanRepository;
	
	@GetMapping("method")
	public String selectMethod() {
		return "thanh-toan"; 
	}
	
	@GetMapping("/list")
	public String LichSuThanhToan(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        Long user_id=u.getId();
		List<ThanhToan> list=thanhToanRepository.findbyUserId(user_id);
		model.addAttribute("thanhtoan", list);
		return "lich-su-thanh-toan";
	}
	
	@GetMapping("/doanhnghiep/vnpay")
	public String formVnPayDN(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        Long user_id=u.getId();
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(user_id);
        List<NhanVien> list= nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
        if(list.isEmpty())
        	return"redirect:/doanhnghiep?error"; //list chua co nhan vien
        else 
        {	
		Long total =DoanhNghiep.CalCulate(list);
		if(doanhNghiep.getPhuongThucDong().equals("03 Tháng"))
			total=total*3;
		else if(doanhNghiep.getPhuongThucDong().equals("06 Tháng"))
			total=total*6;
		VnPay vnPay=new VnPay();
		vnPay.setVnp_Amount(total);
		vnPay.setVnp_OrderInfo("Thanh toan BHYT cho doanh nghiep");
		model.addAttribute("vnPay", vnPay);
		return"VnPay";
		}
	}
	
//	@GetMapping("/doanhnghiep/paypal")
//	public String formPayPalDN(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        User u = userService.getUser(userDetail.getUsername());
//        Long user_id=u.getId();
//        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(user_id);
//		List<NhanVien> list= nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
//		if(list.isEmpty())
//        	return"redirect:/doanhnghiep?error"; //list chua co nhan vien
//        else 
//        {
//		Long total =DoanhNghiep.CalCulate(list);
//		if(doanhNghiep.getPhuongThucDong().equals("03 Tháng"))
//			total=total*3;
//		else if(doanhNghiep.getPhuongThucDong().equals("06 Tháng"))
//			total=total*6;
//		PayPal payPal=new PayPal();
//		payPal.setPrice(total);
//		payPal.setDescription("Thanh toán BHYT cho doanh nghiệp");
//		model.addAttribute("payPal",payPal);
//		return "PayPal";
//		}
//	}
	
	@GetMapping("/hogiadinh/vnpay")
	public String formVnPayHDG(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        Long user_id=u.getId();
        List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(user_id);
        Long total=HoGiaDinh.Calculate(list);
        VnPay vnPay=new VnPay();
		vnPay.setVnp_Amount(total);
		vnPay.setVnp_OrderInfo("Thanh toan BHYT theo ho gia dinh");
		model.addAttribute("vnPay", vnPay);
        return "VnPay";
	}
//	@GetMapping("/hogiadinh/paypal")
//	public String formPaypalHGD(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        User u = userService.getUser(userDetail.getUsername());
//        Long user_id=u.getId();
//        List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(user_id);
//        Long total=HoGiaDinh.Calculate(list);
//        PayPal payPal=new PayPal();
//        payPal.setPrice(total);
//        payPal.setDescription("Thanh toán BHYT theo hộ gia đình");
//        model.addAttribute("payPal",payPal);
//		return "PayPal";
//	}
}
