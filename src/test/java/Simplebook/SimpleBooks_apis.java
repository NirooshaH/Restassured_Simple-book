package Simplebook;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
//import org.testng.annotations.Test;
//import io.restassured.RestAssured;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;

public class SimpleBooks_apis {

	public static String BaseUrl="https://simple-books-api.glitch.me";
	public static String token;
	public static String id;
	
	
	@Test(priority = 1)
	public  void Authenticate(){
		
		RestAssured.baseURI = BaseUrl;
				
		String requestebody="{\r\n"
				+ "   \"clientName\": \"Niru00220\",\r\n"
				+ "   \"clientEmail\": \"niru786@example.com\"\r\n"
				+ "}";
		Response res=given()
				.header("Content-Type","application/json")
				.body(requestebody)
				
				.when()
				.post("/api-clients/")
				
				.then()
				.assertThat().statusCode(201)
				.extract().response();
		
		
		
		String jsonresponse=res.asString();
		System.out.println(jsonresponse);
		JsonPath js=new JsonPath(jsonresponse);
		token=js.get("accessToken");			
	}
	@Test(priority = 2)
	public void Submitanorder() {
		RestAssured.baseURI=BaseUrl;
		String reqbody=" \r\n"
				+ "{\r\n"
				+ "  \"bookId\": 6,\r\n"
				+ "  \"customerName\": \"hjheuigc\"\r\n"
				+ "}";
		Response res=given()
				.header("Content-Type", "application/json")
				.body(reqbody)
				.header("Authorization","Bearer "+token)
				
				.when()
				.post("/orders")
				
				.then()
				.assertThat().statusCode(201)
				.extract().response();
		
		String jsonresponse=res.asString();
		System.out.println(jsonresponse);
		JsonPath js=new JsonPath(jsonresponse);
		id=js.get("orderId");	
	}	
	@Test (priority = 3)
	public void Updateanorder() {
		RestAssured.baseURI=BaseUrl;
		String reqbody="{\r\n"
				+ "  \"customerName\": \"niroo23\"\r\n"
				+ "}";
		Response res=given()
				.header("Content-Type", "application/json")
				.header("Authorization","Bearer "+token)
				.body(reqbody)
				
				.when()
				.patch("/orders/"+id)
				
				.then()
				.assertThat().statusCode(204)
				.extract().response();
	
}
	  @Test(priority = 4)
	  public void Deleteanorder() {
		  RestAssured.baseURI=BaseUrl;
		  Response responsbody=given()
				  .header("Content-Type","application/json")
				  .header("Authorization","Bearer "+token)
				 
				  .when()
				  .delete("/orders/"+id)
				  
				  .then()
				  .assertThat().statusCode(204)
				  .extract().response();
	  }
}
