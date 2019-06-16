package wibmo.api.repo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LoadMoneyIAPAPI {
	static Response res;
	static String response, inflTxnId,wibmoTxnId,wibmoTxnToken,dataPickUpCode,msgHash;
	static JsonPath jp;

	public static void LoadMoneyInit() {
		
		System.out.println("======Load money init======");
		RestAssured.baseURI = LoginAPI.DC;
		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.header("X-Auth-UID", LoginAPI.userId)
				.log().all()
				.body("{"
						+ "\"deviceInfo\":{"
						+ "\"appInstalled\":true,"
						+ "\"deviceID\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"deviceIDType\":3,"
						+ "\"deviceMake\":\"motorola\","
						+ "\"deviceModel\":\"XT1225\"," + "\"deviceType\":3,"
						+ "\"osType\":\"Android\","
						+ "\"osVersion\":\"6.0.1\","
						+ "\"wibmoAppVersion\":\"3.03.01.04\","
						+ "\"wibmoSdkVersion\":\"2.2.0\"" + "},"
						+ "\"pan\":\"4329091207169785\","
						+ "\"sourceOfFundAliasName\":\"Video\","
						+ "\"sourceOfFundId\":\"SFT_VDC\","
						+ "\"transactionCurrencyCode\":\"356\","
						+ "\"txnAmount\":\"100\"" + "}")
				.when()
				.post("/v2/in/txn/loadMoney/6019/"+LoginAPI.pcAcNumber+"/init");
		res.then()
				.body("resDesc",
						equalTo("SUCCESS"));
		response = res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
		wibmoTxnId=jp.getString("initResponse.wibmoTxnId");
		wibmoTxnToken=jp.getString("initResponse.wibmoTxnToken");
		//inflTxnId = jp.getString("browserPostKeyValuePairs.INFLOW_TXN_ID");

	}
	
	public static void loadMoneyAuthentication(){
		System.out.println("======Load money authentication======");

		RestAssured.baseURI=LoginAPI.DC;
		res=given()
				.header("Content-Type", "application/json")
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.header("X-Auth-UID", LoginAPI.userId)
				.log().all()
				.body("{"+  
						   "\"deviceInfo\":{"+  
						      "\"appInstalled\":true,"+
						      "\"deviceID\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","+
						      "\"deviceIDType\":3,"+
						      "\"deviceMake\":\"motorola\","+
						      "\"deviceModel\":\"XT1225\","+
						      "\"deviceType\":3,"+
						      "\"osType\":\"Android\","+
						      "\"osVersion\":\"6.0.1\","+
						      "\"wibmoAppVersion\":\"3.03.01.04\","+
						      "\"wibmoSdkVersion\":\"2.2.0\""+
						   "},"+
						   "\"linkBillingAddress\":false,"+
						   "\"merchantInfo\":{"+  
						      "\"merAppId\":\"9017\","+
						      "\"merCountryCode\":\"IN\","+
						      "\"merId\":\"1273864627877702560\","+
						      "\"merName\":\"PayZapp Load Money\","+
						      "\"merProgramId\":\"6019\""+
						   "},"+
						   "\"paymentCardInfo\":{"+  
						      "\"alias\":\"Visa\","+
						      "\"expMM\":\"08\","+
						      "\"expYYYY\":\"2020\","+
						      "\"favPaymentCard\":false,"+
						      "\"nameOnCard\":\"Video\","+
						      "\"pan\":\"4329091207169785\","+
						      "\"saveCard\":true,"+
						      "\"useWibmoCard\":false"+
						   "},"+
						   "\"userAuthToken\":\""+LoginAPI.userAuthToken+"\","+
						   "\"userId\":\""+LoginAPI.userId+"\","+
						   "\"userPcAccount\":\""+LoginAPI.pcAcNumber+"\","+
						   "\"wibmoTxnId\":\""+wibmoTxnId+"\","+
						   "\"wibmoTxnToken\":\""+wibmoTxnToken+"\""+
						"}")
				.when()
				.post("/v2/in/txn/iap/wpay/6019/process");
		//res.then().body("browserPostKeyValuePairs.body-text-center",equalTo("3DSecure Transaction is being processed, Please wait ..."));
		//res.then().body("additionalUserInputData.redirectPageText",
				//equalTo("3DSecure Transaction is being processed, Please wait ..."));
		response=res.asString();
		System.out.println(response);
		jp = new JsonPath(response);
		
		
	}
	
	public static void LoadMoneyMockAUthenticationResponse() {
		System.out.println("======Load money Mock authentication======");

		RestAssured.baseURI = LoginAPI.DC;
		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.header("X-Auth-UID", LoginAPI.userId)
				.log().all()
				.body("{"+ 
						   "\"additionalUserInputData\":{"+  
						      "\"browserKeyValue\":["+  
						         "{"+ 
						            "\"id\":\"status\","+
						            "\"value\":\"Y\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"eci\","+
						            "\"value\":\"05\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"cavv\","+
						            "\"value\":\"AAABBRkAADdIA2OFlAAAAAAAAAA=\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"purchase_amount\","+
						            "\"value\":\"100\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"currency\","+
						            "\"value\":\"356\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"message_hash\","+
						            "\"value\":\"cBiNI9U6qrcrSN06IAvgrp/GZgM=\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"mpiErrorCode\","+
						            "\"value\":\"000\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"ID\","+
						            "\"value\":\"20180103403038\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"md\","+
						            "\"value\":\""+wibmoTxnId+"\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"xid\","+
						            "\"value\":\"TVBJWElENXhJOHhaNXVPOHlKNm0=\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"statusReceivedInVERes\","+
						            "\"value\":\"Y\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"statusReceivedInPARes\","+
						            "\"value\":\"Y\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"PAResVerified\","+
						            "\"value\":\"true\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"PAResSyntaxOK\","+
						            "\"value\":\"true\""+
						         "},"+
						         "{"+ 
						            "\"id\":\"cardholder_country\","+
						            "\"value\":\"IN\""+
						         "}"+
						      "],"+
						      "\"formInput\":["+  
						         "{"+ 
						            "\"allowStore\":false,"+
						            "\"editable\":false,"+
						            "\"id\":\"METHOD_IDENTIFIER\","+
						            "\"inputType\":\"HID\","+
						            "\"inputValue\":\"RESPONSE_FROM_EXTERANL_SOURCE\","+
						            "\"mandatory\":false,"+
						            "\"maxLength\":0,"+
						            "\"minLength\":0"+
						         "}"+
						      "]"+
						   "},"+
						   "\"deviceInfo\":{"+  
						      "\"appInstalled\":true,"+
						      "\"deviceID\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","+
						      "\"deviceIDType\":3,"+
						      "\"deviceMake\":\"motorola\","+
						      "\"deviceModel\":\"XT1225\","+
						      "\"deviceType\":3,"+
						      "\"osType\":\"Android\","+
						      "\"osVersion\":\"6.0.1\","+
						      "\"wibmoAppVersion\":\"3.03.01.04\","+
						      "\"wibmoSdkVersion\":\"2.2.0\""+
						   "},"+
						   "\"linkBillingAddress\":false,"+
						   "\"merchantInfo\":{"+  
						      "\"merAppId\":\"9017\","+
						      "\"merCountryCode\":\"IN\","+
						      "\"merId\":\"1273864627877702560\","+
						      "\"merName\":\"PayZapp Load Money\","+
						      "\"merProgramId\":\"6019\""+
						   "},"+
						   "\"paymentCardInfo\":{"+  
						      "\"alias\":\"Visa\","+
						      "\"expMM\":\"08\","+
						      "\"expYYYY\":\"2020\","+
						      "\"favPaymentCard\":false,"+
						      "\"nameOnCard\":\"Video\","+
						      "\"pan\":\"4329091207169785\","+
						      "\"saveCard\":true,"+
						      "\"useWibmoCard\":false"+
						   "},"+
						   "\"userAuthToken\":\""+LoginAPI.userAuthToken+"\","+
						   "\"userId\":\""+LoginAPI.userId+"\","+
						   "\"userPcAccount\":\""+LoginAPI.pcAcNumber+"\","+
						   "\"wibmoTxnId\":\""+wibmoTxnId+"\","+
						   "\"wibmoTxnToken\":\""+wibmoTxnToken+"\""+
						"}").when()
				.post("/v2/in/txn/iap/wpay/6019/process");
				
		response = res.asString();
		jp = new JsonPath(response);
		dataPickUpCode=(jp.getString("dataPickUpCode"));
		msgHash=jp.getString("msgHash");
		System.out.println(response);
	}

	public static void LoadMoneyIAPProcess(){
		System.out.println("======Load money completion======");

		RestAssured.baseURI=LoginAPI.DC;
		res=given()
				.header("Content-Type", "application/json")
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.header("X-Auth-UID", LoginAPI.userId)
				.log().all()
				.body("{"+  
						   "\"dataPickUpCode\":\""+dataPickUpCode+"\","+
						   "\"deviceInfo\":{"+  
						      "\"appInstalled\":true,"+
						      "\"deviceID\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","+
						      "\"deviceIDType\":3,"+
						      "\"deviceMake\":\"motorola\","+
						      "\"deviceModel\":\"XT1225\","+
						      "\"deviceType\":3,"+
						      "\"osType\":\"Android\","+
						      "\"osVersion\":\"6.0.1\","+
						      "\"wibmoAppVersion\":\"3.03.01.04\","+
						      "\"wibmoSdkVersion\":\"2.2.0\""+
						   "},"+
						   "\"msgHash\":\""+msgHash+"\","+
						   "\"resCode\":\"000\","+
						   "\"resDesc\":\"SUCCESS\","+
						   "\"wibmoTxnId\":\""+wibmoTxnId+"\""+
						"}")
				.when()
				.post("/v2/in/txn/loadMoney/6019/"+LoginAPI.pcAcNumber+"/process");
		response=res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
		res.then().body("addFundDetails.headerMessage" , equalTo("Funds have been loaded successfully"));

				
	}

	
}
