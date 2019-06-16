package wibmo.api.repo;

import com.libraries.Log;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class LogoutAPI {
	static Response res;
	static String response;
	
	
	
	public static synchronized boolean logout(String mobNo){
		if(LoginAPI.authMobMap.get(mobNo)==null){
			Log.info(mobNo+" has already logged out or has not logged in successfully");
			return true;
		}
		else{
			try{
			RestAssured.baseURI=LoginAPI.DC;
			res=given()
				.header("Content-Type","application/x-www-form-urlencoded")
				.header("X-Auth-Token",LoginAPI.userAuthToken)
				.header("X-Auth-UID",LoginAPI.userId)
				.body("username="+mobNo+"&deviceId=tdid%3A62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4%3D%3A4900&deviceIdType=3")
				.when()
				.post("/in/user/auth/6019/logout");
			response = res.asString();
			System.out.println(response);
			JsonPath jp = new JsonPath(response);
			
			if(jp.getString("status").equals("true"))
			{ //checking of logout api returned true
				Log.info(mobNo+" has logged out successfully");
				LoginAPI.authMobMap.put(mobNo, null);
				return true;
			}
			else{
				Log.info(mobNo+" has not logged out successfully");
				return false;
			}
			}catch(Exception e)
			{
				Log.info("Error in API Logout "+e.getMessage());e.printStackTrace();
				return false;
				
			}
				
		}
				
	}

}
