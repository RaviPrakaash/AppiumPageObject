package wibmo.api.repo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import library.Generic;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import static io.restassured.RestAssured.given;

import org.testng.Assert;


public class JobAPI {
	
	static JsonPath jp;
	static Response res;
	static String Response;
	/***
	 * Method to trigger a job. The method return true or false.
	 */
	public static boolean triggerJob(String jobName)
	{
		RestAssured.baseURI=Generic.getPropValues("CSRRESTDOMAIN", BaseTest1.configPath);
		String jobAPIPassword=Generic.getPropValues("APIPASSWORD", BaseTest1.configPath);
		
		Log.info("=====API call to run " + jobName + " job =====");
		
		try{
			res = given()
					.param("password", jobAPIPassword)
					.log().all()
					.when()
					.post("/internal/job/"+jobName+"/start/");
		
		Response=res.asString();
		Log.info("======== Response of " + jobName + " job API ======== " +Response );
		
		Assert.assertEquals("true", Response);
		return true;
		}catch(Exception e) {
			Assert.fail("====="+jobName+" Job failed=====");
			return false;
		}
		
	}

}
