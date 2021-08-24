package net.springboot.javaguides.entity;

public class VnPayResponse {
	private String vnp_OrderInfo;
	private int vnp_Amount;
	private String vnp_BankCode;
	private String vnp_BankTranNo;
	private String vnp_CardType;
	private String vnp_PayDate;
	private String vnp_ResponseCode;
	private String vnp_TransactionNo;
	private String vnp_TxnRef;
	
	public VnPayResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VnPayResponse(String vnp_OrderInfo, int vnp_Amount, String vnp_BankCode, String vnp_BankTranNo,
			String vnp_CardType, String vnp_PayDate, String vnp_ResponseCode, String vnp_TransactionNo,
			String vnp_TxnRef) {
		super();
		this.vnp_OrderInfo = vnp_OrderInfo;
		this.vnp_Amount = vnp_Amount;
		this.vnp_BankCode = vnp_BankCode;
		this.vnp_BankTranNo = vnp_BankTranNo;
		this.vnp_CardType = vnp_CardType;
		this.vnp_PayDate = vnp_PayDate;
		this.vnp_ResponseCode = vnp_ResponseCode;
		this.vnp_TransactionNo = vnp_TransactionNo;
		this.vnp_TxnRef = vnp_TxnRef;
	}

	public String getVnp_OrderInfo() {
		return vnp_OrderInfo;
	}

	public void setVnp_OrderInfo(String vnp_OrderInfo) {
		this.vnp_OrderInfo = vnp_OrderInfo;
	}

	public int getVnp_Amount() {
		return vnp_Amount;
	}

	public void setVnp_Amount(int vnp_Amount) {
		this.vnp_Amount = vnp_Amount;
	}

	public String getVnp_BankCode() {
		return vnp_BankCode;
	}

	public void setVnp_BankCode(String vnp_BankCode) {
		this.vnp_BankCode = vnp_BankCode;
	}

	public String getVnp_BankTranNo() {
		return vnp_BankTranNo;
	}

	public void setVnp_BankTranNo(String vnp_BankTranNo) {
		this.vnp_BankTranNo = vnp_BankTranNo;
	}

	public String getVnp_CardType() {
		return vnp_CardType;
	}

	public void setVnp_CardType(String vnp_CardType) {
		this.vnp_CardType = vnp_CardType;
	}

	public String getVnp_PayDate() {
		return vnp_PayDate;
	}

	public void setVnp_PayDate(String vnp_PayDate) {
		this.vnp_PayDate = vnp_PayDate;
	}

	public String getVnp_ResponseCode() {
		return vnp_ResponseCode;
	}

	public void setVnp_ResponseCode(String vnp_ResponseCode) {
		this.vnp_ResponseCode = vnp_ResponseCode;
	}

	public String getVnp_TransactionNo() {
		return vnp_TransactionNo;
	}

	public void setVnp_TransactionNo(String vnp_TransactionNo) {
		this.vnp_TransactionNo = vnp_TransactionNo;
	}

	public String getVnp_TxnRef() {
		return vnp_TxnRef;
	}

	public void setVnp_TxnRef(String vnp_TxnRef) {
		this.vnp_TxnRef = vnp_TxnRef;
	}
	
	
}
