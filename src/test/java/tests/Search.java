package tests;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import pageobjects.HomePage;
import pageobjects.SearchResultsPage;
import util.DataUtil;
import util.MyXLSReader;

public class Search extends Base {
	
	WebDriver driver;
	MyXLSReader excelReader = null;
	HomePage homePage;
	SearchResultsPage searchResultsPage;
	
	@AfterMethod
	public void tearDown() {
		
		if(driver!=null)
			driver.quit();
	}
	
	
	@Test(dataProvider="searchDataProvider")
	public void verifySearchFunctionality(HashMap<String,String> hmap) {
		
		if(!DataUtil.isRunnable(excelReader,"SearchTest","testcases") || hmap.get("Runmode").equals("N")) {
			throw new SkipException("Runmode is set to N, hence test got skipped");
		}
		
		driver = openBrowserAndApplication(hmap.get("Browser"));
		
		homePage = new HomePage(driver);
		homePage.enterSearchText(hmap.get("SearchText"));
		searchResultsPage = homePage.clickOnSearchButton();
	
		if(hmap.get("ExpectedResult").equals("Success")) {
	
		   String expectedProduct = hmap.get("ExpectedProduct");
		   Assert.assertEquals(searchResultsPage.getResultedProductName(), expectedProduct);
			
		}else if(hmap.get("ExpectedResult").equals("Failure")){
			String expectedProduct = hmap.get("ExpectedProduct");
			Assert.assertEquals(searchResultsPage.getResultedMessage(), expectedProduct);
		}
	
	}
	
	@DataProvider(name="searchDataProvider")
	public Object[][] supplySearchData() {
		
		loadPropertiesFile();
		
		String excelFilePath = System.getProperty("user.dir")+prop.getProperty("excelfilepath");
		
		try {
			excelReader = new MyXLSReader(excelFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[][] data = null;
		
		try {
			data = DataUtil.getTestData(excelReader,"SearchTest","data");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
		
	}

}
