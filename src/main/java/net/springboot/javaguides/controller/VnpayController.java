package net.springboot.javaguides.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.springboot.javaguides.congif.VnpayConfig;
import net.springboot.javaguides.entity.ThanhToan;
import net.springboot.javaguides.entity.User;
import net.springboot.javaguides.entity.VnPay;
import net.springboot.javaguides.entity.VnPayResponse;
import net.springboot.javaguides.repository.ThanhToanRepository;
import net.springboot.javaguides.service.UserService;

@Controller
@RequestMapping("/api/vnpay")
public class VnpayController {
	@Autowired
	private ThanhToanRepository thanhToanRepository;
	@Autowired
	private UserService userService;
	
	@PostMapping("/make")
	public String createPayment(HttpServletRequest request, 
			VnPay vnPay) throws UnsupportedEncodingException {
		
		String vnp_OrderInfo=vnPay.getVnp_OrderInfo();
		String ordertype=vnPay.getVnp_OrderType();
		Long amount=vnPay.getVnp_Amount();
//		System.out.println(amount);
//		System.out.println(String.valueOf(amount * 100));
		String language=vnPay.getVnp_Locale();
		String bankcode=vnPay.getVnp_BankCode();
		
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
		String vnp_IpAddr = VnpayConfig.getIpAddress(request);
		String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
		vnp_Params.put("vnp_CurrCode", "VND");
		//if (bankcode != null && bankcode.isEmpty()) {
	
			vnp_Params.put("vnp_BankCode", bankcode);
		//}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
		vnp_Params.put("vnp_OrderType", ordertype);

		if (language != null && !language.isEmpty()) {
			vnp_Params.put("vnp_Locale", language);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

//		Date dt = new Date();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		String dateString = formatter.format(dt);
//		String vnp_CreateDate = dateString;
//		String vnp_TransDate = vnp_CreateDate;
//		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//		vnp_Params.put("vnp_TransDate", vnp_TransDate);
		
		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		

		// Build data to hash and querystring
		List<String> fieldNames = new ArrayList<>(vnp_Params.keySet()); // tra ve key trong Map
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator<String> itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName); // lay value đc gán với key tương ứng
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
		}

		String queryUrl = query.toString();
		//String vnp_SecureHash = VnpayConfig.Sha256(VnpayConfig.vnp_HashSecret + hashData.toString());
		String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
		// System.out.println("HashData=" + hashData.toString());
		//queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
		vnp_Params.put("redirect_url", paymentUrl);
		System.err.println(paymentUrl);
		return "redirect:" + paymentUrl;
	//	return paymentUrl;
		//return vnp_Params;
		//return "/";
	}

	
	@GetMapping(value = "/result")
	public String completePayment(HttpServletRequest request, 
			@RequestParam(name = "vnp_OrderInfo") String vnp_OrderInfo,
			@RequestParam(name = "vnp_Amount") Integer vnp_Amount,
			@RequestParam(name = "vnp_BankCode", defaultValue = "") String vnp_BankCode,
			@RequestParam(name = "vnp_BankTranNo",defaultValue = "") String vnp_BankTranNo,
			@RequestParam(name = "vnp_CardType") String vnp_CardType,
			@RequestParam(name = "vnp_PayDate") String vnp_PayDate,
			@RequestParam(name = "vnp_ResponseCode") String vnp_ResponseCode,
			@RequestParam(name = "vnp_TransactionNo") String vnp_TransactionNo,
			@RequestParam(name = "vnp_TxnRef") String vnp_TxnRef
			,Model model) {
//		Map<String, String> response = new HashMap<>();
//		
//		response.put("vnp_OrderInfo", vnp_OrderInfo);
//		response.put("vnp_Amount", vnp_Amount.toString());
//		response.put("vnp_BankCode", vnp_BankCode);
//		response.put("vnp_BankTranNo", vnp_BankTranNo);
//		response.put("vnp_CardType", vnp_CardType);
//		response.put("vnp_PayDate", vnp_PayDate);
//		response.put("vnp_ResponseCode", vnp_ResponseCode);
//		response.put("vnp_TransactionNo", vnp_TransactionNo);
//		response.put("vnp_TxnRef", vnp_TxnRef);
		
		VnPayResponse response = new VnPayResponse(vnp_OrderInfo, vnp_Amount, vnp_BankCode, vnp_BankTranNo,
				vnp_CardType, vnp_PayDate, vnp_ResponseCode, vnp_TransactionNo, vnp_TxnRef);
		System.out.println(vnp_Amount);
		//check thong tin da luu DB chua
		ThanhToan checkExist =thanhToanRepository.checkExist(vnp_TransactionNo, vnp_TxnRef);
		// chua luu thi luu
		if(vnp_ResponseCode.equals("00") && checkExist==null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        UserDetails userDetail = (UserDetails) auth.getPrincipal();
	        User u = userService.getUser(userDetail.getUsername());
	        List<User> list=new ArrayList<>();
	        list.add(u);
			ThanhToan thanhToan=new ThanhToan();
			thanhToan.setUserTT(list);
			thanhToan.setSoTien(vnp_Amount/100);
			thanhToan.setMaGD(vnp_TransactionNo);
			thanhToan.setSoHoaDon(vnp_TxnRef);
			//20210518165050
			String date=vnp_PayDate.substring(6, 8)+"/"+vnp_PayDate.substring(4,6)
			+"/"+vnp_PayDate.substring(0, 4)+" "+vnp_PayDate.substring(8, 10)+":"
			+vnp_PayDate.substring(10, 12)+":"+vnp_PayDate.substring(12, 14);
			vnp_PayDate=date;
			
			thanhToan.setThoiGian(vnp_PayDate);
			thanhToan.setNoiDung(vnp_OrderInfo);
			thanhToan.setPhuongThuc("VnPay");
			thanhToanRepository.save(thanhToan);	
		
		}
		model.addAttribute("vnpayresponse", response);
		//return response;
		return "vnPayResponse";
	}
}
