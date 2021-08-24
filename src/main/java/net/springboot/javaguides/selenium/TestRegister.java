package net.springboot.javaguides.selenium;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.transaction.annotation.Transactional;

import net.springboot.javaguides.entity.User;
public class TestRegister {
	static String siteUrl="http://localhost:8080/registration";
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
		String sheetName="Register";
		
		File file =new File(filePath+"\\"+fileName);
		FileInputStream inputStream=new FileInputStream(file);
		Workbook workbook= new XSSFWorkbook(inputStream);
		Sheet sheet=workbook.getSheet(sheetName);
		int rowCount =sheet.getLastRowNum()- sheet.getFirstRowNum();
		for(int i=1;i < rowCount + 1;i++) {
			Row row=sheet.getRow(i);
			
			User user=new User();
			user.setFirstName(row.getCell(0).getStringCellValue());
			user.setLastName(row.getCell(1).getStringCellValue());
			user.setEmail(row.getCell(2).getStringCellValue());
			user.setPassword(row.getCell(3).getStringCellValue());
			
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
	public void Valid_Register() {
		WebElement txtFirsname=driver.findElement(By.name("firstName"));
		WebElement txtLastname=driver.findElement(By.name("lastName"));
		WebElement txtUsername=driver.findElement(By.name("email"));
		WebElement txtPassword=driver.findElement(By.name("password"));
		WebElement txtConfirmPass=driver.findElement(By.name("confirm_password"));
		WebElement btnRegister=driver.findElement(By.id("submit"));
		
		
		txtFirsname.sendKeys(acclist.get(0).getFirstName());
		txtLastname.sendKeys(acclist.get(0).getFirstName());
		txtUsername.sendKeys(acclist.get(0).getEmail());
		txtPassword.sendKeys(acclist.get(0).getPassword());
		txtConfirmPass.sendKeys(acclist.get(0).getPassword());
		
		btnRegister.click();
		WebElement notify=driver.findElement(By.className("alert-success"));
		assertEquals("Đăng kí thành công, Đăng nhập", notify.getText()); 
		//return false;
	}
	
	
}
