package Framework.BaseTest;


import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentReportsGenerate {
	
	public ExtentHtmlReporter htmlReport;
    public ExtentTest test;
    public ExtentReports extentReports;
    public ITestResult result;

	 @BeforeSuite
	    public void beforeSuite() {
	    	 htmlReport=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport.html");
	    	htmlReport.config().setDocumentTitle("Automation Report");
	    	htmlReport.config().setReportName("functional Report");
	    	htmlReport.config().setTheme(Theme.DARK);
	    	 	extentReports=new ExtentReports();
	    	    	extentReports.attachReporter(htmlReport);
	    	    	extentReports.setSystemInfo("HostName", "LocalHost");
	    	    	extentReports.setSystemInfo("OS", "Windows");
	    	    	extentReports.setSystemInfo("TesterName", "Kiran");
	    	    	extentReports.setSystemInfo("Browser", "Chrome");
	 
	    }
	 
	 @AfterMethod
	    public void getResult() throws IOException {
	    	if(result.getStatus()==ITestResult.FAILURE) {
	    		test.log(Status.FAIL, "TEST CASE FAILED IS :" +result.getName());
	    		test.log(Status.FAIL, "TEST CASE FAILED IS :" +result.getThrowable());
	    		
	    		
	    		

	    	}
	    	else if(result.getStatus()==ITestResult.SKIP) {
	    		test.log(Status.SKIP, "TEST CASE SKIPPED IS :" +result.getName());
	    	}
	    	else if(result.getStatus()==ITestResult.SUCCESS) {
	    		test.log(Status.PASS, "TEST CASE SUCCESS IS :" +result.getName());
	    	}
	    }
	    
	 @AfterSuite
	    public void afterSuite() {
	    	
	    	extentReports.flush();
	    }  

	 
}
