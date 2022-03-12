import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) throws IOException {
		//validate if Add Place API is working as expected
		//given, when, there
		//Add place -> Update place with New Address -> get place to validate if new address in response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\bhpanchal\\OneDrive - MMI HOLDINGS LTD\\Documents\\Workspace\\AddPlace.json")))).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); //for parsing Json
		
		String placeID = js.getString("place_id");
		
		System.out.println(placeID);
		
		//Update Place
		String newAddress = "Summer Walk, Africa ";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").
		when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeID).when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();

		
		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		//Cucumber, Junit , Testng
		Assert.assertEquals(actualAddress, newAddress);
		
		
	}

}
