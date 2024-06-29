package tests;

import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Register {
	
	WebDriver driver;
	
	@AfterMethod
	public void tearDown() {
		
		if(driver!=null)
			driver.quit();
		
	}
	
	@Test
	public void verifyRegisterFunctionality() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		driver.get("https://tutorialsninja.com/demo");
		WebElement myAccountDropMenu = driver.findElement(By.xpath("//span[text()='My Account']"));
		myAccountDropMenu.click();
		WebElement registerOption = driver.findElement(By.linkText("Register"));
		registerOption.click();
		WebElement firstNameField = driver.findElement(By.id("input-firstname"));
		firstNameField.sendKeys("Arun");
		WebElement lastNameField = driver.findElement(By.id("input-lastname"));
		lastNameField.sendKeys("Motoori");
		WebElement emailField = driver.findElement(By.id("input-email"));
		emailField.sendKeys(generateEmailWithTimeStamp());
		WebElement telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.sendKeys("1234567890");
		WebElement passwordField = driver.findElement(By.id("input-password"));
		passwordField.sendKeys("abcde");
		WebElement passwordConfirmField = driver.findElement(By.id("input-confirm"));
		passwordConfirmField.sendKeys("abcde");
		WebElement yesNewsletterOption = driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']"));
		yesNewsletterOption.click();
		WebElement privacyPolicyOption = driver.findElement(By.name("agree"));
		privacyPolicyOption.click();
		WebElement contineButton = driver.findElement(By.xpath("//input[@value='Continue']"));
		contineButton.click();
		
		String expectedPageTitle = "Your Account Has Been Created!";
		
		Assert.assertEquals(driver.getTitle(),expectedPageTitle);
		
	}
	
	public String generateEmailWithTimeStamp() {
		
		Date date = new Date();
		return date.toString().replace(" ","_").replace(":","_")+"@gmail.com";
		
	}

}
