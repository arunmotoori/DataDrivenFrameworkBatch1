package listeners;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import util.ExtentReporter;

public class MyListeners implements ITestListener {
	
	ExtentReports extentReport = null;
	ExtentTest extentTest = null;
	
	@Override
	public void onStart(ITestContext context) {
		extentReport = ExtentReporter.getExtentReport();
	}

	@Override
	public void onTestStart(ITestResult result) {
		extentTest = extentReport.createTest(result.getName());
		extentTest.log(Status.INFO,result.getName()+" test execution started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.pass(result.getName()+" test got passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		WebDriver driver = null;
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String screenshotFilePath = System.getProperty("user.dir")+"\\screenshots\\"+result.getName()+".png";
		
		try {
			FileHandler.copy(srcFile,new File(screenshotFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		extentTest.addScreenCaptureFromPath(screenshotFilePath);
		
		extentTest.fail(result.getName()+" test got failed");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.skip(result.getName()+" test got skipped");
	}

	
	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}
	
}
