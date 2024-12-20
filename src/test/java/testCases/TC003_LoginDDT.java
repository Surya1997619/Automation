package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

//DATA is valid-login success-test pass-logout
//DATA is valid-login failed-test fail

//DATA is invalid-login success-test fail-logout
//DATA is invalid-login failed-test pass

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven")
	public void verif_loginDDT(String email, String pwd, String exp)
	{
		logger.info("****************starting TC003_LoginDDT ********");
try
{
	//Homepage
	HomePage hp = new HomePage(driver);
	hp.clickMyAccount();
	hp.clickLogin();

	//Login
	LoginPage lp = new LoginPage(driver);
	lp.setemail(email);
	lp.setpassword(pwd);
	lp.clickLogin();
	
	//MyAccount
	MyAccountPage macc = new MyAccountPage(driver);
	boolean targetPage = macc.isMyAccountExists();
	
	if(exp.equalsIgnoreCase("Valid")) //DATA is valid-login success-test pass-logout
										//DATA is valid-login failed-test fail
	{
		
		if(targetPage==true)
		{
			macc.clickLogout();
			Assert.assertTrue(true);

		}
		else
		{
		Assert.assertTrue(false);

		}
	}
	if(exp.equalsIgnoreCase("InValid"))
{
		
		if(targetPage==true)
		{
			macc.clickLogout();
			Assert.assertTrue(false);
		}
		else
		{
			Assert.assertTrue(true);

		}
	}
	
	}catch(Exception e) {
		 Assert.fail();
	}
logger.info("****************Finish TC003_LoginDDT ********");

}
}
