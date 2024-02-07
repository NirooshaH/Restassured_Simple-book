package Simplebook;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ja {

	public static String BaseUrl="https://simple-books-api.glitch.me";
	public static String token;
	public  static String id;
	@Test(priority = 0)
	public void Authentication()
	{
		RestAssured.baseURI=BaseUrl;
		String requestbody="{\r\n"
				+ "   \"clientName\": \"gfgheayewg\",\r\n"
				+ "   \"clientEmail\": \"ahhhgrershg@example.com\"\r\n"
				+ "}";
		Response res=given()
				.header("Content-Type","application/json")
				.body(requestbody)
				
				.when()
				.post("/api-clients/")
				
				.then()
				.assertThat().statusCode(201)
				.extract().response();
		String jsonsresponse=res.asString();
		System.out.println(jsonsresponse);
		JsonPath js=new JsonPath(jsonsresponse);
		token=js.get("accessToken");
	}
	@Test(priority = 1)
	public void submitanorder() {
		RestAssured.baseURI=BaseUrl;
		String requestbody="{\r\n"
				+ "  \"bookId\": 1,\r\n"
				+ "  \"customerName\": \"tta\"\r\n"
				+ "}";
		Response res=given()
				.header("Content-Type","application/json")
				.body(requestbody)
				.header("Authorization","Bearer "+token)
				
				.when()
				.post("/orders")
				.then()
				.assertThat().statusCode(201)
				.extract().response();
		
		String jsonsep=res.asString();
		System.out.println(jsonsep);
		JsonPath js=new JsonPath(jsonsep);
		 id = js.get("orderId");
	}
	@Test(priority=2)
	public void Updateanorder() {
		RestAssured.baseURI=BaseUrl;
		String reqbody="{\r\n"
				+ "  \"customerName\": \"fga\"\r\n"
				+ "}";
		Response res=given()
				.header("Content-Type","application/json")
				.header("Authorization","Bearer "+token)
				.body(reqbody)
				
				.when()
				.patch("/orders/"+id)
				
				.then()
				.assertThat().statusCode(204)
				.extract().response();
		
	}
	@Test(priority = 3)
	public void deleteanorder() {
		RestAssured.baseURI=BaseUrl;
		Response res=given()
				.header("Content-Type","application/json")
				.header("Authorization","Bearer "+token)
				
				.when()
				.delete("/orders/"+id)
				
				.then()
				.assertThat().statusCode(204)
				.extract().response();
	}
}
