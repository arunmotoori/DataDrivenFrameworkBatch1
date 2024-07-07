package tests;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import pageobjects.HomePage;
import pageobjects.RegisterPage;
import util.DataUtil;
import util.MyXLSReader;

public class Register extends Base {
	
	WebDriver driver;
	MyXLSReader myXLSReader = null;
	HomePage homePage;
	RegisterPage registerPage;
	
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
		
	
		driver = openBrowserAndApplication(hmap.get("Browser"));
		
		homePage = new HomePage(driver);
		homePage.clickOnMyAccountDropMenu();
		registerPage = homePage.selectRegisterOption();
		registerPage.enterFirstName(hmap.get("FirstName"));
		registerPage.enterLastName(hmap.get("LastName"));
		registerPage.enterEmailAddress(generateEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(hmap.get("Telephone"));
		registerPage.enterPassword(hmap.get("Password"));
		registerPage.enterConfirmationPassword(hmap.get("PasswordConfirm"));
		registerPage.optForNewsletter();
		registerPage.selectPrivacyPolicy();
		registerPage.clickOnContinueButton();
		
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
		
		loadPropertiesFile();
		String excelPath = System.getProperty("user.dir")+prop.getProperty("excelfilepath");
		
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
	

}
