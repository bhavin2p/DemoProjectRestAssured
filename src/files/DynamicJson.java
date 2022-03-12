package files;

import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "getData")
	public void addBook(String ibna, String asiel) {
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().header("Content-Type", "application/json").
		body(payload.Addbook(ibna, asiel)).
		when().post("/Library/Addbook.php")
		.then().log().all().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		String ID = js.getString("ID");
		System.out.println(ID);
		
		/*
		//delete Book
		String cont = ibna+asiel;
		given().header("Content-Type", "application/json").
		body(payload.Deletebook(cont)).
		when().delete("/Library/DeleteBook.php")
		.then().log().all().statusCode(200)
		.extract().response().asString();
	*/
	}
	
	@DataProvider
	public Object[][] getData(){
		
		return new Object[][] {
			{"Book1 raj", "9362"},
			{"Book2 kulsum", "2703"},
			{"Book3 bhavin", "1410"}
		};
		
	}
	
}
