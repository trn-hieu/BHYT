package net.springboot.javaguides.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;


import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="canhan")
public class CaNhan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "hogiadinh_id")
	private HoGiaDinh hoGiaDinh;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaySinh;
	
	@Column(name = "gioitinh")
	private String gioiTinh;
	
	@Column(name = "soCMND")
	@Size(max = 11)
	private String soCMND;
	
	@Column(name="diachi")
	private String diaChi;
	
	@Column(name="email")
	private String email;
	
	@Column(name = "sodt")
	private String soDT;
	
	@Column(name = "thanhpho")
	private String thanhPho;
	
	@Column(name = "quan")
	private String quan;
	
	@Column(name = "maBHXH")
	private String maBHXH;
	
	@Column(name = "noiKCB")
	private String noiKCB;
	
	public CaNhan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaNhan(String name, User user, Date ngaySinh, String gioiTinh, @Size(max = 11) String soCMND, String diaChi,
			String email, String soDT, String thanhPho, String quan) {
		super();
		this.name = name;
		this.user = user;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soCMND = soCMND;
		this.diaChi = diaChi;
		this.email = email;
		this.soDT = soDT;
		this.thanhPho = thanhPho;
		this.quan = quan;
	}
	
	

	public CaNhan(String name, Date ngaySinh, String gioiTinh, @Size(max = 11) String soCMND, String diaChi,
			String email, String soDT, String thanhPho, String quan, String maBHXH, String noiKCB) {
		super();
		this.name = name;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soCMND = soCMND;
		this.diaChi = diaChi;
		this.email = email;
		this.soDT = soDT;
		this.thanhPho = thanhPho;
		this.quan = quan;
		this.maBHXH = maBHXH;
		this.noiKCB = noiKCB;
	}

	public CaNhan(String name) {
		super();
		this.name = name;
	}
	

	public CaNhan(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HoGiaDinh getHoGiaDinh() {
		return hoGiaDinh;
	}

	public void setHoGiaDinh(HoGiaDinh hoGiaDinh) {
		this.hoGiaDinh = hoGiaDinh;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		this.soCMND = soCMND;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getThanhPho() {
		return thanhPho;
	}

	public void setThanhPho(String thanhPho) {
		this.thanhPho = thanhPho;
	}

	public String getQuan() {
		return quan;
	}

	public void setQuan(String quan) {
		this.quan = quan;
	}

	public String getMaBHXH() {
		return maBHXH;
	}

	public void setMaBHXH(String maBHXH) {
		this.maBHXH = maBHXH;
	}

	public String getNoiKCB() {
		return noiKCB;
	}

	public void setNoiKCB(String noiKCB) {
		this.noiKCB = noiKCB;
	}

	
	
}
