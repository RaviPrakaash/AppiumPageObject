package wibmo.api.repo;

import library.Log;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class GetBalanceAPI {
	static Response res;
	static String response;
	public static int availableBalance;
	static JsonPath jp;
	
	
	public static void balanceFetch(){
		RestAssured.baseURI=LoginAPI.DC;
		res = given().
				//.log().all().
				header("X-Auth-Token",LoginAPI.userAuthToken).
				header("X-Auth-UID",LoginAPI.userId).
				when().
				get("/in/user/balance/6019/"+LoginAPI.pcAcNumber);
		response=res.asString();
		jp=new JsonPath(response);
		//System.out.println(response);
		availableBalance=Integer.parseInt(jp.getString("availableBalance"));
		Log.info("======Balance is "+availableBalance+"======");
				
	}

}
