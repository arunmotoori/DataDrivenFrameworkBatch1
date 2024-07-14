package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	
	public static ExtentReports getExtentReport() {
		
		ExtentReports extentReport = new ExtentReports();
		Properties prop = Utilities.loadPropertiesFile();
		String extentReportFilePath = System.getProperty("user.dir")+prop.getProperty("extentreportpath");
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFilePath);
		sparkReporter.config().setReportName("DDF - Extent Report Demo Name");
		sparkReporter.config().setDocumentTitle("DDF - Extent Report Demo Title");
		
		extentReport.attachReporter(sparkReporter);
		extentReport.setSystemInfo("Operating System",System.getProperty("os.name"));
		extentReport.setSystemInfo("Java Version",System.getProperty("java.version"));
		extentReport.setSystemInfo("Selenium Java Version","4.22.0");
		extentReport.setSystemInfo("Exectued By",System.getProperty("user.name"));
		
		return extentReport;
		
	}

}
