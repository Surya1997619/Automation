package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger; //log4j
import org.apache.logging.log4j.LogManager; //log4j

 
public class BaseClass {
	public static WebDriver driver;//making static for taking SS
	public Logger logger;
	public Properties p; // to get the data from property file

	@BeforeClass(groups= {"Regression","Sanity", "Master"})
	@Parameters({"OS", "browser"})
	public void setup(String os, String br) throws IOException {
		
		FileReader file=new FileReader("./src//test//resources//config.properties");// to get the data from property file
		p=new Properties();// to get the data from property file
		p.load(file);// to get the data from property file
		
		logger=LogManager.getLogger(this.getClass());// this is the log file variable
		
		//if remote
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			//String huburl = "http://localhost: 4444/wd/hub";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
				capabilities.setPlatform(Platform.WIN11);
			else if(os.equalsIgnoreCase("mac"))
				capabilities.setPlatform(Platform.MAC);
			else if(os.equalsIgnoreCase("linux"))
				capabilities.setPlatform(Platform.LINUX);
			else
				System.out.print("No Maching OS");
				
			//browser
				switch(br.toLowerCase()) {
				case "chrome":capabilities.setBrowserName("chrome");break;
				case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
				case "firefox":capabilities.setBrowserName("firefox");break;

				default:System.out.println("No Matching browser"); return;
				}
			
			 driver=new RemoteWebDriver(new URL("http://192.168.1.2:4444/wd/hub"),capabilities );
			/* ===================
			 * String huburl = "http://localhost: 4444/wd/hub";
               DesiredCapabilities cap = new DesiredCapabilities();
                cap. setPlatform(Platform.WIN11); //cap. setPlatform(Platform.MAC); cap setBrowserName ("chrome"); //cap. setBrowserName ("MicrosoftEdge")
               WebDriver driver = new RemoteWebDriver (new URL (hubur]), cap);
               =============
			*/
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch(br.toLowerCase()) {
			case "chrome":driver = new ChromeDriver();break;
			case "edge":driver = new EdgeDriver();break;
			case "firefox":driver = new FirefoxDriver();break;
			default:System.out.println("Invalid browser name"); return;
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));// Reading url from property file
		driver.manage().window().maximize();

	}
	@AfterClass(groups= {"Regression","Sanity", "Master"})
	public void teardown() {
		driver.quit();
	}
	public String randomString() {
		String generatedstring= RandomStringUtils.randomAlphabetic(5);
		return generatedstring;	
	}
	public String randomNumber() {
		String generatednumber= RandomStringUtils.randomNumeric(10);
		return generatednumber;	
	}
	public String randomAlphaNumeric() {
		String generatedstring= RandomStringUtils.randomAlphabetic(5);
		String generatednumber= RandomStringUtils.randomNumeric(10);
		return (generatedstring+generatednumber);	
	}
	
	
	public String captureScreen (String tname) throws IOException {
		
		String timeStamp = new SimpleDateFormat("УУУУМMddhhmmss").format(new Date()) ;
		
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty ("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp+".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		return targetFilePath;//Based on the filepath it will be aaded in the report 
	}
	

}
