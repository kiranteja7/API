package FrameWork2.TestClasses;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import FrameWork2.Fixtures.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC04 extends TestBase{

	
	String empName=TestBase.empName();
	String empSal=TestBase.empSal();
	String empAge=TestBase.empAge();
	
    String propFile="src/test/java/resources/base.prop";
	
	String uri=Utils.DataProvider.getTestData(propFile,"TC03");
	
	String jsonPath="src/test/java/resources/TC03.json";
	
	
	@Before
	public void beforeTest() throws InterruptedException, IOException {
		
		RestAssured.baseURI=uri;
		 
        httpRequest= RestAssured.given();
        
        byte[] inp=Files.readAllBytes(Paths.get(jsonPath));
        
         String inpValue= new String(inp);
         

         
         httpRequest.body(inpValue);
         
         response= httpRequest.request(Method.POST,"/create");
         
         String responseBody= response.getBody().asString();
         

         
         int statusCode=response.getStatusCode();
         
         assertEquals(statusCode, 200);
         
         Thread.sleep(3000);
         
       String id= response.jsonPath().get("data.id").toString();
  	   
  	
				
		 response= httpRequest.request(Method.GET,"/employee/"+id);
		 
		 assertEquals(statusCode, 200);
		 
		 Thread.sleep(3000);
		
		response= httpRequest.request(Method.DELETE,"/delete/"+id);
		
		Thread.sleep(3000);
	}
	
	@Test
	public void deleteUser() {
		
		
		String responseBody= response.getBody().asString();
			

		
		assertEquals(responseBody.contains("Successfully! Record has been deleted"), true);
		
		int statusCode=response.getStatusCode();
		
		assertEquals(statusCode, 200);
	}
}
