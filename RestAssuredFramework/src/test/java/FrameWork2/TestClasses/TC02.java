package FrameWork2.TestClasses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import FrameWork2.Fixtures.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC02 extends TestBase{

     String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC02");
	
	String jsonPath="src/test/java/resources/TC02.json";
	
	@Before
	public void fetchResult() {
		
       RestAssured.baseURI=uri;
		
	    httpRequest= RestAssured.given();
		
	   response= httpRequest.request(Method.GET);
		
	}
	
	@Test
	public void getResponse() throws IOException {
		
		     
		 byte[] inp=Files.readAllBytes(Paths.get(jsonPath));
	        
         String inpValue= new String(inp);
		
		String responseBody=response.getBody().asString();
		
	
		
		assertEquals(inpValue.contains(responseBody),true);
		
		
		int statusCode=response.getStatusCode();
		
		assertEquals(statusCode, 200);
	}
}
