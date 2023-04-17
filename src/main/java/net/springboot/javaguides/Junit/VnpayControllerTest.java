/*
 * package net.springboot.javaguides.Junit;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertThrows;
 * 
 * import java.io.UnsupportedEncodingException; import java.net.URLEncoder;
 * import java.nio.charset.StandardCharsets; import java.text.SimpleDateFormat;
 * import java.util.ArrayList; import java.util.Collections; import
 * java.util.Date; import java.util.HashMap; import java.util.Iterator; import
 * java.util.List; import java.util.Map;
 * 
 * import javax.servlet.http.HttpServletRequest;
 * 
 * import org.junit.jupiter.api.Test; import org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.context.ApplicationContext; import
 * org.springframework.security.test.context.support.WithMockUser; import
 * org.springframework.test.context.junit4.SpringRunner; import
 * org.springframework.transaction.annotation.Transactional; import
 * org.springframework.ui.Model;
 * 
 * import net.springboot.javaguides.congif.VnpayConfig; import
 * net.springboot.javaguides.controller.VnpayController; import
 * net.springboot.javaguides.controller.dto.UserRegistrationDto; import
 * net.springboot.javaguides.entity.ThanhToan; import
 * net.springboot.javaguides.entity.User; import
 * net.springboot.javaguides.entity.VnPay; import
 * net.springboot.javaguides.repository.ThanhToanRepository; import
 * net.springboot.javaguides.service.UserService;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest
 * 
 * @Transactional class VnpayControllerTest {
 * 
 * @Autowired private ApplicationContext context;
 * 
 * @Autowired VnpayController vnpayController;
 * 
 * @MockBean HttpServletRequest request;
 * 
 * @MockBean Model model;
 * 
 * @Test void testCreatePayment_Successfull() {
 * 
 * VnPay vnPay = new VnPay();
 * vnPay.setVnp_OrderInfo("Thanh toan BHYT theo ho gia dinh");
 * vnPay.setVnp_OrderType("billpayment"); vnPay.setVnp_Amount(1000000L);
 * vnPay.setVnp_Locale("vn"); vnPay.setVnp_BankCode("NCB");
 * 
 * String s = vnpayController.createPayment(request, vnPay);
 * 
 * int vitri = s.indexOf("vnp_TxnRef");
 * 
 * String vnp_OrderInfo=vnPay.getVnp_OrderInfo(); String
 * ordertype=vnPay.getVnp_OrderType(); long amount=vnPay.getVnp_Amount(); String
 * language=vnPay.getVnp_Locale(); String bankcode=vnPay.getVnp_BankCode();
 * 
 * String vnp_Version = "2.0.0"; String vnp_Command = "pay"; String vnp_TxnRef =
 * s.substring(vitri + 11, vitri + 11 + 8); String vnp_IpAddr =
 * VnpayConfig.getIpAddress(request); String vnp_TmnCode =
 * VnpayConfig.vnp_TmnCode;
 * 
 * Map<String, String> vnp_Params = new HashMap<>();
 * vnp_Params.put("vnp_Version", vnp_Version); vnp_Params.put("vnp_Command",
 * vnp_Command); vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
 * vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
 * vnp_Params.put("vnp_CurrCode", "VND"); vnp_Params.put("vnp_BankCode",
 * bankcode); vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
 * vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
 * vnp_Params.put("vnp_OrderType", ordertype); vnp_Params.put("vnp_Locale",
 * language); vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
 * vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
 * 
 * Date dt = new Date(); SimpleDateFormat formatter = new
 * SimpleDateFormat("yyyyMMddHHmmss"); String dateString = formatter.format(dt);
 * String vnp_CreateDate = dateString; String vnp_TransDate = vnp_CreateDate;
 * vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
 * vnp_Params.put("vnp_TransDate", vnp_TransDate);
 * 
 * // Build data to hash and querystring List<String> fieldNames = new
 * ArrayList<>(vnp_Params.keySet()); Collections.sort(fieldNames); StringBuilder
 * hashData = new StringBuilder(); StringBuilder query = new StringBuilder();
 * Iterator<String> itr = fieldNames.iterator(); while (itr.hasNext()) { String
 * fieldName = (String) itr.next(); String fieldValue = (String)
 * vnp_Params.get(fieldName); if ((fieldValue != null) && (fieldValue.length() >
 * 0)) { // Build hash data hashData.append(fieldName); hashData.append('=');
 * hashData.append(fieldValue); // Build query try {
 * query.append(URLEncoder.encode(fieldName,
 * StandardCharsets.US_ASCII.toString())); query.append('=');
 * query.append(URLEncoder.encode(fieldValue,
 * StandardCharsets.US_ASCII.toString())); } catch (UnsupportedEncodingException
 * e) { e.printStackTrace(); } if (itr.hasNext()) { query.append('&');
 * hashData.append('&'); } } }
 * 
 * String queryUrl = query.toString(); String vnp_SecureHash =
 * VnpayConfig.Sha256(VnpayConfig.vnp_HashSecret + hashData.toString());
 * queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
 * String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
 * 
 * assertEquals(s, "redirect:" + paymentUrl); }
 * 
 * @Test void testCreatePayment_Successfull_LocaleIsNull() {
 * 
 * VnPay vnPay = new VnPay();
 * vnPay.setVnp_OrderInfo("Thanh toan BHYT theo ho gia dinh");
 * vnPay.setVnp_OrderType("billpayment"); vnPay.setVnp_Amount(1000000L);
 * vnPay.setVnp_BankCode("NCB"); vnPay.setVnp_Locale(null);
 * 
 * String s = vnpayController.createPayment(request, vnPay);
 * 
 * int vitri = s.indexOf("vnp_TxnRef");
 * 
 * String vnp_OrderInfo=vnPay.getVnp_OrderInfo(); String
 * ordertype=vnPay.getVnp_OrderType(); long amount=vnPay.getVnp_Amount(); String
 * language=vnPay.getVnp_Locale(); String bankcode=vnPay.getVnp_BankCode();
 * 
 * String vnp_Version = "2.0.0"; String vnp_Command = "pay"; String vnp_TxnRef =
 * s.substring(vitri + 11, vitri + 11 + 8); String vnp_IpAddr =
 * VnpayConfig.getIpAddress(request); String vnp_TmnCode =
 * VnpayConfig.vnp_TmnCode;
 * 
 * Map<String, String> vnp_Params = new HashMap<>();
 * vnp_Params.put("vnp_Version", vnp_Version); vnp_Params.put("vnp_Command",
 * vnp_Command); vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
 * vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
 * vnp_Params.put("vnp_CurrCode", "VND"); vnp_Params.put("vnp_BankCode",
 * bankcode); vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
 * vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
 * vnp_Params.put("vnp_OrderType", ordertype); vnp_Params.put("vnp_Locale",
 * "vn"); vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
 * vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
 * 
 * Date dt = new Date(); SimpleDateFormat formatter = new
 * SimpleDateFormat("yyyyMMddHHmmss"); String dateString = formatter.format(dt);
 * String vnp_CreateDate = dateString; String vnp_TransDate = vnp_CreateDate;
 * vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
 * vnp_Params.put("vnp_TransDate", vnp_TransDate);
 * 
 * // Build data to hash and querystring List<String> fieldNames = new
 * ArrayList<>(vnp_Params.keySet()); Collections.sort(fieldNames); StringBuilder
 * hashData = new StringBuilder(); StringBuilder query = new StringBuilder();
 * Iterator<String> itr = fieldNames.iterator(); while (itr.hasNext()) { String
 * fieldName = (String) itr.next(); String fieldValue = (String)
 * vnp_Params.get(fieldName); if ((fieldValue != null) && (fieldValue.length() >
 * 0)) { // Build hash data hashData.append(fieldName); hashData.append('=');
 * hashData.append(fieldValue); // Build query try {
 * query.append(URLEncoder.encode(fieldName,
 * StandardCharsets.US_ASCII.toString())); query.append('=');
 * query.append(URLEncoder.encode(fieldValue,
 * StandardCharsets.US_ASCII.toString())); } catch (UnsupportedEncodingException
 * e) { e.printStackTrace(); } if (itr.hasNext()) { query.append('&');
 * hashData.append('&'); } } }
 * 
 * String queryUrl = query.toString(); String vnp_SecureHash =
 * VnpayConfig.Sha256(VnpayConfig.vnp_HashSecret + hashData.toString());
 * queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
 * String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
 * 
 * assertEquals(s, "redirect:" + paymentUrl); }
 * 
 * @Test void testCreatePayment_Successfull_LocaleIsEmpty() {
 * 
 * VnPay vnPay = new VnPay();
 * vnPay.setVnp_OrderInfo("Thanh toan BHYT theo ho gia dinh");
 * vnPay.setVnp_OrderType("billpayment"); vnPay.setVnp_Amount(1000000L);
 * vnPay.setVnp_BankCode("NCB"); vnPay.setVnp_Locale("");
 * 
 * String s = vnpayController.createPayment(request, vnPay);
 * 
 * int vitri = s.indexOf("vnp_TxnRef");
 * 
 * String vnp_OrderInfo=vnPay.getVnp_OrderInfo(); String
 * ordertype=vnPay.getVnp_OrderType(); long amount=vnPay.getVnp_Amount(); String
 * language=vnPay.getVnp_Locale(); String bankcode=vnPay.getVnp_BankCode();
 * 
 * String vnp_Version = "2.0.0"; String vnp_Command = "pay"; String vnp_TxnRef =
 * s.substring(vitri + 11, vitri + 11 + 8); String vnp_IpAddr =
 * VnpayConfig.getIpAddress(request); String vnp_TmnCode =
 * VnpayConfig.vnp_TmnCode;
 * 
 * Map<String, String> vnp_Params = new HashMap<>();
 * vnp_Params.put("vnp_Version", vnp_Version); vnp_Params.put("vnp_Command",
 * vnp_Command); vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
 * vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
 * vnp_Params.put("vnp_CurrCode", "VND"); vnp_Params.put("vnp_BankCode",
 * bankcode); vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
 * vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
 * vnp_Params.put("vnp_OrderType", ordertype); vnp_Params.put("vnp_Locale",
 * "vn"); vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);
 * vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
 * 
 * Date dt = new Date(); SimpleDateFormat formatter = new
 * SimpleDateFormat("yyyyMMddHHmmss"); String dateString = formatter.format(dt);
 * String vnp_CreateDate = dateString; String vnp_TransDate = vnp_CreateDate;
 * vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
 * vnp_Params.put("vnp_TransDate", vnp_TransDate);
 * 
 * // Build data to hash and querystring List<String> fieldNames = new
 * ArrayList<>(vnp_Params.keySet()); Collections.sort(fieldNames); StringBuilder
 * hashData = new StringBuilder(); StringBuilder query = new StringBuilder();
 * Iterator<String> itr = fieldNames.iterator(); while (itr.hasNext()) { String
 * fieldName = (String) itr.next(); String fieldValue = (String)
 * vnp_Params.get(fieldName); if ((fieldValue != null) && (fieldValue.length() >
 * 0)) { // Build hash data hashData.append(fieldName); hashData.append('=');
 * hashData.append(fieldValue); // Build query try {
 * query.append(URLEncoder.encode(fieldName,
 * StandardCharsets.US_ASCII.toString())); query.append('=');
 * query.append(URLEncoder.encode(fieldValue,
 * StandardCharsets.US_ASCII.toString())); } catch (UnsupportedEncodingException
 * e) { e.printStackTrace(); } if (itr.hasNext()) { query.append('&');
 * hashData.append('&'); } } }
 * 
 * String queryUrl = query.toString(); String vnp_SecureHash =
 * VnpayConfig.Sha256(VnpayConfig.vnp_HashSecret + hashData.toString());
 * queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
 * String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
 * 
 * assertEquals(s, "redirect:" + paymentUrl); }
 * 
 * @Test void testCreatePayment_Successfull_VnPayNull() {
 * 
 * VnPay vnPay = new VnPay();
 * 
 * assertThrows(NullPointerException.class,
 * ()->{vnpayController.createPayment(request, vnPay);}, "Amount is not Null");
 * }
 * 
 * @WithMockUser(username = "test2")
 * 
 * @Test void testCompletePayment_ThanhCong() { UserService userService =
 * this.context.getBean(UserService.class); ThanhToanRepository
 * thanhToanRepository = context.getBean(ThanhToanRepository.class);
 * 
 * UserRegistrationDto user = new UserRegistrationDto();
 * user.setFirstName("Hieu"); user.setLastName("Nguyen");
 * user.setEmail("test2"); user.setPassword("123456"); userService.save(user);
 * 
 * String vnp_OrderInfo = "Thanh toan BHYT theo ho gia dinh"; Integer vnp_Amount
 * = 136782000; String vnp_BankCode = "NCB"; String vnp_BankTranNo = "NCB";
 * String vnp_CardType = "CARD"; String vnp_PayDate = "20210518165050"; String
 * vnp_ResponseCode = "00"; String vnp_TransactionNo = "13512148"; String
 * vnp_TxnRef = "12345678";
 * 
 * String s = vnpayController.completePayment(request, vnp_OrderInfo,
 * vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType, vnp_PayDate,
 * vnp_ResponseCode, vnp_TransactionNo, vnp_TxnRef, model);
 * assertEquals("vnPayResponse", s);
 * 
 * ThanhToan thanhToan=new ThanhToan(); thanhToan.setSoTien(vnp_Amount/100);
 * thanhToan.setMaGD(vnp_TransactionNo); thanhToan.setSoHoaDon(vnp_TxnRef);
 * String date=vnp_PayDate.substring(6, 8)+"/"+vnp_PayDate.substring(4,6)
 * +"/"+vnp_PayDate.substring(0, 4)+" "+vnp_PayDate.substring(8, 10)+":"
 * +vnp_PayDate.substring(10, 12)+":"+vnp_PayDate.substring(12, 14);
 * vnp_PayDate=date;
 * 
 * thanhToan.setThoiGian(vnp_PayDate); thanhToan.setNoiDung(vnp_OrderInfo);
 * thanhToan.setPhuongThuc("VnPay");
 * 
 * ThanhToan test = thanhToanRepository.checkExist(vnp_TransactionNo,
 * vnp_TxnRef); assertEquals(thanhToan.getMaGD(), test.getMaGD());
 * assertEquals(thanhToan.getSoHoaDon(), test.getSoHoaDon());
 * 
 * User u = userService.getUser(user.getEmail()); List<ThanhToan> listTT
 * =thanhToanRepository.findbyUserId(u.getId()); assertEquals(1, listTT.size());
 * }
 * 
 * @WithMockUser(username = "test2")
 * 
 * @Test void testCompletePayment_DaThanhToan() { UserService userService =
 * this.context.getBean(UserService.class); ThanhToanRepository
 * thanhToanRepository = context.getBean(ThanhToanRepository.class);
 * 
 * UserRegistrationDto user = new UserRegistrationDto();
 * user.setFirstName("Hieu"); user.setLastName("Nguyen");
 * user.setEmail("test2"); user.setPassword("123456"); userService.save(user);
 * 
 * String vnp_OrderInfo = "Thanh toan BHYT theo ho gia dinh"; Integer vnp_Amount
 * = 136782000; String vnp_BankCode = "NCB"; String vnp_BankTranNo = "NCB";
 * String vnp_CardType = "CARD"; String vnp_PayDate = "20210518165050"; String
 * vnp_ResponseCode = "00"; String vnp_TransactionNo = "13512148"; String
 * vnp_TxnRef = "12345678";
 * 
 * User u = userService.getUser(user.getEmail()); List<User> list=new
 * ArrayList<>(); list.add(u);
 * 
 * ThanhToan thanhToan=new ThanhToan(); thanhToan.setUserTT(list);
 * thanhToan.setSoTien(vnp_Amount/100); thanhToan.setMaGD(vnp_TransactionNo);
 * thanhToan.setSoHoaDon(vnp_TxnRef);
 * 
 * String date=vnp_PayDate.substring(6, 8)+"/"+vnp_PayDate.substring(4,6)
 * +"/"+vnp_PayDate.substring(0, 4)+" "+vnp_PayDate.substring(8, 10)+":"
 * +vnp_PayDate.substring(10, 12)+":"+vnp_PayDate.substring(12, 14);
 * vnp_PayDate=date;
 * 
 * thanhToan.setThoiGian(vnp_PayDate); thanhToan.setNoiDung(vnp_OrderInfo);
 * thanhToan.setPhuongThuc("VnPay"); thanhToanRepository.save(thanhToan);
 * 
 * String s = vnpayController.completePayment(request, vnp_OrderInfo,
 * vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType, vnp_PayDate,
 * vnp_ResponseCode, vnp_TransactionNo, vnp_TxnRef, model);
 * assertEquals("vnPayResponse", s);
 * 
 * List<ThanhToan> listTT =thanhToanRepository.findbyUserId(u.getId());
 * assertEquals(1, listTT.size()); }
 * 
 * @WithMockUser(username = "test2")
 * 
 * @Test void testCompletePayment_KhongThanhCong() { UserService userService =
 * this.context.getBean(UserService.class); ThanhToanRepository
 * thanhToanRepository = context.getBean(ThanhToanRepository.class);
 * 
 * UserRegistrationDto user = new UserRegistrationDto();
 * user.setFirstName("Hieu"); user.setLastName("Nguyen");
 * user.setEmail("test2"); user.setPassword("123456"); userService.save(user);
 * 
 * String vnp_OrderInfo = "Thanh toan BHYT theo ho gia dinh"; Integer vnp_Amount
 * = 136782000; String vnp_BankCode = "NCB"; String vnp_BankTranNo = "NCB";
 * String vnp_CardType = ""; String vnp_PayDate = ""; String vnp_ResponseCode =
 * "01"; String vnp_TransactionNo = ""; String vnp_TxnRef = "12345678";
 * 
 * String s = vnpayController.completePayment(request, vnp_OrderInfo,
 * vnp_Amount, vnp_BankCode, vnp_BankTranNo, vnp_CardType, vnp_PayDate,
 * vnp_ResponseCode, vnp_TransactionNo, vnp_TxnRef, model);
 * assertEquals("vnPayResponse", s);
 * 
 * User u = userService.getUser(user.getEmail()); List<ThanhToan> listTT
 * =thanhToanRepository.findbyUserId(u.getId()); assertEquals(0, listTT.size());
 * } }
 */