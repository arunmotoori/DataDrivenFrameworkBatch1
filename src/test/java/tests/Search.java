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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import util.DataUtil;
import util.MyXLSReader;

public class Search extends Base {
	
	WebDriver driver;
	MyXLSReader excelReader = null;
	
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
		
		WebElement searchBoxField = driver.findElement(By.name("search"));
		searchBoxField.sendKeys(hmap.get("SearchText"));
		WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']"));
		searchButton.click();
		
		if(hmap.get("ExpectedResult").equals("Success")) {
	
		   String expectedProduct = hmap.get("ExpectedProduct");
		   Assert.assertEquals(driver.findElement(By.xpath("//h4/a")).getText(), expectedProduct);
			
		}else if(hmap.get("ExpectedResult").equals("Failure")){
			String expectedProduct = hmap.get("ExpectedProduct");
			Assert.assertEquals(driver.findElement(By.xpath("//h2/following-sibling::p")).getText(), expectedProduct);
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
