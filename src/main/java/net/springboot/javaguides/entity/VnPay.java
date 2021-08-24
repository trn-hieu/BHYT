package net.springboot.javaguides.entity;

public class VnPay {
	private String vnp_OrderInfo;
	private String vnp_OrderType;
	private Long vnp_Amount;
	private String vnp_Locale;
	private String vnp_BankCode;
	
	public VnPay() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VnPay(String vnp_OrderInfo, String vnp_OrderType, Long vnp_Amount, String vnp_Locale,
			String vnp_BankCode) {
		super();
		this.vnp_OrderInfo = vnp_OrderInfo;
		this.vnp_OrderType = vnp_OrderType;
		this.vnp_Amount = vnp_Amount;
		this.vnp_Locale = vnp_Locale;
		this.vnp_BankCode = vnp_BankCode;
	}

	public String getVnp_OrderInfo() {
		return vnp_OrderInfo;
	}

	public void setVnp_OrderInfo(String vnp_OrderInfo) {
		this.vnp_OrderInfo = vnp_OrderInfo;
	}

	public String getVnp_OrderType() {
		return vnp_OrderType;
	}

	public void setVnp_OrderType(String vnp_OrderType) {
		this.vnp_OrderType = vnp_OrderType;
	}

	public Long getVnp_Amount() {
		return vnp_Amount;
	}

	public void setVnp_Amount(Long vnp_Amount) {
		this.vnp_Amount = vnp_Amount;
	}

	public String getVnp_Locale() {
		return vnp_Locale;
	}

	public void setVnp_Locale(String vnp_Locale) {
		this.vnp_Locale = vnp_Locale;
	}

	public String getVnp_BankCode() {
		return vnp_BankCode;
	}

	public void setVnp_BankCode(String vnp_BankCode) {
		this.vnp_BankCode = vnp_BankCode;
	}
	
	
}
