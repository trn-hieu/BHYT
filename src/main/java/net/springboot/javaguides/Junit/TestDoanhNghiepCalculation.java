package net.springboot.javaguides.Junit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import junit.framework.TestCase;
import net.springboot.javaguides.entity.DoanhNghiep;
import net.springboot.javaguides.entity.NhanVien;

@SpringBootTest
public class TestDoanhNghiepCalculation  {
	@Test
	public void TestEmptyList() {
		List<NhanVien> list=new ArrayList<>();
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(0L, total);
	}
	@Test
	public void TestEmptyNhanvien() {
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien());
		long total =new DoanhNghiep().CalCulate(list);
		//Assert.assertEquals();
		Assert.assertEquals(0L, total);
	}
	
	@Test
	public void Test_mucLuong_phuCap_bang_0() {
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(0L,0L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(0, total);
	}
	@Test
	public void Test_mucLuong_nho_hon_0(){
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(-1L,1000000L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(45000, total);
	}
	@Test
	public void Test_phuCap_nho_hon_0(){
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(1000000L,-1L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(45000, total);
	}
	@Test
	public void Test_mucLuong_bang_0() {
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(0L,10000000L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(450000, total);
	}
	@Test
	public void Test_phuCap_bang_0() {
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(10000000L,0L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(450000, total);
	}
	@Test
	public void Test_mucLuong_phuCap_Lon_Hon_0() {
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(1000000L,1000000L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(90000, total);
	}
	@Test
	public void Test_2_NhanVien() {
		List<NhanVien> list=new ArrayList<>();
		list.add(new NhanVien(1000000L,1000000L));
		list.add(new NhanVien(1000000L,1000000L));
		long total =new DoanhNghiep().CalCulate(list);
		Assert.assertEquals(180000, total);
	}
	
	
}
