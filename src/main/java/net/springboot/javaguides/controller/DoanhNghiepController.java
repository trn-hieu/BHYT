package net.springboot.javaguides.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.NhanVien;

import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.entity.VnPay;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.service.UserService;

@Controller
@RequestMapping("doanhnghiep")
public class DoanhNghiepController { 
	@Autowired
	private DoanhNghiepRepository doanhNghiepRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	@GetMapping
	public String showForm(DoanhNghiep doanhNghiep,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        Long user_id=u.getId();
        if(doanhNghiepRepository.findbyUserId(user_id)==null)
        	return "dki-doanhNghiep-form";
        else {
        	DoanhNghiep doanhNghiep2=doanhNghiepRepository.findbyUserId(user_id);
        	List<NhanVien> list=nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep2.getId());
        	model.addAttribute("doanhnghiep", doanhNghiep2);
        	model.addAttribute("nhanvien", list);
        	return "list-nhan-vien";
        }
	}
	
	@PostMapping
	public String newDoanhNghiep(DoanhNghiep doanhNghiep,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        doanhNghiep.setUserDN(u);
        doanhNghiepRepository.save(doanhNghiep);
        model.addAttribute("doanhnghiep", doanhNghiep);
        return "redirect:/doanhnghiep";
	}
	@GetMapping("/form")
	public String form(VnPay vnPay) {
		return"VNpay";
	}
	
//	@GetMapping("/vnpay")
//	public String form(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        User u = userService.getUser(userDetail.getUsername());
//        Long user_id=u.getId();
//        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(user_id);
//		List<NhanVien> list= nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
//		Long total =DoanhNghiep.CalCulate(list);
//		VnPay vnPay=new VnPay();
//		vnPay.setVnp_Amount(total);
//		vnPay.setVnp_OrderInfo("Thanh toan BHYT cho doanh nghiep");
//		model.addAttribute("vnPay", vnPay);
//		return"VnPay";//VNpay
//	}
//	@GetMapping("/paypal")
//	public String formPayPal(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        User u = userService.getUser(userDetail.getUsername());
//        Long user_id=u.getId();
//        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(user_id);
//		List<NhanVien> list= nhanVienRepository.findAllByDoanhNghiepId(doanhNghiep.getId());
//		Long total =DoanhNghiep.CalCulate(list);
//		PayPal payPal=new PayPal();
//		payPal.setPrice(total);
//		payPal.setDescription("Thanh toán BHYT cho doanh nghiệp");
//		model.addAttribute("payPal",payPal);
//		return "PayPal";
//	}
}
