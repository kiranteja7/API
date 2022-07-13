package FrameWork2.TestClasses;

import static org.junit.Assert.*;



import org.junit.Before;

import org.junit.Test;

import FrameWork2.Fixtures.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC01 extends TestBase{

    String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC01");
	
	@Before
	public void beforeClass() {
       System.out.println(uri);
		
        RestAssured.baseURI=uri;	
		
		 httpRequest= RestAssured.given();
		
		 response= httpRequest.request(Method.GET);
		 
	}
	
	@Test
	public void getRequest() {
		
		
		String responseBody=response.getBody().asString();
		
		
		System.out.println("response message is" +responseBody);
			
		int statusCode=response.getStatusCode();
		
		assertEquals(statusCode,200);
		
	}
	
}
