package net.springboot.javaguides.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailService {
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	public void sendEmail() {
		MimeMessage message = sender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			
			// code for FreeMarkerTemplate
//			Map<String, String> context = new HashMap<String, String>();
//			context.put("customer_name", "Trung Hieu");
//			context.put("customer_email", "trantrunghieu1811999@gmail.com");
//			context.put("order_id", "GFSDO23SD9K34");
//			context.put("customer_address", "23/332 Minh Khai");
//			context.put("ship_type", "Giao hang nhanh");
//			context.put("ship_fee", "25.000");
//			context.put("total_noship", "20000000");
//			context.put("total", "25000000");
//			context.put("date_order", "23/04/2023");
//			Template t = config.getTemplate("email-template.ftl");
//			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, context);
			
			// code for Thymeleaf template
			Context context = new Context();
			context.setVariable("customer_name", "Trung Hieu");
			context.setVariable("customer_email", "trantrunghieu1811999@gmail.com");
			context.setVariable("order_id", "GFSDO23SD9K34");
			context.setVariable("customer_address", "23/332 Minh Khai");
			context.setVariable("ship_type", "Giao hang nhanh");
			context.setVariable("ship_fee", "25.000");
			context.setVariable("total_noship", "20000000");
			context.setVariable("total", "25000000");
			String html = templateEngine.process("email-template", context);
			
			helper.setTo("trantrunghieu1811999@gmail.com");
	        helper.setText(html, true);
	        helper.setSubject("Cam on ban da mua hang");
	        helper.setFrom("hieutt181@gmail.com");
	        
	        sender.send(message);
		}catch (Exception e) {
			System.err.println(e);
			
		}
		
		
	}
}
