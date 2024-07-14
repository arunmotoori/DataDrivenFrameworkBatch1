package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Utilities {
	
	public static String captureScreeshot(WebDriver driver,String testName) {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String screenshotFilePath = System.getProperty("user.dir")+"\\screenshots\\"+testName+".png";
		
		try {
			FileHandler.copy(srcFile,new File(screenshotFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return screenshotFilePath;
	}
	
	public static Properties loadPropertiesFile() {
		
		Properties prop = null;
		
		try {
			prop = new Properties();
			File propFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\projectdata.properties");
			FileReader fr = new FileReader(propFile);
			prop.load(fr);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
}
