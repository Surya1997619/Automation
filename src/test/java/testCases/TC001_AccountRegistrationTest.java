package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
 
	@Test(groups={"Regression", "Master"})
	public void verify_account_registration() {

		logger.info("*******Starting TC001_AccountRegistrationTest**************");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();

			logger.info("*******Clicked on MyAccount Link*************");

			hp.MyRegister();
			
			logger.info("*******Clicked on Register Link*************");
			
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			 
			logger.info("*******Providing cusomer details*************");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toUpperCase());
			regpage.setEmail(randomString() + "@gmail.com");
			regpage.setTelephone(randomNumber());

			String password = randomAlphaNumeric();

			regpage.setPassword(password);
			regpage.setConfirmPassword(password);
			regpage.setPrivacyPolicy();
			regpage.clickContinue();
			
			logger.info("*******Validating expecting message*************");
			String confmsg = regpage.getConfirmationMessage();
			if(confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}
				
			else {
				logger.error("Test failed...");
				logger.debug("Debug Logs..");
				Assert.fail();
				
			}
			//Assert.assertEquals(confmsg, "Your Account Has Been Created!");}

		} catch (Exception e) {
			logger.error("Test failed...");
			logger.debug("Debug Logs..");
			Assert.fail();

		}
		logger.info("*******Finishing TC001_AccountRegistrationTest**************");

	}

}
