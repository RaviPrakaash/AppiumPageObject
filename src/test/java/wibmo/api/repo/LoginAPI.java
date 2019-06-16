package wibmo.api.repo;

import java.util.HashMap;

import org.testng.Assert;

import com.google.gson.JsonObject;

import library.Generic;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;



public class LoginAPI {

	public static String salt;
	static JsonPath jp;
	static Response res;
	static String OTP , response,pwd, tempToken,userId, userAuthToken, pcAcNumber;
	static HashMap<String, String> authMobMap = new HashMap<String , String>(); //map variable to store mob-auth token mapping
	
	/***
	 * Identifies which data center the request should be served from.
	 * Comes from the getSalt response
	 */
	static String DC;
	
	/***
	 * Login has 5 steps which are called within this method
	 */
	public static void Login(String mobNo , String pin){
		Login_generateSalt(mobNo);
		Login_createPassword(pin);
		login_enterPin(mobNo);
		Login_getOTP(mobNo);
		Login_enterDVC(mobNo);
	}

	public static void Login_generateSalt(String mobNo) {
		Log.info("=======Getting salt & data center information========");
		//RestAssured.baseURI = "https://api.pc.enstage-sas.com";
		RestAssured.baseURI=Generic.getPropValues("RESTDOMAIN", BaseTest1.configPath);
		res = given().param("username", mobNo).when()
				.post("/in/user/auth/6019/salt");

		String r = res.asString();
		jp = new JsonPath(r);
		salt = jp.getJsonObject("salt");
		//System.out.println("Salt response is" + r);

		DC = jp.get("clusterInfo.urls.api");
		//System.out.println("DC is " + DC);
		//System.out.println("Salt is " + salt);
		
	}

	public static void Login_createPassword(String pin) {
		RestAssured.baseURI = Generic.getPropValues("WALLETDOMAIN", BaseTest1.configPath);
		res = given()
				//.log().all()
				.param("password", pin).param("key", salt)
				.when().post("/sampleMerchant/passwordEncrypt.jsp");
		String re = res.asString();
		jp = new JsonPath(re);
		//System.out.println(re);
		pwd = jp.getString("pwd");
		Log.info("======The encrypted password is " + pwd+"======");
	}

	public static void login_enterPin(String mobNo) {
		RestAssured.baseURI = DC;
		res = given()
				.header("Content-Type", "application/json")
				.body(""
						+ "{"
						+ "\"clientInfo\":{"
						+ "\"clientDeviceName\":\"AndriodApp @ motorola XT1225 - Vodafone IN\","
						+ "\"clientMaker\":\"motorola\","
						+ "\"clientModel\":\"XT1225\","
						+ "\"clientOsName\":\"Android\","
						+ "\"clientOsVersion\":\"5.0.2\","
						+ "\"clientDeviceType\":3"
						+ "},"
						+ "\"gpsInfo\":{"
						+ "\"accuracy\":0.0,"
						+ "\"gpsTime\":0,"
						+ "\"latitude\":0.0,"
						+ "\"longitude\":0.0"
						+ "},"
						+ "\"password\":\""
						+ pwd
						+ "\","
						+ "\"salt\":\""
						+ salt
						+ "\","
						+ "\"simAndDeviceData\":{"
						+ "\"deviceData1\":\"zoklBWqgx5agU+s5I7mDMiCFqIKbHgszRuUuJAQ8CXs=\","
						+ "\"deviceData2\":\"DES+n3lIlX2wc6HyTCZrB1CLEn7AMLMoJprQUtchOqc=\","
						+ "\"deviceData3\":\"NeYujrnWV1k3N+dm11rz4OdhI0fSU7T3ejJkgqjsSf8=\","
						+ "\"deviceData4\":\"3uZIZQX5wO9c0ZG+UsyKRYDUri+KHA+zPh5go4yrp4M=\","
						+ "\"deviceId\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"deviceIdProof\":\"UFV4wtZ/gAZ1cQq0j9gzFgi47T1M/NUhPxTNTt5fFgyCV56clBFY1IDkv6tT6JcQGjcpR/Ij+54FO9Wde47IAQ==\","
						+ "\"simId\":\"FL41kzjvztwh6TzyugVKRiLrFUEfFQrE+LTZgEa5wB0=:4555\","
						+ "\"simOperator\":\"Vodafone IN\","
						+ "\"deviceIdType\":3" + "},"
						+ "\"username\":\""+mobNo+"\","
						+ "\"triggerDvcIfReq\":false,"
						+ "\"appVersion\":3020005" + "}")
						//.log().all()
						.when()
				.post("/in/user/auth/6019/login");
		response = res.asString();
		jp = new JsonPath(response);
		tempToken=jp.getString("tempToken");
		userId=jp.getString("userId");
		//System.out.println("Login response is" + response);
		Log.info("=====The temp token is "+ tempToken+"=====");
		Log.info("=====The user ID of "+mobNo+" is "+ userId+"=====");


	}

