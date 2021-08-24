package net.springboot.javaguides.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "thanhtoan")
public class ThanhToan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "magiaodich")
	private String maGD;
	
	@Column(name = "sohoadon")
	private String soHoaDon;
	
	@Column(name = "sotien")
	private long soTien;
	
	@Column(name = "phuongthuc")
	private String phuongThuc;
	
	@Column(name = "noidung")
	private String noiDung;
	
	@Column(name = "thoigian")
	private String thoiGian;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "user_payment"
	,joinColumns = @JoinColumn(name="thanhtoan_id")
	,inverseJoinColumns = @JoinColumn(name="user_id") )
	private List<User> userTT;

	public ThanhToan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ThanhToan(String maGD, String soHoaDon, long soTien, String phuongThuc, String noiDung, String thoiGian,
			List<User> userTT) {
		super();
		this.maGD = maGD;
		this.soHoaDon = soHoaDon;
		this.soTien = soTien;
		this.phuongThuc = phuongThuc;
		this.noiDung = noiDung;
		this.thoiGian = thoiGian;
		this.userTT = userTT;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMaGD() {
		return maGD;
	}

	public void setMaGD(String maGD) {
		this.maGD = maGD;
	}

	public String getSoHoaDon() {
		return soHoaDon;
	}

	public void setSoHoaDon(String soHoaDon) {
		this.soHoaDon = soHoaDon;
	}

	public long getSoTien() {
		return soTien;
	}

	public void setSoTien(long soTien) {
		this.soTien = soTien;
	}

	public String getPhuongThuc() {
		return phuongThuc;
	}

	public void setPhuongThuc(String phuongThuc) {
		this.phuongThuc = phuongThuc;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(String thoiGian) {
		this.thoiGian = thoiGian;
	}

	public List<User> getUserTT() {
		return userTT;
	}

	public void setUserTT(List<User> userTT) {
		this.userTT = userTT;
	}
	
	
}
