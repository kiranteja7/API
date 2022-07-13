package Framework.BaseTest;

import org.apache.commons.lang3.RandomStringUtils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {

	public  RequestSpecification httpRequest;
	public  Response response;
	public String empId="1";
	
	public static String empName() {
		String generateName= RandomStringUtils.randomAlphabetic(1);
		return ("kiran"+generateName);
	}
	
	public static String empSal() {
		String generateSal= RandomStringUtils.randomNumeric(5);
		return (generateSal);
	}
	
	public static String empAge() {
		String generateAge= RandomStringUtils.randomNumeric(5);
		return (generateAge);
	}
	
}