	public static void Login_getOTP(String mobNo) {
		RestAssured.baseURI = Generic.getPropValues("WALLETDOMAIN", BaseTest1.configPath);
		res = given().param("accessData", mobNo)
				//.log().all()
				.param("programId", "6019").param("eventId", "5").when()
				.post("/sampleMerchant/getOtp.jsp");
		OTP = res.asString().split("\n")[1];
		Log.info("=====The OTP is "+OTP+"======");

	}

	public static void Login_enterDVC(String mobNo) {
		RestAssured.baseURI = DC;
		res=given().header("Content-type", "application/json")
				.body(""
						+ "{"
						+ "\"clientInfo\":{"
						+ "\"clientDeviceName\":\"AndriodApp @ motorola XT1225 - Vodafone IN\","
						+ "\"clientMaker\":\"motorola\","
						+ "\"clientModel\":\"XT1225\","
						+ "\"clientOsName\":\"Android\","
						+ "\"clientOsVersion\":\"5.0.2\","
						+ "\"clientDeviceType\":3"
						+ "},"
						+ "\"dvc\":\""
						+ OTP
						+ "\","
						+ "\"gpsInfo\":{"
						+ "\"accuracy\":0.0,"
						+ "\"gpsTime\":0,"
						+ "\"latitude\":0.0,"
						+ "\"longitude\":0.0"
						+ "},"
						+ "\"simAndDeviceData\":{"
						+ "\"deviceData1\":\"zoklBWqgx5agU+s5I7mDMiCFqIKbHgszRuUuJAQ8CXs=\","
						+ "\"deviceData2\":\"DES+n3lIlX2wc6HyTCZrB1CLEn7AMLMoJprQUtchOqc=\","
						+ "\"deviceData3\":\"NeYujrnWV1k3N+dm11rz4OdhI0fSU7T3ejJkgqjsSf8=\","
						+ "\"deviceData4\":\"3uZIZQX5wO9c0ZG+UsyKRYDUri+KHA+zPh5go4yrp4M=\","
						+ "\"deviceId\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"simId\":\"FL41kzjvztwh6TzyugVKRiLrFUEfFQrE+LTZgEa5wB0=:4555\","
						+ "\"simOperator\":\"Vodafone IN\","
						+ "\"deviceIdType\":3"
						+ "},"
						+ "\"tempToken\":\""
						+ tempToken
						+ "\","
						+ "\"userId\":\""
						+ userId
						+ "\","
						+ "\"username\":\""+mobNo+"\","
						+ "\"trustThisDevice\":true" + "}").when()
				.post("/in/user/auth/6019/completeLoginFromNewDevice");
		response = res.asString();
		jp=new JsonPath(response);
		//System.out.println(response);
		userAuthToken=jp.getString("userToken");
		authMobMap.put(mobNo, userAuthToken); //adding the user auth token mobile no: mapping to map variable
		Log.info("======User auth token is "+userAuthToken+"======");
		pcAcNumber=jp.getString("userBankAc[0].pcAccNumber");
		Log.info("======PC AC NUMBER is "+pcAcNumber+"======");
		
	}

}
