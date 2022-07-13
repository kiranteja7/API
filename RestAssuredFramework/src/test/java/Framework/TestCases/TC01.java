package Framework.TestCases;

import java.io.IOException;


import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Framework.BaseTest.ExtentReportsGenerate;
import Framework.BaseTest.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;



public class TC01 extends TestBase{
	
	
	
	String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC01");
	

	public ExtentHtmlReporter htmlReport;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@BeforeSuite
	public void createReport() {
		 htmlReport=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport.html");
	    	htmlReport.config().setDocumentTitle("API REPORT");
	    	htmlReport.config().setReportName("TEST CONDITION 001");
	    	htmlReport.config().setTheme(Theme.DARK);
	    	 	extentReports=new ExtentReports();
	    	    	extentReports.attachReporter(htmlReport);
	    	    	extentReports.setSystemInfo("HostName", "LocalHost");
	    	    	extentReports.setSystemInfo("OS", "Windows");
	    	    	extentReports.setSystemInfo("TesterName", "Kiran");
	    	    	extentReports.setSystemInfo("Browser", "Chrome");
	}
	
	
	@BeforeClass
	public void fetchDetails() throws InterruptedException {
		
		System.out.println(uri);
		
        RestAssured.baseURI=uri;	
		
		 httpRequest= RestAssured.given();
		
		 response= httpRequest.request(Method.GET);
		 
		 Thread.sleep(3);
	}

	@Test
	public void getRequest() {
			
		test= extentReports.createTest("TC01");
		
		String responseBody=response.getBody().asString();
		
		
		System.out.println("response message is" +responseBody);
		
		
		
		int statusCode=response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
		
	}
	
	@AfterMethod
	public void getrequestResult(ITestResult result) throws IOException {
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
	
	@AfterClass
	public void tearDown() {
		extentReports.flush();
	}
}
