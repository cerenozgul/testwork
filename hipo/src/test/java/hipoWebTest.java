
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;

//My first test automation exercise and firt time using Selenium, thanks for your time and attention.


public class hipoWebTest {
    protected WebDriver driver;
    public static String eneteredUrl = "https://www.google.com.tr/";

    @Before
    public void setUp() {

        try {

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            System.setProperty("webdriver.chrome.driver", "C:\\\\Users\\\\CerenOzgul\\\\IdeaProjects\\\\hipo\\\\driver\\\\chromedriver.exe");
            driver = new ChromeDriver(capabilities);

            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

            //dynamic wait
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void searchClick() {
        WebElement element = driver.findElement(By.name("q"));
        ((WebElement) element).sendKeys("Hipo Labs");
        try {

            driver.get(eneteredUrl);
            driver.findElement(xpath("//input[@name='oneGoogleBarEndOfBody']")).sendKeys("Hipo Labs");
            sleep(5000);

// In order to verify that searched HIPO LABS sendKey is verified and listed accordingly.

            WebElement matchingResult= driver.findElement(By.xpath(".//div[@class='sfcnt']"));
            List<WebElement> listResult= matchingResult.findElements(By.xpath("//li/div/div[@class='GyAeWb']"));
            System.out.println(listResult.size());

//if you want to print matching results
            for(WebElement results: listResult)
            {
                String value= results.getText();
                System.out.println(value);
            }

//Click element "Hipolabs"
            driver.findElement(xpath("//div[contains(text(),'Hipo Labs')]")).click();
            sleep(5000);
            driver.quit();
            //Click "Team" menu in the Hipo Labs website
            driver.findElement(xpath("./[@class='bg team']")).click();
            sleep(5000);
            driver.quit();
// Verify that page has APPYL NOW text. (There is no Apply Now  text but Apply For Jobs and Apply for Internship is included.)

            WebElement searchApply = driver.findElement(By.name("q"));
            ((WebElement) searchApply).sendKeys("APPLY NOW");
//Take screenshot of APPLY
            TakesScreenshot scrShot =((TakesScreenshot)driver);
            File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            driver.get("https://hipolabs.com/team/");

            //Call take screenshot function

            this.takeSnapShot(driver, "c://testApplyimg.png") ;

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void takeSnapShot(WebDriver driver, String fileWithPath) throws IOException {
        //Converts driver object to the TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        //Calls getScreenshotAs method to create img file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(fileWithPath);//Moves image file to new destination
        FileUtils.copyFile(SrcFile, DestFile);//Copy file at destination

    }

    @After
    public void tearDown() throws Exception {

    }
}

