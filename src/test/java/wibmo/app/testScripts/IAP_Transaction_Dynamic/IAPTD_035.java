package wibmo.app.testScripts.IAP_Transaction_Dynamic;


import wibmo.app.testScripts.IAP_Transaction.IAPTS_035;

/**
 * The Class IAPTD_035  App-SDK IAP Transaction hold & cancel at login,phone verification,card selection,3DS- Dynamic merchant
 * 
 * IAPTD_006 App-SDK IAP transaction hold on login screen and Verify Balance
 * IAPTD_001 App-SDK IAP transaction cancellation in login and Verify Balance 
 * IAPTD_008 App-SDK IAP transaction hold on card selection and Verify Balance
 * IAPTD_003 App-SDK IAP transaction cancellation in card selection and Verify Balance
 * IAPTD_010 App-SDK IAP transaction status in hold on 3ds screen and Verify Balance 
 * IAPTD_012 App-SDK IAP transaction failure on 3ds authentication and Verify Balance
 * IAPTD_005 App-SDK IAP transaction status in cancellation 3ds screen 
 *  
 * */
 
public class IAPTD_035 extends IAPTS_035 
{	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_035")+" Dynamic Merchant";		
}
