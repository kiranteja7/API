package Framework.TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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


public class TC03 extends TestBase{
	
	String empName=TestBase.empName();
	String empSal=TestBase.empSal();
	String empAge=TestBase.empAge();
	
    String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC03");
	//String responseMsg=Utils.Helper.readFromExcel(path,"Sheet1",3,6);
	
	public ExtentHtmlReporter htmlReport;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@BeforeSuite
	public void createReport() {
		 htmlReport=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport3.html");
	    	htmlReport.config().setDocumentTitle("API REPORT");
	    	htmlReport.config().setReportName("TEST CONDITION 003");
	    	htmlReport.config().setTheme(Theme.DARK);
	    	 	extentReports=new ExtentReports();
	    	    	extentReports.attachReporter(htmlReport);
	    	    	extentReports.setSystemInfo("HostName", "LocalHost");
	    	    	extentReports.setSystemInfo("OS", "Windows");
	    	    	extentReports.setSystemInfo("TesterName", "Kiran");
	    	    	extentReports.setSystemInfo("Browser", "Chrome");
	}
	
	
	@BeforeTest
	public void sendPost() throws IOException {
		
        RestAssured.baseURI=uri;
		
	    httpRequest= RestAssured.given();
	       
         JSONObject requestParams= new JSONObject();
		
		requestParams.put("name", empName);
		requestParams.put("salary", empSal);
		requestParams.put("age", empAge);
		
		httpRequest.header("Content-type","application/json");
		
		httpRequest.body(requestParams.toString());
		
	    response= httpRequest.request(Method.POST,"/create");
	}

	@Test
	public void postRequest() throws InterruptedException {			
	    
		test= extentReports.createTest("TC03");
		
		String responseBody= response.getBody().asString();
		
		Assert.assertEquals(responseBody.contains(empName), true);
		Assert.assertEquals(responseBody.contains(empSal), true);
		Assert.assertEquals(responseBody.contains(empAge), true);
		
		Thread.sleep(5000);
		
		int statusCode= response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
		
		String successMessage= response.jsonPath().get("message");
		
		Assert.assertEquals(successMessage, "Successfully! Record has been added.");
	}
	
	@AfterMethod
	public void getrequestResult(ITestResult result) throws IOException {
		
		result = Reporter.getCurrentTestResult();
		
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
	
