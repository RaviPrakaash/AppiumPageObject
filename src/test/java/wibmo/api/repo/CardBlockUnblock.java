package wibmo.api.repo;

import org.testng.Assert;

import library.Log;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 *  Used to lock/unlock the PayZapp Wallet Card through API
 * 
 *
 */
public class CardBlockUnblock {
	
	static Response res;
	static String response;
	static JsonPath jp;
	public static void block(){
		RestAssured.baseURI=LoginAPI.DC;
		res = given().
				header("content-type", "application/x-www-form-urlencoded").
				header("X-Auth-Token",LoginAPI.userAuthToken).
				header("X-Auth-UID",LoginAPI.userId).
				body("type=0").
				when().
				post("/in/am/wibmocard/6019/"+LoginAPI.pcAcNumber+"/blockunblock");
		response = res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
		Assert.assertEquals("{\"resCode\":\"000\",\"resDesc\":\"SUCCESS\"}", response);
		Log.info("======Card is blocked successfully======");
		
		
	}
	
	public static void unblock(){
		RestAssured.baseURI=LoginAPI.DC;
		res = given().
				header("content-type", "application/x-www-form-urlencoded").
				header("X-Auth-Token",LoginAPI.userAuthToken).
				header("X-Auth-UID",LoginAPI.userId).
				body("type=1").
				when().
				post("/in/am/wibmocard/6019/"+LoginAPI.pcAcNumber+"/blockunblock");
		response = res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
		Assert.assertEquals("{\"resCode\":\"000\",\"resDesc\":\"SUCCESS\"}", response);
		Log.info("======Card is unblocked successfully======");
		
		
	}

}
