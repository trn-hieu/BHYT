package net.springboot.javaguides.selenium;

import static org.junit.Assert.assertEquals;

//import static org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.springboot.javaguides.entity.User;

public class Login {
	static String siteUrl="http://localhost:8080/login";
	WebDriver driver;
	JavascriptExecutor js;
	static ArrayList<User> acclist= new ArrayList<User>();
	
	public static boolean isClickable(WebElement el, WebDriver driver) {
		try {
		WebDriverWait wait =new WebDriverWait(driver, 6);
		wait.until(ExpectedConditions.elementToBeClickable(el));
		return true;
		} 
		catch (Exception e){
            return false;
        }
	}
	
	@BeforeAll
	public static void setup() throws IOException {
		String filePath="C:\\Users\\ADMINS\\eclipse-workspace\\BHYTtestSelenium\\data";
		String fileName="AccountBHYT.xlsx";
		String sheetName="Login";
		
		File file =new File(filePath+"\\"+fileName);
		FileInputStream inputStream=new FileInputStream(file);
		Workbook workbook= new XSSFWorkbook(inputStream);
		Sheet sheet=workbook.getSheet(sheetName);
		int rowCount =sheet.getLastRowNum()- sheet.getFirstRowNum();
		for(int i=1;i < rowCount + 1;i++) {
			Row row=sheet.getRow(i);
			
			User user=new User();
			user.setEmail(row.getCell(0).getStringCellValue());
			user.setPassword(row.getCell(1).getStringCellValue());
			
			acclist.add(user);
		}
		workbook.close();
	}
	
	@BeforeEach
	public void init() {
	ChromeOptions options=new ChromeOptions();
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMINS\\Desktop\\Study\\chromedriver_win32\\chromedriver.exe");
	driver=new ChromeDriver(options);
	js=(JavascriptExecutor) driver;
	driver.get(siteUrl);
	
	
}
	
	@AfterEach
	public void afterTest() throws Exception {
		Thread.sleep(1000);
		driver.close();
	}
	
	@Test
	public void test_Dang_Nhap_Thanh_Cong() { // dang nhap thanh cong
		WebElement txtUsername=driver.findElement(By.name("username"));
		WebElement txtPassword= driver.findElement(By.name("password"));
		WebElement btnLogin=driver.findElement(By.className("btn-login"));
		
		
		txtUsername.sendKeys(acclist.get(0).getEmail());
		txtPassword.sendKeys(acclist.get(0).getPassword());
		btnLogin.click();
		WebElement usernameSpan=driver.findElement(By.className("fa-clinic-medical"));
	
		//assertEquals("Quản lý thông tin",usernameSpan.getText());
		Assertions.assertAll("Login Valid", () -> assertEquals("http://localhost:8080/",(String) driver.getCurrentUrl()), // ktra url da chuyen trang chu
				() -> assertEquals("Quản lý thông tin",usernameSpan.getText()));
		
	}
	
	@Test
	public void test_Sai_Ten_Dang_Nhap() {
		WebElement txtUsername=driver.findElement(By.name("username"));
		WebElement txtPassword= driver.findElement(By.name("password"));
		WebElement btnLogin=driver.findElement(By.className("btn-login"));
		
		txtUsername.sendKeys(acclist.get(1).getEmail());
		txtPassword.sendKeys(acclist.get(1).getPassword());
		btnLogin.click();
		WebElement notify=driver.findElement(By.className("alert-danger"));
		assertEquals("Tên đăng nhập/mật khẩu không đúng", notify.getText());
	}
	
	@Test
	public void test_Sai_Mat_Khau() {
		WebElement txtUsername=driver.findElement(By.name("username"));
		WebElement txtPassword= driver.findElement(By.name("password"));
		WebElement btnLogin=driver.findElement(By.className("btn-login"));
		
		txtUsername.sendKeys(acclist.get(2).getEmail());
		txtPassword.sendKeys(acclist.get(2).getPassword());
		btnLogin.click();
		WebElement notify=driver.findElement(By.className("alert-danger"));
		assertEquals("Tên đăng nhập/mật khẩu không đúng", notify.getText());
	}
	
	@Test
	public void test_Nhap_Thieu_Ten_Dang_Nhap() {
		WebElement txtPassword= driver.findElement(By.name("password"));
		WebElement btnLogin=driver.findElement(By.className("btn-login"));
		
		txtPassword.sendKeys(acclist.get(0).getPassword());
		btnLogin.click();
		WebElement elem1 = driver.findElement(By.cssSelector("input:required")); // thông báo "Vui lòng điền vào trường này của input"
		assertEquals(true, elem1.isDisplayed());
	}
	
	@Test
	public void test_Nhap_Thieu_Mat_Khau() {
		WebElement txtUsername=driver.findElement(By.name("username"));
		WebElement btnLogin=driver.findElement(By.className("btn-login"));
		txtUsername.sendKeys(acclist.get(0).getEmail());
		btnLogin.click();
		WebElement elem1 = driver.findElement(By.cssSelector("input:required")); // thông báo "Vui lòng điền vào trường này của input"
		assertEquals(true, elem1.isDisplayed());
	}
	
	@Test
	public void test_UI() {
		WebElement btnLogin=driver.findElement(By.className("btn-login")); // nút đăng nhập
		WebElement RegisterLink= driver.findElement(By.xpath("/html/body/section/div/div/div/div/span/a")); // link đăng ký
		Assertions.assertAll("Login Display", () -> assertEquals(siteUrl, (String) driver.getCurrentUrl()),
				() -> assertEquals("TÀI KHOẢN", driver.findElement(By.xpath("/html/body/section/div/div/div/form/div[1]/label")).getText()),
				() -> assertEquals("username", driver.findElement(By.xpath("/html/body/section/div/div/div/form/div[1]/input")).getAttribute("name")),
				() -> assertEquals("MẬT KHẨU", driver.findElement(By.xpath("/html/body/section/div/div/div/form/div[2]/label")).getText()),
				() -> assertEquals("password", driver.findElement(By.xpath("/html/body/section/div/div/div/form/div[2]/input")).getAttribute("name")),
				() -> assertEquals("Đăng nhập", driver.findElement(By.className("btn-login")).getText()),
				() -> assertEquals(true, Login.isClickable(btnLogin, driver)),
				() -> assertEquals(true, Login.isClickable(RegisterLink, driver) ));
	}
	
	@Test
	public void test_Click_Link_Dang_Ky() {
		WebElement RegisterLink= driver.findElement(By.xpath("/html/body/section/div/div/div/div/span/a"));
		RegisterLink.click();
		assertEquals("http://localhost:8080/registration",(String) driver.getCurrentUrl());
	}
}

