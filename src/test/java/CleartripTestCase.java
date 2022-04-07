import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class CleartripTestCase {
	
	WebDriver driver;
	String baseURL;
	
	
	@Test
	public void flightbooking() throws Exception
	
	{
		
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		
		driver = new ChromeDriver(options);
		baseURL = "https://www.cleartrip.com/";
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseURL);
		
		WebElement roundTrip = driver.findElement(By.id("RoundTrip"));
		roundTrip.click();
		
		Thread.sleep(4000);
		
		String partialText = "kolk";
		String textToSelect = "Kolkata, IN - Netaji Subhas Chandra Bose Airport (CCU)";
		
		WebElement textField = driver.findElement(By.id("FromTag"));
		textField.sendKeys(partialText);
		
		List<WebElement> list = driver.findElements(By.id("ui-id-1"));
		Thread.sleep(4000);

	       for (WebElement ele : list)
	 
	       {
	 
	          // for every elements it will print the name using innerHTML
	 
	          System.out.println("Values " + ele.getAttribute("innerHTML"));
	 
	          // Here we will verify if link (item) is equal to Java Script
	 
	          if (ele.getAttribute("innerHTML").contains("Kolkata")) {
	 
	             // if yes then click on link (iteam)
	             ele.click();
	             // break the loop or come out of loop
	 
	             break;
	 
	          }
	 
	       }
	       
			
			String partialTextt = "delhi";
			
			
			
			WebElement textField2 = driver.findElement(By.id("ToTag"));
			textField2.sendKeys(partialTextt);
			
			List<WebElement> listtt = driver.findElements(By.id("ui-id-2"));
			Thread.sleep(4000);

		       for (WebElement elementt : listtt)
		 
		       {
		 
		          // for every elements it will print the name using innerHTML
		 
		          System.out.println("Values " + elementt.getAttribute("innerHTML"));
		 
		          // Here we will verify if link (item) is equal to Java Script
		 
		          if (elementt.getAttribute("innerHTML").contains("New Delhi")) {
		 
		             // if yes then click on link (iteam)
		             elementt.click();
		             // break the loop or come out of loop
		 
		             break;
		 
		          }
		 
		       }
		       
		       
//		       WebElement dateToSelect = driver.findElement(By.xpath("//table[@class='calendar']//tr//td/a[contains(@class,'ui-state-default ui-state-highlight ui-state-active ')]"));
//				// Click the date
//		       
//		     String date = dateToSelect.getText();
//		     
//		     int number = Integer.parseInt(date);
//		    // System.out.println(number);
//		     int datetoSelected = number + 2;
//		//  String s=String.valueOf(datetoSelected);
//		   String s= Integer.toString(datetoSelected);
//          
//		     List<WebElement> defaultdate = driver.findElements(By.xpath("//a[@class = 'ui-state-default ']"));
//		     
//		     for (WebElement ei : defaultdate )
//		     {
//		    	 if(ei.getText().equalsIgnoreCase(s))
//		    	 {
//		    		 ei.click();
//		    		 break;
//		    		 
//		    	 }
//		    	  
//		     }
		       
		       selectDate(driver, 2);
				waitForSeconds(1000);
				selectDate(driver, 5);
				
				 WebElement searchFlight = driver.findElement(By.xpath("//input[@class='booking large mt15']"));
				 searchFlight.click();
	

	}
	
	public void waitForSeconds(int timeInMiliSeconds) {
		try {
			Thread.sleep(timeInMiliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    public void selectDate(WebDriver driver, int numberOfDaysFromNow) {
   		List<WebElement> availableDates = driver.findElements(By.xpath("//td[@data-handler = 'selectDay']"));
   		availableDates.get(numberOfDaysFromNow).click();

   	}
  
		}

   

