package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[text()='My Account']")
	WebElement accountheading;

	@FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")//added in step 6 for logout
	WebElement clkLogout;
	
	//a[@class='list-group-item'][normalize-space()='Logout']
	public boolean isMyAccountExists() {
		try {
			return accountheading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	public void clickLogout() {
		clkLogout.click();
	}
}