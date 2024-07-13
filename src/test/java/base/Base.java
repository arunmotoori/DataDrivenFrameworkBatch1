package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Base {
	
	WebDriver driver;
	public Properties prop;
	
	public Base() {
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
	}
	
	public WebDriver openBrowserAndApplication(String browserName) {
				
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
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		
		driver.get(prop.getProperty("url"));
		
		return driver;
		
	}
	
	public String generateEmailWithTimeStamp() {
		
		Date date = new Date();
		return date.toString().replace(" ","_").replace(":","_")+"@gmail.com";
		
	}

}
