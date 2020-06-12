package testCasesPackage;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



import basePackage.TestBase;

public class TestCases extends TestBase {
	
	
	public TestCases()
	{
		super();
	}
	
	@BeforeMethod
	public void setup()
	{
		initialization();
		
	}
	@AfterMethod
	public void teardown() throws IOException, InterruptedException
	{
		logg.info("Quitting Application ");
		AppTestSession.quit();
		DesktopSession.quit();
		logg.info("Deleting the created project");
		
		Process p1 = Runtime.getRuntime().exec("cmd /c "+cmd_killExistingApplicationInstances);
		p1.waitFor(); 
	}
	
	@Test
			public void AddNameToTextBox()
			{
			    WebElement txtName = AppTestSession.findElementByAccessibilityId("txtName");
			    txtName.sendKeys("Matteo");
			    logg.info("sending the name in name text box");
			    AppTestSession.findElementByAccessibilityId("sayHelloButton").click();
			    logg.info("Clicked on Say Hello button");
			    WebElement txtResult = AppTestSession.findElementByAccessibilityId("txtResult");
			    logg.info("Now checking if Greeting message has correct name or not");
			    
			    Assert.assertEquals(txtResult.getText(), "Hello "+txtName.getText());
			}

}
