package wibmo.api.repo;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.response.Response;
import library.Generic;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import io.restassured.matcher.RestAssuredMatchers.*;

import org.apache.commons.collections4.Get;
import org.hamcrest.Matchers.*;

public class API {
	
	public synchronized static void fetchBalance(String uname , String pin){
		LoginAPI.Login(uname, pin);
		GetBalanceAPI.balanceFetch();
		int balance=GetBalanceAPI.availableBalance;
		Log.info("======The implied decimal balance is "+balance);
				
	}
	
	public synchronized static double fetchBalanceDouble(String uname , String pin){
		fetchBalance(uname, pin);
		double balance=GetBalanceAPI.availableBalance/100.0;
		Log.info("======The balance is "+balance);
		return balance;
	}
	
	public synchronized static void cardBlock(String uname , String pin){
		LoginAPI.Login(uname, pin);
		CardBlockUnblock.block();
	}
	
	public synchronized static void cardUnblock(String uname , String pin){
		LoginAPI.Login(uname, pin);
		CardBlockUnblock.unblock();
	}

	/**
	 * Method to register a mob no: with pin 1234
	 * 
	 * @param mob
	 * @return
	 */
	public static void registration(String mob) {
		
		String response="";
		 Response res;		
		
		RestAssured.baseURI = Generic.getPropValues("RESTDOMAIN", BaseTest1.configPath);
		Log.info("=====Registering "+mob+" from API =====");
		try {
			res = given().header("Content-Type", "application/json").log().all().header("X-API-KEY", "testing")
					.body("{" + 
							"\"firstName\" : \"Prathyush\"," + 
							"\"lastName\" : \"Parat\"," + 
							"\"mobile\" : \"" + mob+ "\"," + 
							"\"dateOfBirthYYYYMMDD\" : \"19861123\"," + 
							"\"gender\" : \"M\","+
							"\"pin\" : \"MTExMQ==\"," + 
							"\"pinType\" : \"0\"," + 
							"\"linkedCards\" : [ " + 
							"]" + 
						 "}")
					.when().post("/in/user/reg/6019/trustedsrc");
			response = res.asString();
			Assert.assertTrue(response.contains("true"),"Invalid response : "+response);
			
			System.out.println(mob+" Registration complete \n");
			
			if(response.contains("already"))
				Assert.fail(mob+" already registered "+response);
	
		} catch (Exception e) {
			
			Assert.fail("===== " + mob + response+"... registration failed =====");
		}
		
	}
	
	public static void APIChecker()
	{
}
	
	

}
