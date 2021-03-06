package wibmo.api.repo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class LoadMoney {
	static Response res;
	static String response, inflTxnId;
	static JsonPath jp;

	public static void LoadMoneyInitiateAuthentication() {
		RestAssured.baseURI = LoginAPI.DC;
		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.header("X-Auth-UID", LoginAPI.userId)
				.body("{"
						+ "\"clientInfo\":{"
						+ "\"clientDeviceName\":\"AndriodApp @ motorola XT1225 - Vodafone IN\","
						+ "\"clientDeviceType\":3,"
						+ "\"clientMaker\":\"motorola\","
						+ "\"clientModel\":\"XT1225\","
						+ "\"clientOsName\":\"Android\","
						+ "\"clientOsVersion\":\"6.0.1\""
						+ "},"
						+ "\"gpsInfo\":{"
						+ "\"accuracy\":0.0,"
						+ "\"gpsTime\":0,"
						+ "\"latitude\":0.0,"
						+ "\"longitude\":0.0"
						+ "},"
						+ "\"inputs\":["
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"PAN\","
						+ "\"inputValue\":\"4329091207169785\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"EXPIRY_YYYYMM\","
						+ "\"inputValue\":\"202008\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"CH_NAME\","
						+ "\"inputValue\":\"Prathyush parat\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"AMOUNT\","
						+ "\"inputValue\":\"1.0\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "}"
						+ "],"
						+ "\"newSourceOfFund\":false,"
						+ "\"pcAccountNumber\":\""
						+ LoginAPI.pcAcNumber
						+ "\","
						+ "\"saveUserSF\":false,"
						+ "\"simAndDeviceData\":{"
						+ "\"deviceData1\":\"zoklBWqgx5agU+s5I7mDMiCFqIKbHgszRuUuJAQ8CXs=\","
						+ "\"deviceData2\":\"DES+n3lIlX2wc6HyTCZrB1CLEn7AMLMoJprQUtchOqc=\","
						+ "\"deviceData3\":\"NeYujrnWV1k3N+dm11rz4OdhI0fSU7T3ejJkgqjsSf8=\","
						+ "\"deviceData4\":\"coyERY/CJaEj47LHmuUZ73uaROgPudGhrKuS8jF4ym4=\","
						+ "\"deviceId\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"deviceIdProof\":\"BEmmuuPZezZEkM+m/NpwSspwCNCr1Rq5uD+yDSS3v6Vq45kZOo6tZXBhtTT940k8207ljbprGvuzlosGaOdCRQ==\","
						+ "\"deviceIdType\":3,"
						+ "\"simId\":\"subid:UO/GZJGz7H2y4Cm1umj80Lqu1Mn5kMZEuK74MZaTEHU=:0373\","
						+ "\"simIdProof\":\"K0gSVQ8tztRn7K/SXGHqn/D+s9OW2Ng0KDksg3zjB09XuRxVVbDvwBKSoPOSyEKAER8EC6wjHd+Bic/PPrl8ym3KqwURfIVomQcyydSC4MR0QYfM7Sryu/Ss+O/AuM1Nl75fY1Y2MPKjkItAHfN2uXm/iJllR5AgcYj1gKfljeY=\","
						+ "\"simOperator\":\"Vodafone IN\"" + "},"
						+ "\"sourceOfFundAliasName\":\"Corp\","
						+ "\"sourceOfFundId\":\"SFT_VDC\","
						+ "\"userSourceOfFundId\":\"422612\"" + "}").when()
				.post("/in/txn/inflow/6019/loadFund");
		res.then().body("browserPostKeyValuePairs.body-text-center",equalTo("3DSecure Transaction is being processed, Please wait ..."));
		response = res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
		inflTxnId = jp.getString("browserPostKeyValuePairs.INFLOW_TXN_ID");

	}

	public static void LoadMoneyMockAUthenticationResponse() {
		RestAssured.baseURI = LoginAPI.DC;
		res = given()
				.header("Content-Type", "application/json")
				.header("X-Auth-Token", LoginAPI.userAuthToken)
				.header("X-Auth-UID", LoginAPI.userId)
				.body("{"
						+ "\"clientInfo\":{"
						+ "\"clientDeviceName\":\"AndriodApp @ motorola XT1225 - Vodafone IN\","
						+ "\"clientDeviceType\":3,"
						+ "\"clientMaker\":\"motorola\","
						+ "\"clientModel\":\"XT1225\","
						+ "\"clientOsName\":\"Android\","
						+ "\"clientOsVersion\":\"6.0.1\"" + "},"
						+ "\"gpsInfo\":{" + "\"accuracy\":0.0,"
						+ "\"gpsTime\":0," + "\"latitude\":0.0,"
						+ "\"longitude\":0.0" + "}," + "\"inputs\":[" + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"statusReceivedInVERes\","
						+ "\"inputValue\":\"Y\"," + "\"mandatory\":false,"
						+ "\"maxLength\":0," + "\"minLength\":0" + "}," + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"purchase_amount\","
						+ "\"inputValue\":\"0.1\"," + "\"mandatory\":false,"
						+ "\"maxLength\":0," + "\"minLength\":0" + "}," + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"statusReceivedInPARes\","
						+ "\"inputValue\":\"Y\"," + "\"mandatory\":false,"
						+ "\"maxLength\":0," + "\"minLength\":0" + "}," + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false," + "\"id\":\"PAResSyntaxOK\","
						+ "\"inputValue\":\"true\"," + "\"mandatory\":false,"
						+ "\"maxLength\":0," + "\"minLength\":0" + "}," + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false," + "\"id\":\"cavv\","
						+ "\"inputValue\":\"AAABBRkAAABidFiXYQAAAAAAAAA=\","
						+ "\"mandatory\":false," + "\"maxLength\":0,"
						+ "\"minLength\":0" + "}," + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false," + "\"id\":\"ID\","
						+ "\"inputValue\":\"20170327640345\","
						+ "\"mandatory\":false," + "\"maxLength\":0,"
						+ "\"minLength\":0" + "}," + "{"
						+ "\"allowStore\":false," + "\"editable\":false,"
						+ "\"encryption\":false," + "\"id\":\"INFLOW_TXN_ID\","
						+ "\"inputValue\":\""
						+ inflTxnId
						+ "\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"PAResVerified\","
						+ "\"inputValue\":\"true\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"eci\","
						+ "\"inputValue\":\"05\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"md\","
						+ "\"inputValue\":\""
						+ inflTxnId
						+ "\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"status\","
						+ "\"inputValue\":\"Y\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"cardholder_country\","
						+ "\"inputValue\":\"IN\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"message_hash\","
						+ "\"inputValue\":\"ZHpi+AHMBlkVvfSOhXAC4ZPbEjM=\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"xid\","
						+ "\"inputValue\":\"TVBJWElENXpCNGFKM3NYM3BSMGQ=\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"METHOD_IDENTIFIER\","
						+ "\"inputValue\":\"RESPONSE_FROM_EXTERANL_SOURCE\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"currency\","
						+ "\"inputValue\":\"356\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "},"
						+ "{"
						+ "\"allowStore\":false,"
						+ "\"editable\":false,"
						+ "\"encryption\":false,"
						+ "\"id\":\"mpiErrorCode\","
						+ "\"inputValue\":\"000\","
						+ "\"mandatory\":false,"
						+ "\"maxLength\":0,"
						+ "\"minLength\":0"
						+ "}"
						+ "],"
						+ "\"newSourceOfFund\":false,"
						+ "\"pcAccountNumber\":\""
						+ LoginAPI.pcAcNumber
						+ "\","
						+ "\"saveUserSF\":false,"
						+ "\"simAndDeviceData\":{"
						+ "\"deviceData1\":\"zoklBWqgx5agU+s5I7mDMiCFqIKbHgszRuUuJAQ8CXs=\","
						+ "\"deviceData2\":\"DES+n3lIlX2wc6HyTCZrB1CLEn7AMLMoJprQUtchOqc=\","
						+ "\"deviceData3\":\"NeYujrnWV1k3N+dm11rz4OdhI0fSU7T3ejJkgqjsSf8=\","
						+ "\"deviceData4\":\"coyERY/CJaEj47LHmuUZ73uaROgPudGhrKuS8jF4ym4=\","
						+ "\"deviceId\":\"tdid:62Zm0zrjP80kOILv2YEE7xthhp1UhjWFpNbzm3PhcT4=:4900\","
						+ "\"deviceIdProof\":\"BEmmuuPZezZEkM+m/NpwSspwCNCr1Rq5uD+yDSS3v6Vq45kZOo6tZXBhtTT940k8207ljbprGvuzlosGaOdCRQ==\","
						+ "\"deviceIdType\":3,"
						+ "\"simId\":\"subid:UO/GZJGz7H2y4Cm1umj80Lqu1Mn5kMZEuK74MZaTEHU=:0373\","
						+ "\"simIdProof\":\"K0gSVQ8tztRn7K/SXGHqn/D+s9OW2Ng0KDksg3zjB09XuRxVVbDvwBKSoPOSyEKAER8EC6wjHd+Bic/PPrl8ym3KqwURfIVomQcyydSC4MR0QYfM7Sryu/Ss+O/AuM1Nl75fY1Y2MPKjkItAHfN2uXm/iJllR5AgcYj1gKfljeY=\","
						+ "\"simOperator\":\"Vodafone IN\""
						+ "},"
						+ "\"sourceOfFundId\":\"SFT_VDC\","
						+ "\"userSourceOfFundId\":\"422612\"" + "}").when()
				.post("/in/txn/inflow/6019/loadFund");
		res.then().body("addFundDetails.headerMessage", equalTo(  "Funds have been loaded successfully"));
		response = res.asString();
		jp = new JsonPath(response);
		System.out.println(response);
	}

}
