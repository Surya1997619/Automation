package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
//import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceBaseResolver;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;

	public void onStart(ITestContext testContext) {

		/*
		 * SimpleDateFormat df = new SimpleDateFormat("yYYy-MM.dd. HH.mm. ss");//import java.text.SimpleDateFormat; 
		 * Date dt = new Date(); String currentdatetimestamp
		 * = df.format(dt);
		 */

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		
		repName="Test-Report-"+timeStamp +".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);// specify location of the report

		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("opencart Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
		System.out.println("checking");

		String os = testContext.getCurrentXmlTest().getParameter("os");// this will take the parameter in current XML
		extent.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");// this will take the parameter in current XML
		extent.setSystemInfo("Browser", browser);

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();// this will include he groups in current XML
		if (!includedGroups.isEmpty()) {//if group is there then include in the report
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());// get the corresponding class
		test.assignCategory(result.getMethod().getGroups()); // to display groups in ref 
		test. log(Status.PASS,result.getName()+" got successfully executed");
	}

	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());//from result get the class name, from class name get the name
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());//print the error in report

		
		 try { String imgPath = new BaseClass().captureScreen(result.getName());//make  driver as static so only one driver //get the screenshot and path through thebase class name 
		 test.addScreenCaptureFromPath(imgPath);//add the SS to report
		  } catch (IOException el) { el.printStackTrace(); }
		 
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);
		
		//open the report automatically
		try {//if extentReport is not there; so try
			Desktop.getDesktop().browse(extentReport.toURI());//Desktop is default type
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//send report to mail
		/*
		 * try { URL url = new
		 * URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName)
		 * ;//converting the report file to html by changing toURL formal // Create the
		 * email message ImageHtmlEmail email =new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.googlemai1.com");//this will work only for gmail
		 * email.setSmtpPort(465); email.setAuthenticator(new
		 * DefaultAuthenticator("suryaprakashselvaraj619@gmail.com","Stark@619"));
		 * email.setSSLOnConnect(true);
		 * email.setFrom("suryaprakashselvaraj619@gmail.com"); //Sender
		 * email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report....");
		 * email.addTo("pavankumar.busyqa@gmail.com"); //Receiver email.attach(url,
		 * email.attach(url,"extent report","please check report..."); email. send(); //
		 * send the email } catch (Exception e) { e.printStackTrace(); }
		 */
				 
	}
}


 