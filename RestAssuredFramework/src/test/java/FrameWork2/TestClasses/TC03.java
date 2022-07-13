package FrameWork2.TestClasses;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import FrameWork2.Fixtures.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC03 extends TestBase{

	String empName=TestBase.empName();
	String empSal=TestBase.empSal();
	String empAge=TestBase.empAge();
	
    String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC03");
	
	
	@Before
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
	    

		
		String responseBody= response.getBody().asString();
		
		assertEquals(responseBody.contains(empName), true);
		assertEquals(responseBody.contains(empSal), true);
		assertEquals(responseBody.contains(empAge), true);
		
		Thread.sleep(5000);
		
		int statusCode= response.getStatusCode();
		
		assertEquals(statusCode, 200);
		
		String successMessage= response.jsonPath().get("message");
		
		assertEquals(successMessage, "Successfully! Record has been added.");
	}
}
