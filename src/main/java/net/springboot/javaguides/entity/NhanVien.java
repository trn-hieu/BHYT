package net.springboot.javaguides.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "nhanvien")
public class NhanVien {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ten")
	private String ten;
	
	@Column(name = "gioitinh")
	private String gioiTinh;
	
	@Column(name = "ngaysinh")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaySinh;
	
	@Column(name = "diaChi")
	private String diaChi;
	
	@ManyToOne
	@JoinColumn(name = "doanhnghiep_id")
	private DoanhNghiep doanhNghiep;
	
	@Column(name = "maBHXH")
	private String maBHXH;
	
	@Column(name = "chucvu")
	private String chucVu;
	
	@Column(name = "mucluong")
	private long mucluong;
	
	@Column(name = "phucap")
	private long phuCap;
	
	@Column(name = "noiKCB")
	private String noiKCB;
	
	
	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NhanVien(String ten, String gioiTinh, Date ngaySinh, String diaChi, DoanhNghiep doanhNghiep, String maBHXH,
			String chucVu, long mucluong, long phuCap,String noiKCB) {
		super();
		this.ten = ten;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.doanhNghiep = doanhNghiep;
		this.maBHXH = maBHXH;
		this.chucVu = chucVu;
		this.mucluong = mucluong;
		this.phuCap = phuCap;
		this.noiKCB=noiKCB;
	}
	

	public NhanVien(long mucluong, long phuCap) {
		super();
		this.mucluong = mucluong;
		this.phuCap = phuCap;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTen() {
		return ten;
	}


	public void setTen(String ten) {
		this.ten = ten;
	}


	public String getGioiTinh() {
		return gioiTinh;
	}


	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}


	public String getDiaChi() {
		return diaChi;
	}


	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}


	public DoanhNghiep getDoanhNghiep() {
		return doanhNghiep;
	}


	public void setDoanhNghiep(DoanhNghiep doanhNghiep) {
		this.doanhNghiep = doanhNghiep;
	}


	public String getMaBHXH() {
		return maBHXH;
	}


	public void setMaBHXH(String maBHXH) {
		this.maBHXH = maBHXH;
	}


	public String getChucVu() {
		return chucVu;
	}


	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}


	public long getMucluong() {
		return mucluong;
	}


	public void setMucluong(long mucluong) {
		this.mucluong = mucluong;
	}


	public long getPhuCap() {
		return phuCap;
	}


	public void setPhuCap(long phuCap) {
		this.phuCap = phuCap;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getNoiKCB() {
		return noiKCB;
	}

	public void setNoiKCB(String noiKCB) {
		this.noiKCB = noiKCB;
	}

	
	
	
	
	
}
