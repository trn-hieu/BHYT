package net.springboot.javaguides.Junit;


import java.util.ArrayList;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


import junit.framework.TestCase;
import net.springboot.javaguides.entity.CaNhan;
import net.springboot.javaguides.entity.HoGiaDinh;

@SpringBootTest
public class TestHoGiaDinhCalculation  {
	@Test
	public void testEmptyList() {
		List<CaNhan> list=new ArrayList<>();
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(0, total);
	}
	
	@Test
	public void test_Mot_Thanh_Vien() {
		List<CaNhan> list=new ArrayList<>();
		list.add(new CaNhan("A"));
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(804600, total);
	}
	@Test
	public void test_2_Thanh_Vien() {
		List<CaNhan> list=new ArrayList<>();
		list.add(new CaNhan("A"));
		list.add(new CaNhan("B"));
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(1367820, total);
	}
	@Test
	public void test_3_Thanh_Vien() {
		List<CaNhan> list=new ArrayList<>();
		list.add(new CaNhan("A"));
		list.add(new CaNhan("B"));
		list.add(new CaNhan("C"));
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(1850580, total);
	}
	@Test
	public void test_4_Thanh_Vien() {
		List<CaNhan> list=new ArrayList<>();
		list.add(new CaNhan("A"));
		list.add(new CaNhan("B"));
		list.add(new CaNhan("C"));
		list.add(new CaNhan("D"));
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(2252880, total);
	}
	@Test
	public void test_5_Thanh_Vien() {
		List<CaNhan> list=new ArrayList<>();
		list.add(new CaNhan("A"));
		list.add(new CaNhan("B"));
		list.add(new CaNhan("C"));
		list.add(new CaNhan("D"));
		list.add(new CaNhan("E"));
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(2574720, total);
	}
	@Test
	public void test_6_Thanh_Vien() {
		List<CaNhan> list=new ArrayList<>();
		list.add(new CaNhan("A"));
		list.add(new CaNhan("B"));
		list.add(new CaNhan("C"));
		list.add(new CaNhan("D"));list.add(new CaNhan("E"));list.add(new CaNhan("F"));
		HoGiaDinh hoGiaDinh=new HoGiaDinh();
		long total = hoGiaDinh.Calculate(list);
		Assert.assertEquals(2896560, total);
	}
}
