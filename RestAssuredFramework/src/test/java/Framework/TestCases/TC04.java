package Framework.TestCases;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import io.restassured.path.json.JsonPath;

public class TC04 extends TestBase{

	String empName=TestBase.empName();
	String empSal=TestBase.empSal();
	String empAge=TestBase.empAge();
	
    String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC03");
	
	String jsonPath="src/test/java/resources/TC03.json";
	
	public ExtentHtmlReporter htmlReport;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@BeforeSuite
	public void createReport() {
		 htmlReport=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport4.html");
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
	public void beforeTest() throws InterruptedException, IOException {
		
		RestAssured.baseURI=uri;
		 
        httpRequest= RestAssured.given();
        
        byte[] inp=Files.readAllBytes(Paths.get(jsonPath));
        
         String inpValue= new String(inp);
         
        // System.out.println(inpValue);
         
         httpRequest.body(inpValue);
         
         response= httpRequest.request(Method.POST,"/create");
         
         String responseBody= response.getBody().asString();
         
         //System.out.println(responseBody);
         
         int statusCode=response.getStatusCode();
         
         Assert.assertEquals(statusCode, 200);
         
         Thread.sleep(3000);
         
       String id= response.jsonPath().get("data.id").toString();
  	   
  	       // System.out.println(id);
				
		 response= httpRequest.request(Method.GET,"/employee/"+id);
		 
		 Assert.assertEquals(statusCode, 200);
		 
		 Thread.sleep(3000);
		
		response= httpRequest.request(Method.DELETE,"/delete/"+id);
		
		Thread.sleep(3000);
	}
	
	@Test
	public void deleteUser() {
		
		test= extentReports.createTest("TC04");
		
		String responseBody= response.getBody().asString();
			
	//	System.out.println(responseBody);
		
		Assert.assertEquals(responseBody.contains("Successfully! Record has been deleted"), true);
		
		int statusCode=response.getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
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
