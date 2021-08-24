package net.springboot.javaguides.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doanhnghiep")
public class DoanhNghiep {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User userDN;
	
	@OneToMany(mappedBy = "doanhNghiep")
	private List<NhanVien> nhanViens;
	
	@Column(name = "ten")
	private String ten;
	
	@Column(name = "madonvi")
	private String maDonVi;
	
	@Column(name = "mathue")
	private String maThue;
	
	@Column(name = "diachikinhdoanh")
	private String diaChiKinhDoanh;
	
	@Column(name = "diachilienhe")
	private String diaChiLienHe;
	
	@Column(name = "loaihinhkinhdoanh")
	private String loaiHinhKinhDoanh;
	
	@Column(name = "sodt")
	private String soDT;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "giayphepkinhdoanh")
	private String giayPhepKinhDoanh;
	
	@Column(name = "noicap")
	private String noiCap;
	
	@Column(name = "phuongthucdong")
	private String phuongThucDong;

	public DoanhNghiep() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DoanhNghiep(String ten, String maDonVi, String maThue, String diaChiKinhDoanh, String diaChiLienHe,
			String loaiHinhKinhDoanh, String soDT, String email, String giayPhepKinhDoanh, String noiCap,
			String phuongThucDong) {
		super();
		this.ten = ten;
		this.maDonVi = maDonVi;
		this.maThue = maThue;
		this.diaChiKinhDoanh = diaChiKinhDoanh;
		this.diaChiLienHe = diaChiLienHe;
		this.loaiHinhKinhDoanh = loaiHinhKinhDoanh;
		this.soDT = soDT;
		this.email = email;
		this.giayPhepKinhDoanh = giayPhepKinhDoanh;
		this.noiCap = noiCap;
		this.phuongThucDong = phuongThucDong;
	}
	
	public static Long CalCulate(List<NhanVien> list) {
		Long total = 0L;//Long.parseLong("0");
    	for(int i=0;i<=list.size()-1;i++) {
    		NhanVien nhanVien=list.get(i);
    		Long sum=nhanVien.getMucluong()+nhanVien.getPhuCap(); 
    		total=total+ (sum*3)/100+ (sum*15)/1000;
    	}
    	return total;
	}
	// mức lương tối thiểu cho nhân viên đki bhyt
	public static long getMucLuongToiThieu(String noicap) {
		long result=0L;
		String[] Vung1= {"Tp.Hà Nội","Tp.Hải Phòng","TP HCM","Đồng Nai","Bình Dương","Bà Rịa - Vũng Tàu"};
		String[] Vung2 = {"Hưng Yên","Vĩnh Phúc","Bắc Ninh","Nam Định","Tp.Đà Nẵng","Tây Ninh","Tp.Cần Thơ"};
		String[] Vung3= {"Hải Dương","Quảng Ninh","Thái Nguyên","Phú Thọ","Lào Cai","Ninh Bình","Thừa Thiên Huế","Quảng Nam","Khánh Hòa","Lâm Đồng","Bình Thuận","Bình Phước","Long An","Tiền Giang","Kiên Giang","An Giang"
				,"Trà Vinh","Cà Mau","Bến Tre","Quảng Bình"};
		String[] Vung4 = {"Bắc Giang","Hà Nam","Hòa Bình","Thanh Hóa","Hà Tĩnh","Phú Yên","Ninh Thuận","Kon Tum","Vĩnh Long","Hậu Giang","Bạc Liêu","Sóc Trăng","Bắc Kạn","Cao Bằng","Đắk Lắk"
				,"Đắk Nông","Điện Biên","Đồng Tháp","Gia Lai","Hà Giang","Lai Châu","Lạng Sơn","Nghệ An","Quảng Trị","Sơn La","Thái Bình","Tuyên Quang","Yên Bái","Bình Định","Quảng Ngãi"};
		for(int i=0;i<=Vung1.length-1;i++) {
			if(noicap.equals(Vung1[i])) 
				result=4729400L;
		}
		for(int i=0;i<=Vung2.length-1;i++) {
			if(noicap.equals(Vung2[i])) 
				result=4194100L;
		}
		for(int i=0;i<=Vung3.length-1;i++) {
			if(noicap.equals(Vung3[i])) 
				result=3670100L;
		}
		for(int i=0;i<=Vung4.length-1;i++) {
			if(noicap.equals(Vung4[i])) 
				result=3284900L;
		}
		return result;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMaDonVi() {
		return maDonVi;
	}

	public void setMaDonVi(String maDonVi) {
		this.maDonVi = maDonVi;
	}

	public String getMaThue() {
		return maThue;
	}

	public void setMaThue(String maThue) {
		this.maThue = maThue;
	}

	public String getDiaChiKinhDoanh() {
		return diaChiKinhDoanh;
	}

	public void setDiaChiKinhDoanh(String diaChiKinhDoanh) {
		this.diaChiKinhDoanh = diaChiKinhDoanh;
	}

	public String getDiaChiLienHe() {
		return diaChiLienHe;
	}

	public void setDiaChiLienHe(String diaChiLienHe) {
		this.diaChiLienHe = diaChiLienHe;
	}

	public String getLoaiHinhKinhDoanh() {
		return loaiHinhKinhDoanh;
	}

	public void setLoaiHinhKinhDoanh(String loaiHinhKinhDoanh) {
		this.loaiHinhKinhDoanh = loaiHinhKinhDoanh;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGiayPhepKinhDoanh() {
		return giayPhepKinhDoanh;
	}

	public void setGiayPhepKinhDoanh(String giayPhepKinhDoanh) {
		this.giayPhepKinhDoanh = giayPhepKinhDoanh;
	}

	public String getNoiCap() {
		return noiCap;
	}

	public void setNoiCap(String noiCap) {
		this.noiCap = noiCap;
	}

	public String getPhuongThucDong() {
		return phuongThucDong;
	}

	public void setPhuongThucDong(String phuongThucDong) {
		this.phuongThucDong = phuongThucDong;
	}

	public List<NhanVien> getNhanViens() {
		return nhanViens;
	}

	public void setNhanViens(List<NhanVien> nhanViens) {
		this.nhanViens = nhanViens;
	}

	public User getUserDN() {
		return userDN;
	}

	public void setUserDN(User userDN) {
		this.userDN = userDN;
	}
	
	
	
}
