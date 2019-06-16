package wibmo.api.repo;

import java.util.HashMap;
import org.testng.Assert;
import com.google.gson.JsonObject;
import library.DB;
import library.Generic;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;



public class BlockUnblockWalletAPI {
	static JsonPath jp;
	static Response res;
	static String Response;
	
	/***
	 * Method to credit block a wallet due to non-compliance
	 */
	public static void creditBlockWallet(String mobileNo) {
		String pcACNumber = DB.getPCACNumber(mobileNo);
		String urn = DB.getURN(mobileNo);
		RestAssured.baseURI = Generic.getPropValues("RESTDOMAIN", BaseTest1.configPath);
		
		Log.info("=====API call to credit block " + mobileNo + "=====");
		try {
			res = given()
					.header("Content-Type", "application/json")
					.body("{" + "\"pcAcNum\":\"" + pcACNumber + "\"," + "\"urn\":\""+urn+"\","
							+ "\"reasonCode\":\"1002\"," + "\"walletStatus\":\"110\"" + "}")
					.when()
					.post("/in/user/auth/6019/walletStatus/update");
			
			Response = res.asString();
			jp = new JsonPath(Response);
			
			Log.info("Response of block wallet API call " + Response);
			Assert.assertEquals(jp.getString("resDesc"), "SUCCESS");
			
		} catch (Exception e) {
			Assert.fail("=====Unable to credit block wallet=====");
		}
		
	}
	
	/***
	 * Method to activate wallet
	 */
	public static void activateWallet(String mobileNo) {
		String pcACNumber = DB.getPCACNumber(mobileNo);
		RestAssured.baseURI = Generic.getPropValues("RESTDOMAIN", BaseTest1.configPath);
		Log.info("=====API call to activate wallet of " + mobileNo + "=====");
		try {
			res = given()
					.header("x-api-key", "TEST")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.body("walletStatus=active")
					.when()
					.post("/in/user/auth/6019/accoStatus/"+pcACNumber+"/");
			Response = res.asString();
			jp = new JsonPath(Response);
			Log.info("Response of activate wallet API call " + Response);
			Assert.assertEquals(jp.getString("resDesc"), "SUCCESS");
		} catch (Exception e) {
			Assert.fail("=====Unable to activate wallet=====");
		}
		
	}
	
	/***
	 * Method to block a wallet due to inactivity
	 */
	public static void inactivityBlockWallet(String mobileNo) 
	{
		String pcACNumber = DB.getPCACNumber(mobileNo); 
		String urn = DB.getURN(mobileNo);
		RestAssured.baseURI = Generic.getPropValues("RESTDOMAIN", BaseTest1.configPath);
		
		Log.info("=====API call to inactivity block " + mobileNo + "=====");
		try {
			res = given()
					.header("Content-Type", "application/json")
					.body("{" + "\"pcAcNum\":\"" + pcACNumber + "\"," + "\"urn\":\""+urn+"\","
							+ "\"reasonCode\":\"1003\"," + "\"walletStatus\":\"120\"" + "}")
					.when()
					.post("/in/user/auth/6019/walletStatus/update");
			
			Response = res.asString();
			jp = new JsonPath(Response);
			
			Log.info("Response of block wallet API call " + Response);
			Assert.assertEquals(jp.getString("resDesc"), "SUCCESS");
			
		} catch (Exception e) { Assert.fail("=====Unable to block wallet due to inactivity=====");}
		
	}

	

}
