package net.springboot.javaguides.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.NhanVien;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.repository.CaNhanRepository;
import net.springboot.javaguides.repository.DoanhNghiepRepository;
import net.springboot.javaguides.repository.NhanVienRepository;
import net.springboot.javaguides.service.UserService;

@Controller
@RequestMapping("nhanvien")
public class NhanVienController {
	@Autowired
	private NhanVienRepository nhanVienRepository;
	@Autowired
	private DoanhNghiepRepository doanhNghiepRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CaNhanRepository caNhanRepository;
	
	@GetMapping
	public String showForm(HttpServletRequest request,Model model) {
		NhanVien nhanVien=(NhanVien) request.getSession().getAttribute("nhanVien");
		//NhanVien nhanVien=nhanVienRepository.findByMaBHXH("42342342");
		if(nhanVien== null) 
			nhanVien=new NhanVien();
		model.addAttribute("nhanVien", nhanVien);
		return "add-nhan-vien";
	}
	@PostMapping
	public String newNhanVien(NhanVien nhanVien,HttpServletRequest request) {
		NhanVien checkDuplicate = nhanVienRepository.findByMaBHXH(nhanVien.getMaBHXH());
        CaNhan checkDuplicate2 = caNhanRepository.findByMaBHXH(nhanVien.getMaBHXH());
        if(checkDuplicate != null || checkDuplicate2 != null ) // Kiem tra ma BHXH co bi trung ko
        	return "redirect:/nhanvien?duplicate";
        
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User u = userService.getUser(userDetail.getUsername());
        Long user_id=u.getId();
        
        DoanhNghiep doanhNghiep=doanhNghiepRepository.findbyUserId(user_id);
        long mucLuongToiThieu=DoanhNghiep.getMucLuongToiThieu(doanhNghiep.getNoiCap()); // ktra lương nhân viên có lớn hơn mức lương tối thiểu k
        if(nhanVien.getMucluong() < mucLuongToiThieu) {
        	request.getSession().setAttribute("nhanVien", nhanVien);
        	if(mucLuongToiThieu==4729400L)
        		return "redirect:/nhanvien?error=01";
        	else if (mucLuongToiThieu==4194100L)
        		return "redirect:/nhanvien?error=02";
        	else if (mucLuongToiThieu==3670100L)
        		return "redirect:/nhanvien?error=03";
        	else 
        		return "redirect:/nhanvien?error=04";
        	}
        
        nhanVien.setDoanhNghiep(doanhNghiep);
        nhanVienRepository.save(nhanVien);
		return "redirect:/doanhnghiep?success";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteNhanVien(@PathVariable("id")Long id) {
		nhanVienRepository.delete(nhanVienRepository.findById(id).orElseThrow());
		return "redirect:/doanhnghiep?delete";
	}
}
