package basePackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class TestBase {


	public static WindowsDriver AppTestSession=null;
	public static WindowsDriver DesktopSession=null;
	
	public static String cmd_killExistingApplicationInstances = "wmic process where name='SampleWpfApp1.exe' delete";
	
	public static WebDriverWait wait;
	public static Properties prop;
	public static Logger logg;
	public static String propertiesFileLocation = System.getProperty("user.dir")+"\\src\\main\\java\\com\\bp\\qa\\config\\project.properties";
	public TestBase()
	{
		try {
			prop = new Properties();
			FileInputStream file = new FileInputStream(propertiesFileLocation);
			prop.load(file);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch ( IOException e) {
			e.printStackTrace();
		}
	} 

	public static void initialization()
	{
		try { 
		  Process p1 = Runtime.getRuntime().exec("cmd /c "+cmd_killExistingApplicationInstances);
		  p1.waitFor();
		
			String Log4JConfPath= prop.getProperty("Log4JConfPath");
			PropertyConfigurator.configure(Log4JConfPath);
			logg = Logger.getLogger("devpinoyLogger");
			String AppFullPath = System.getProperty("user.dir")+"//"+prop.getProperty("AppPath");
			System.out.println("The App Full Path : "+AppFullPath);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			logg.info("capabilities1 object of DesiredCapabilities class is created");
			capabilities.setCapability("app",AppFullPath);
			capabilities.setCapability("ms:experimental-webdriver", true);
			capabilities.setCapability("ms:waitForAppLaunch", "25");
			logg.info("Capabilities are set for DesiredCapabilities class"); 
			try
			{AppTestSession = new WindowsDriver<WindowsElement>(new URL(prop.getProperty("WinAppServerCommandsListeningURL")), capabilities); 
			}catch(Exception e)
			{AppTestSession = new WindowsDriver<WindowsElement>(new URL(prop.getProperty("WinAppServerCommandsListeningURL")), capabilities); 
			}
			logg.info("WindowsDriver object AppTestSession1 is created");
			Assert.assertNotNull(AppTestSession);
			AppTestSession.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			logg.info("Implicit timeout is set");
			AppTestSession.manage().window().maximize();
			logg.info("Window Maximized is set");
//======================================================================================================================
			DesiredCapabilities appCapabilities = new DesiredCapabilities(); 
			logg.info("appCapabilities object of DesiredCapabilities class for TopLevel element handling is created"); 
			appCapabilities.setCapability("app", "Root");
			appCapabilities.setCapability("deviceName", "WindowsPC");
			appCapabilities.setCapability("platformName", "Win10");
			logg.info("capability.setCapability with app = Root");
			DesktopSession = new WindowsDriver<WindowsElement>(new URL(prop.getProperty("WinAppServerCommandsListeningURL")), appCapabilities);
			logg.info("DeskTopSession a WindowsDriver object is created"); 
			Assert.assertNotNull(DesktopSession);
					

		}catch(Exception e)
		{
			e.printStackTrace();
		}


	}

}
