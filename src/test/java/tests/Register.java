package tests;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;
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

public class Register {
	
	WebDriver driver;
	MyXLSReader myXLSReader = null;
	
	@AfterMethod
	public void tearDown() {
		
		if(driver!=null)
			driver.quit();
		
	}
	
	@Test(dataProvider="registerDataProvider")
	public void verifyRegisterFunctionality(HashMap<String,String> hmap) {
		
		if(!DataUtil.isRunnable(myXLSReader,"RegisterTest","testcases") || hmap.get("Runmode").equals("N")) {
			throw new SkipException("Run mode is set N, hence test got skipped");
		}
		
		String browserName = hmap.get("Browser");
		
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
		driver.get("https://tutorialsninja.com/demo");
		WebElement myAccountDropMenu = driver.findElement(By.xpath("//span[text()='My Account']"));
		myAccountDropMenu.click();
		WebElement registerOption = driver.findElement(By.linkText("Register"));
		registerOption.click();
		WebElement firstNameField = driver.findElement(By.id("input-firstname"));
		firstNameField.sendKeys(hmap.get("FirstName"));
		WebElement lastNameField = driver.findElement(By.id("input-lastname"));
		lastNameField.sendKeys(hmap.get("LastName"));
		WebElement emailField = driver.findElement(By.id("input-email"));
		emailField.sendKeys(generateEmailWithTimeStamp());
		WebElement telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.sendKeys(hmap.get("Telephone"));
		WebElement passwordField = driver.findElement(By.id("input-password"));
		passwordField.sendKeys(hmap.get("Password"));
		WebElement passwordConfirmField = driver.findElement(By.id("input-confirm"));
		passwordConfirmField.sendKeys(hmap.get("PasswordConfirm"));
		WebElement yesNewsletterOption = driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']"));
		yesNewsletterOption.click();
		WebElement privacyPolicyOption = driver.findElement(By.name("agree"));
		privacyPolicyOption.click();
		WebElement contineButton = driver.findElement(By.xpath("//input[@value='Continue']"));
		contineButton.click();
		
		String expectedResult = hmap.get("ExpectedResult");
		
		String expectedPageTitle = "Your Account Has Been Created!";
		
		if(expectedResult.equals("Success")) {
			Assert.assertEquals(driver.getTitle(),expectedPageTitle);
		}else if(expectedResult.equals("Failure")) {
			Assert.assertEquals(driver.getTitle(),"Register Account");
		}
		

	}
	
	@DataProvider(name="registerDataProvider")
	public Object[][] registerDataSupplierMethod() {
		
		String excelPath = System.getProperty("user.dir")+"\\src\\test\\resources\\TutorialsNinja.xlsx";
		
		try {
			myXLSReader = new MyXLSReader(excelPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[][] data = null;

		try {
			data = DataUtil.getTestData(myXLSReader,"RegisterTest","data");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
		
	}
	
	public String generateEmailWithTimeStamp() {
		
		Date date = new Date();
		return date.toString().replace(" ","_").replace(":","_")+"@gmail.com";
		
	}

}
