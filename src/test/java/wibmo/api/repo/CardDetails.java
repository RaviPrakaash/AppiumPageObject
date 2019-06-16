package wibmo.api.repo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CardDetails {
	static Response res;
	static JsonPath jp;
	static String response, corpCardId;
	
	public static void getCorpCard(){
		RestAssured.baseURI=LoginAPI.DC;
		res = given()
				.header("X-Auth-Token",LoginAPI.userAuthToken)
				.header("X-Auth-UID",LoginAPI.userId)
				.when()
				.get("/in/user/linkedcard/6019/"+LoginAPI.pcAcNumber);
		response = res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
		//corpCardId=res.then().extract().jsonPath().;
		//corpCardId=jp.getJsonObject("$..[?(@['cardnumber']==\"4329091207169785\")].id");
		corpCardId=jp.getString("$..[?(@['cardnumber']==\"4329091207169785\")].id");

								 
		System.out.println(corpCardId);
	}

}
