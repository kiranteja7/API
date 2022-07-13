package Framework.TestCases;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Framework.BaseTest.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;


public class TC02 extends TestBase{
String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC02");
	//String responseMsg=Utils.Helper.readFromExcel(path,"Sheet1",3,6);
	String jsonPath="src/test/java/resources/TC02.json";
	
	public ExtentHtmlReporter htmlReport;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@BeforeSuite
	public void createReport() {
		 htmlReport=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport2.html");
	    	htmlReport.config().setDocumentTitle("API REPORT");
	    	htmlReport.config().setReportName("TEST CONDITION 002");
	    	htmlReport.config().setTheme(Theme.DARK);
	    	 	extentReports=new ExtentReports();
	    	    	extentReports.attachReporter(htmlReport);
	    	    	extentReports.setSystemInfo("HostName", "LocalHost");
	    	    	extentReports.setSystemInfo("OS", "Windows");
	    	    	extentReports.setSystemInfo("TesterName", "Kiran");
	    	    	extentReports.setSystemInfo("Browser", "Chrome");
	}
	
	@BeforeTest
	public void fetchResult() {
		
       RestAssured.baseURI=uri;
		
	    httpRequest= RestAssured.given();
		
	   response= httpRequest.request(Method.GET);
		
	}
	
	@Test
	public void getResponse() throws IOException {
		
		test= extentReports.createTest("TC02");
		     
		 byte[] inp=Files.readAllBytes(Paths.get(jsonPath));
	        
         String inpValue= new String(inp);
		
		String responseBody=response.getBody().asString();
		
	
		
		Assert.assertEquals(inpValue.contains(responseBody),true);
		
		
		int statusCode=response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
	}
	
	@AfterMethod
	public void getrequestResults(ITestResult result) throws IOException {
		
		result = Reporter.getCurrentTestResult();
		if(result.getStatus()==ITestResult.FAILURE) {
    		test.log(Status.FAIL, "TEST CASE FAILED IS :" +result.getName());
    		test.log(Status.FAIL, "TEST CASE FAILED IS :" +result.getThrowable());  				

    	}
    	else if(result.getStatus()==ITestResult.SKIP) {
    		test.log(Status.SKIP, "TEST CASE SKIPPED IS :" +result.getName());
    	}
    	else if(result.getStatus()==ITestResult.SUCCESS) {
    		test.log(Status.PASS, "TEST CASE SUCCESS  :");
    	}
	}
	
	@AfterClass
	public void tearDown() {
		extentReports.flush();
	}

}
