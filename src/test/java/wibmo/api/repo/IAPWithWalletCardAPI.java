package wibmo.api.repo;

import library.Generic;

import org.testng.Assert;

import library.Log;

import wibmo.app.testScripts.BaseTest1;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class IAPWithWalletCardAPI {

	static Response res;
	static JsonPath jp;

	static String response;
	public static String pgTxnId;
	/** This is merchant txn ID **/

	static String merTxnId, wibmoTxnId, wibmoTxnToken, msgHash, enqMsgHash;

	public static void merchantTxnIdGeneration(String amt) {
		RestAssured.baseURI = Generic.getPropValues("WALLETDOMAIN", BaseTest1.configPath);
		res = given()
				.param("merAppData", "This is Wibmo Test App")
				.param("merName", "Mobile Merchant")
				.param("merDyn", "false")
				.param("txnAmount", amt)
				.param("txnAmountKnown", "false")
				.param("chargeLater", "true")
				.param("version", "2")
				.param("txnType", "wPay").when()
				.post("/sampleMerchant/iap/generateInitReqMessageHash.jsp");
		response = res.asString();
		jp = new JsonPath(response);
		//System.out.println(response);
		merTxnId = jp.getString("merTxnId");
		msgHash = jp.getString("msgHash");

	}

	public static void init(String amt) {
		RestAssured.baseURI = LoginAPI.DC;

		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-UID", LoginAPI.userId)
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				//.log().all()
				.body("{" + "\"cardInfo\":{" +

				"}," + "\"customerInfo\":{" + "\"custDob\":\"20140101\","
						+ "\"custEmail\":\"abc@abc.com\","
						+ "\"custMobile\":\"1234512345\","
						+ "\"custName\":\"Sharath Kumar\"" + "},"
						+ "\"deviceInfo\":{" + "\"appInstalled\":true,"
						+ "\"deviceIDType\":3," + "\"deviceType\":3,"
						+ "\"osType\":\"Android\","
						+ "\"wibmoAppVersion\":\"??\","
						+ "\"wibmoSdkVersion\":\"2.0.1\"" + "},"
						+ "\"merchantInfo\":{" + "\"merAppId\":\"1\","
						+ "\"merCountryCode\":\"IN\","
						+ "\"merId\":\"81516121\","
						+ "\"merName\":\"Test Merchant\"" + "},"
						+ "\"msgHash\":\"" + msgHash + "\","
						+ "\"transactionInfo\":{" + "\"chargeLater\":true,"
						+ "\"merAppData\":\"This is Wibmo Test App\","
						+ "\"merDataField\":\"This is for recon "+amt+"\","
						+ "\"merTxnId\":\"" + merTxnId + "\","
						+ "\"supportedPaymentType\":[" + "\"*\"" + "],"
						+ "\"txnAmount\":\""+amt+"\"," + "\"txnAmtKnown\":false,"
						+ "\"txnCurrency\":\"356\","
						+ "\"txnDesc\":\"For Movie Ticket\"" + "},"
						+ "\"txnType\":\"WPay\"" + "}").when()
				.post("/v2/in/txn/iap/wpay/init");
		response = res.asString();
		jp = new JsonPath(response);
		//System.out.println(response);

		wibmoTxnId = jp.getString("wibmoTxnId");
		wibmoTxnToken = jp.getString("wibmoTxnToken");
		Log.info("======Wibmo txn ID is "+wibmoTxnId+"======");
		Log.info("======Wibmo txn token is "+wibmoTxnToken+"======");
		//System.out.println(wibmoTxnId);
		//System.out.println(wibmoTxnToken);
	}

	public static void initiateAuthentication() {
		Log.info("=======Starting authentication leg======");
		RestAssured.baseURI = LoginAPI.DC;
		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-UID", LoginAPI.userId)
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				//.log().all()
				.body("{"
						+ "\"deviceInfo\": {"
						+ "\"appInstalled\": true,"
						+ "\"deviceID\": \"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"deviceIDType\": 3,"
						+ "\"deviceMake\": \"motorola\","
						+ "\"deviceModel\": \"XT1225\"," + "\"deviceType\": 3,"
						+ "\"osType\": \"Android\","
						+ "\"osVersion\": \"6.0.1\","
						+ "\"wibmoAppVersion\": \"3.02.01.10\","
						+ "\"wibmoSdkVersion\": \"2.0.6\"" + "},"
						+ "\"linkBillingAddress\": false,"
						+ "\"merchantInfo\": {" + "\"merAppId\": \"1\","
						+ "\"merCountryCode\": \"IN\","
						+ "\"merId\": \"81516121\","
						+ "\"merName\": \"Test Merchant\","
						+ "\"merProgramId\": \"6019\"" + "},"
						+ "\"paymentCardInfo\": {"
						+ "\"favPaymentCard\": false," + "\"saveCard\": false,"
						+ "\"useWibmoCard\": true" + "},"
						+ "\"userAuthToken\": \"" + LoginAPI.userAuthToken
						+ "\"," + "\"userId\": \"" + LoginAPI.userId + "\","
						+ "\"userPcAccount\": \"" + LoginAPI.pcAcNumber + "\","
						+ "\"wibmoTxnId\": \"" + wibmoTxnId + "\","
						+ "\"wibmoTxnToken\": \"" + wibmoTxnToken + "\"" + "}")
				.when().post("/v2/in/txn/iap/wpay/6019/process");
		response = res.asString();
		// System.out.println(response);
	}

	public static void mockAuthenticationResponse(String amt) {
		Log.info("======Receiving authentication response========");
		RestAssured.baseURI = LoginAPI.DC;
		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-UID", LoginAPI.userId)
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.body("{"
						+ "\"additionalUserInputData\": {"
						+ "\"browserKeyValue\": ["
						+ "{"
						+ "\"id\": \"status\","
						+ "\"value\": \"Y\""
						+ "},"
						+ "{"
						+ "\"id\": \"eci\","
						+ "\"value\": \"05\""
						+ "},"
						+ "{"
						+ "\"id\": \"cavv\","
						+ "\"value\": \"AAABBzcAAFklaGVzCQAAAAAAAAA=\""
						+ "},"
						+ "{"
						+ "\"id\": \"purchase_amount\","
						+ "\"value\": \""+amt+"\""
						+ "},"
						+ "{"
						+ "\"id\": \"currency\","
						+ "\"value\": \"356\""
						+ "},"
						+ "{"
						+ "\"id\": \"message_hash\","
						+ "\"value\": \"9mbCzpJVYV9GQv/IUp8C3MFCPfw=\""
						+ "},"
						+ "{"
						+ "\"id\": \"mpiErrorCode\","
						+ "\"value\": \"000\""
						+ "},"
						+ "{"
						+ "\"id\": \"ID\","
						+ "\"value\": \"20171011899691\""
						+ "},"
						+ "{"
						+ "\"id\": \"md\","
						+ "\"value\": \"201710111128098914dI98iN6\""
						+ "},"
						+ "{"
						+ "\"id\": \"xid\","
						+ "\"value\": \"TVBJWElENWdEMWtDMHBNOW5KMG8=\""
						+ "},"
						+ "{"
						+ "\"id\": \"statusReceivedInVERes\","
						+ "\"value\": \"Y\""
						+ "},"
						+ "{"
						+ "\"id\": \"statusReceivedInPARes\","
						+ "\"value\": \"Y\""
						+ "},"
						+ "{"
						+ "\"id\": \"PAResVerified\","
						+ "\"value\": \"true\""
						+ "},"
						+ "{"
						+ "\"id\": \"PAResSyntaxOK\","
						+ "\"value\": \"true\""
						+ "},"
						+ "{"
						+ "\"id\": \"cardholder_country\","
						+ "\"value\": \"IN\""
						+ "}"
						+ "],"
						+ "\"formInput\": ["
						+ "{"
						+ "\"allowStore\": false,"
						+ "\"editable\": false,"
						+ "\"id\": \"METHOD_IDENTIFIER\","
						+ "\"inputType\": \"HID\","
						+ "\"inputValue\": \"RESPONSE_FROM_EXTERANL_SOURCE\","
						+ "\"mandatory\": false,"
						+ "\"maxLength\": 0,"
						+ "\"minLength\": 0"
						+ "}"
						+ "]"
						+ "},"
						+ "\"deviceInfo\": {"
						+ "\"appInstalled\": true,"
						+ "\"deviceID\": \"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"deviceIDType\": 3,"
						+ "\"deviceMake\": \"motorola\","
						+ "\"deviceModel\": \"XT1225\"," + "\"deviceType\": 3,"
						+ "\"osType\": \"Android\","
						+ "\"osVersion\": \"6.0.1\","
						+ "\"wibmoAppVersion\": \"3.02.01.10\","
						+ "\"wibmoSdkVersion\": \"2.0.6\"" + "},"
						+ "\"linkBillingAddress\": false,"
						+ "\"merchantInfo\": {" + "\"merAppId\": \"1\","
						+ "\"merCountryCode\": \"IN\","
						+ "\"merId\": \"81516121\","
						+ "\"merName\": \"Test Merchant\","
						+ "\"merProgramId\": \"6019\"" + "},"
						+ "\"paymentCardInfo\": {"
						+ "\"favPaymentCard\": false," + "\"saveCard\": false,"
						+ "\"useWibmoCard\": true" + "},"
						+ "\"userAuthToken\": \"" + LoginAPI.userAuthToken
						+ "\"," + "\"userId\": \"" + LoginAPI.userId + "\","
						+ "\"userPcAccount\": \"" + LoginAPI.pcAcNumber + "\","
						+ "\"wibmoTxnId\": \"" + wibmoTxnId + "\","
						+ "\"wibmoTxnToken\": \"" + wibmoTxnToken + "\"" + "}")
				.when().post("/v2/in/txn/iap/wpay/6019/process");
		response = res.asString();
		 //System.out.println(response);
	}
	
	public static void generateEnquiryHash(int amt){
		RestAssured.baseURI=Generic.getPropValues("WALLETDOMAIN", BaseTest1.configPath);
		res = given().
				param("txnAmount",amt).
				param("txnType", "wPay").
				param("merTxnId",merTxnId).
				param("chargeUser", "true").
				param("wibmoTxnId",wibmoTxnId).
				when().
				post("/sampleMerchant/iap/generateEnquiryMessageHash.jsp");
		response = res.asString();
		jp=new JsonPath(response);
		//System.out.println(response);
		enqMsgHash=jp.getString("msgHash");
		
	}

	public static void charge(int amt) {
		generateEnquiryHash(amt);
		RestAssured.baseURI = LoginAPI.DC;

		res = given().
				header("Content-Type","application/json")
				//.log().all()
				.body("{"+
						  "\"merchantInfo\": {"+
						    "\"merName\": null,"+
						    "\"merId\": \"81516121\","+
						    "\"merCountryCode\": \"IN\","+
						    "\"merAppId\": \"1\""+
						  "},"+
						  "\"transactionInfo\": {"+
						    "\"txnAmount\": \""+amt+"\","+
						    "\"txnCurrency\": \"356\","+
						    "\"txnDesc\": null,"+
						    "\"supportedPaymentType\": null,"+
						    "\"restrictedPaymentType\": null,"+
						    "\"merAppData\": null,"+
						    "\"merTxnId\": \""+merTxnId+"\","+
						    "\"merDataField\": null,"+
						    "\"txnDate\": \"20171011\","+
						    "\"txnAmtKnown\": true,"+
						    "\"chargeLater\": false"+
						  "},"+
						  "\"msgHash\": \""+enqMsgHash+"\","+
						  "\"chargeCard\": true,"+
						  "\"txnType\": \"WPay\","+
						  "\"wibmoTxnId\": \""+wibmoTxnId+"\""+
						"}")
				.when().post("/v2/in/txn/iap/wpay/enquiry/");
		response = res.asString();
		jp=new JsonPath(response);
		//System.out.println(response);
		
	}
	
	public static void chargeValidation(int amt , String...validateChargeStatus){
		String validateCharge=validateChargeStatus.length>0?validateChargeStatus[0]:"";
		charge(amt);
		String pgStatusCode=jp.getString("data.pgStatusCode");
		String pgErrorDesc = jp.getString("data.pgErrorDesc");
		pgTxnId=jp.getString("data.pgTxnId");
		Log.info("======PG status is "+pgStatusCode);
		Log.info("======PG txn ID is "+pgTxnId);
		//Assert.assertTrue(!pgStatusCode.equals("50021")&&!pgStatusCode.equals("50020"),"Charge is neither success of failure");
		if(Generic.containsIgnoreCase(validateCharge, "fail")){
			Assert.assertEquals(pgStatusCode, "50021");
		}
		else if(Generic.containsIgnoreCase(validateCharge, "success"))
			Assert.assertEquals(pgStatusCode, "50020");
			

	}
	
	public static String fetchWibmoTxnID(){
		return wibmoTxnId;
	}
}
