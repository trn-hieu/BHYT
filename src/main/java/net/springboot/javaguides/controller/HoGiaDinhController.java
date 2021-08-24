package net.springboot.javaguides.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.HoGiaDinh;
import net.springboot.javaguides.entity.NhanVien;

import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.entity.VnPay;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.HoGiaDinhRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.service.UserService;

@Controller
@RequestMapping("hogiadinh")
public class HoGiaDinhController {
	@Autowired
	private HoGiaDinhRepository hoGiaDinhRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CaNhanRepository caNhanRepository;
	@Autowired
	private NhanVienRepository nhanVienRepository;
	
	@GetMapping("list")
	public String dsDangKi(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        CaNhan caNhan=this.caNhanRepository.findbyUserId(u.getId());
        
        if(caNhan==null)
        	 return "redirect:/canhan/form";
        else
        if(caNhan.getHoGiaDinh()==null) {
        	HoGiaDinh hoGiaDinh=new HoGiaDinh();
        	this.hoGiaDinhRepository.save(hoGiaDinh);
        	caNhan.setHoGiaDinh(hoGiaDinh);
        	caNhanRepository.save(caNhan);
        	model.addAttribute("canhan",this.hoGiaDinhRepository.findAll());
        	return"redirect:/hogiadinh/list";
        }else 
        	
        	{model.addAttribute("canhan",this.caNhanRepository.findAllHGDbyUserId(u.getId()));
    		return"list-ca-nhan";}
	}
	
	
	@GetMapping("delete/{id}")
	public String deleteHoGiaDinh(@PathVariable("id") long id, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        CaNhan caNhan=this.caNhanRepository.findbyUserId(u.getId());
        Long canhan_id=caNhan.getId();
        if(id==canhan_id) return "redirect:/hogiadinh/list?error";
        else {
		caNhanRepository.deleteHGDbyid(id);
		return "redirect:/hogiadinh/list?delete";}
		
	}
	
	@GetMapping("form")
	public String showForm(CaNhan caNhan) {
		return "add-thanhvien-hogiadinh";
	}
	
	@PostMapping("add")
	public String addThanhvien(CaNhan caNhan) {
		NhanVien checkDuplicate = nhanVienRepository.findByMaBHXH(caNhan.getMaBHXH());
        CaNhan checkDuplicate2 = caNhanRepository.findByMaBHXH(caNhan.getMaBHXH());
        if(checkDuplicate != null || checkDuplicate2 != null )
        	return "redirect:/hogiadinh/form?duplicate";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        CaNhan currentlogin=this.caNhanRepository.findbyUserId(u.getId());
        caNhan.setHoGiaDinh(currentlogin.getHoGiaDinh());
        caNhanRepository.save(caNhan);
        return "redirect:/hogiadinh/list?success";
	}
//	@GetMapping("/vnpay")
//	public String formVnPay(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        User u = userService.getUser(userDetail.getUsername());
//        Long user_id=u.getId();
//        List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(user_id);
//        HoGiaDinh hoGiaDinh=new HoGiaDinh();
//        Long total=hoGiaDinh.Calculate(list);
//        VnPay vnPay=new VnPay();
//		vnPay.setVnp_Amount(total);
//		vnPay.setVnp_OrderInfo("Thanh toan BHYT theo ho gia dinh");
//		model.addAttribute("vnPay", vnPay);
//        return "VnPay";
//	}
//	
//	@GetMapping("/paypal")
//	public String formPaypal(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//        User u = userService.getUser(userDetail.getUsername());
//        Long user_id=u.getId();
//        List<CaNhan> list=caNhanRepository.findAllHGDbyUserId(user_id);
//        HoGiaDinh hoGiaDinh=new HoGiaDinh();
//        Long total=hoGiaDinh.Calculate(list);
//        PayPal payPal=new PayPal();
//        payPal.setPrice(total);
//        payPal.setDescription("Thanh toán BHYT theo hộ gia đình");
//        model.addAttribute("payPal",payPal);
//		return "PayPal";
//	}
}
