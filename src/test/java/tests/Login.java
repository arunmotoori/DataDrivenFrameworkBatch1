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
import pageobjects.AccountPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import util.DataUtil;
import util.MyXLSReader;

public class Login extends Base {
	
	public WebDriver driver;
	MyXLSReader myXLSReader = null;
	HomePage homePage;
	LoginPage loginPage;
	AccountPage accountPage;
	
	public Login() {
		super();
	}
	
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
		
		driver = openBrowserAndApplication(hMap.get("Browser"));
		
		homePage = new HomePage(driver);
		homePage.clickOnMyAccountDropMenu();
		loginPage =homePage.selectLoginOption();
		loginPage.enterEmailAddress(hMap.get("Username"));
		loginPage.enterPassword(hMap.get("Password"));
		accountPage = loginPage.clickOnLoginButton();
		
		if(hMap.get("ExpectedResult").equals("Success")) {
			Assert.assertTrue(accountPage.loginStatus());
		}else if(hMap.get("ExpectedResult").equals("Failure")){
			Assert.assertFalse(loginPage.loginWarningDisplayStatus());
		}
	}
	
	@DataProvider(name="dataSupplierOne")
	public Object[][] supplyTestData() {
		
//		Object[][] data = {{"arunbatch1@gmail.com","abcde"},
//				{"arunbatch2@gmail.com","xyzab"},
//				{"arunbatch3@gmail.com","mnopq"}};
		
		String xlsxFilePath = System.getProperty("user.dir")+prop.getProperty("excelfilepath");
		
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
