import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

//1.Launch the website - https://www.makemytrip.com/
//2.Click on “Flights”
//3.Choose From and To Values
//4.Select the Travel date
//5.Click Search
//6.Print the “Price” values in ascending order
//7.Choose the lowest fare bus and capture screenshot
//8.Click “Select seats” button and put explicit wait.
//9.Click the required seat
//10.Click “Book Seat”
//11.Assert the From and To Value
//12.Close the browser
public class Mmp {
	static WebDriver driver;
	static String baseURL;
	static String toCity = "DEL";
	static String fromCity = "PNQ";
	static int min =9999999;

	public static void main(String[] args) {
		initializeDriver();
		try {

			chooseCheapestFlight();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.quit();
	}

	public static void initializeDriver() {
		driver = new ChromeDriver();
		baseURL = "https://www.makemytrip.com/";
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(baseURL);
	}

	public static void chooseCheapestFlight() throws InterruptedException {
		WebElement element = driver.findElement(By.xpath("//label[@for='fromCity']"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		
		// driver.findElement(By.xpath("//label[@for='fromCity']")).click();
		driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys(fromCity);
		Thread.sleep(2000);
		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(text(),'" + fromCity + "')]")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys(toCity);
		Thread.sleep(2000);
		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(text(),'" + toCity + "')]")));
		Thread.sleep(2000);

		chooseDate(5,executor);
		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[normalize-space()='Search']")));
		//driver.findElement(By.xpath("//a[normalize-space()='Search']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[contains(text(),'OKAY, GOT IT!')]")).click();
		Thread.sleep(2000);
		List<WebElement> itinaries = driver.findElements(By.xpath("//div[@class='listingCard']"));
		itinaries.forEach(p -> {
		int priceInteger =	priceInInteger(p.findElement(By.xpath(".//div[@class='priceSection']//p")).getText());
		if(priceInteger<min) {
			min=priceInteger;
		}
		});
		System.out.println("cheapest price->"+min);
		//choosing the lowest fare bus 
		for(int i=0;i<itinaries.size();i++) {
			if(priceInInteger(itinaries.get(i).findElement(By.xpath(".//div[@class='priceSection']//p")).getText())==min) {
				itinaries.get(i).findElement(By.xpath(".//div[@class='makeFlex perfectCenter']//button")).click();
				List<WebElement> bookNow = itinaries.get(i).findElements(By.xpath("./following-sibling::div[@class='collapse show']//button[contains(text(),'Book Now')]"));
				bookNow.get(0).click();
				break;
			}
		}
		switchToWindow();
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Review your booking')]")));
		
		try {
			takeSnapShot(driver) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> journeyID = driver.findElements(By.xpath("//div[starts-with(@id,'journey_')]//div//p//span"));
		
		Assert.assertEquals(fromCity+"-"+toCity, journeyID.get(0).getText());
		System.out.println("Itinary selected is --> "+journeyID.get(0).getText());
	}

	public static void chooseDate(int days, JavascriptExecutor executor) {
		List<WebElement> dates = driver
				.findElements(By.xpath("//div[@class='DayPicker-Week']/div[@class='DayPicker-Day']"));
		executor.executeScript("arguments[0].click();", dates.get(days));
	}

	public static int priceInInteger(String price) {
		String priceNew = "";
		for (int i = 0; i < price.length(); i++) {
			if (Character.isDigit(price.charAt(i))) {
				priceNew = priceNew + price.charAt(i);
			}
		}
		System.out.println(priceNew);
		return Integer.parseInt(priceNew);
	}
	public static void takeSnapShot(WebDriver webdriver) throws Exception{
		TakesScreenshot scrShot =((TakesScreenshot)webdriver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File(System.getProperty("user.dir")+"/src/test/resources/screenshots/test.png");
		FileUtils.copyFile(SrcFile, DestFile);
		}
	public static void switchToWindow() {
		String parent=driver.getWindowHandle();
		Set<String>s=driver.getWindowHandles();
		Iterator<String> I1= s.iterator();
		while(I1.hasNext())
		{
		String child_window=I1.next();
		if(!parent.equals(child_window))
		{
		driver.switchTo().window(child_window);
		System.out.println(driver.switchTo().window(child_window).getTitle());
		}
	}
}
}
