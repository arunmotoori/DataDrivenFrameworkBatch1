package listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

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
import util.Utilities;

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
		
		String screenshotFilePath = Utilities.captureScreeshot(driver,result.getName());
		
		extentTest.addScreenCaptureFromPath(screenshotFilePath);
		
		extentTest.log(Status.INFO, result.getThrowable());
		
		extentTest.fail(result.getName()+" test got failed");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.log(Status.INFO, result.getThrowable());
		extentTest.skip(result.getName()+" test got skipped");
	}

	
	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
		try {
			Properties prop = Utilities.loadPropertiesFile();
			String extentReportFilePath = System.getProperty("user.dir")+prop.getProperty("extentreportpath");
			File extentReportFile = new File(extentReportFilePath);
			Desktop.getDesktop().browse(extentReportFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
