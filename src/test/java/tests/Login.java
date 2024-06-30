package tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.DataUtil;
import util.MyXLSReader;

public class Login {
	
	WebDriver driver;
	MyXLSReader myXLSReader = null;
	
	@AfterMethod
	public void tearDown() {
		
		if(driver!=null) {
			driver.quit();
		}
		
	}
	
	@Test(dataProvider="dataSupplierOne")
	public void verifyLoginFunctionality(HashMap<String,String> hMap) {
	
		if(!DataUtil.isRunnable(myXLSReader,"LoginTest","testcases") || hMap.get("Runmode").equals("N")) {
			
			throw new SkipException("Run mode is set to N in excel file, hence not executed");
			
		}
		
		String browserName = hMap.get("Browser");
		
		if(browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}else if(browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}else if(browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}else if(browserName.equalsIgnoreCase("ie")) {
			driver = new InternetExplorerDriver();
		}else if(browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.get("https://tutorialsninja.com/demo/");
		WebElement myAccountDropMenu = driver.findElement(By.xpath("//span[text()='My Account']"));
		myAccountDropMenu.click();
		WebElement loginOption = driver.findElement(By.linkText("Login"));
		loginOption.click();
		WebElement emailField = driver.findElement(By.id("input-email"));
		emailField.sendKeys(hMap.get("Username"));
		WebElement passwordField = driver.findElement(By.id("input-password"));
		passwordField.sendKeys(hMap.get("Password"));
		WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
		loginButton.click();
		
		if(hMap.get("ExpectedResult").equals("Success")) {
			Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed());
		}else if(hMap.get("ExpectedResult").equals("Failure")){
			Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).isDisplayed());
		}
	}
	
	@DataProvider(name="dataSupplierOne")
	public Object[][] supplyTestData() {
		
//		Object[][] data = {{"arunbatch1@gmail.com","abcde"},
//				{"arunbatch2@gmail.com","xyzab"},
//				{"arunbatch3@gmail.com","mnopq"}};
		
		String xlsxFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\TutorialsNinja.xlsx";
		
		try {
			myXLSReader = new MyXLSReader(xlsxFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[][] data = null;
		
		try {
			data = DataUtil.getTestData(myXLSReader,"LoginTest","data");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
		
	}
	
}
