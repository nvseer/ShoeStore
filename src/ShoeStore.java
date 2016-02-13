import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by rvseer on 2/11/2016.
 */
public class ShoeStore {

    WebDriver driver;
    PageActions pa;
    String URL = "http://shoestore-manheim.rhcloud.com/";

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethod(String browser) throws Exception {
        if(browser.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }
        else if(browser.equalsIgnoreCase("chrome")){
            String chrome = "webdriver.chrome.driver";
            String locationChrome = "C:\\chromedriver.exe";
            System.setProperty(chrome,locationChrome);
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("ie")){
            String ie = "webdriver.ie.driver";
            String locationIe = "C:\\IEDriverServer.exe";
            System.setProperty(ie,locationIe);
            driver = new InternetExplorerDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(URL);
        driver.manage().window().maximize();
    }

    @Test (description = "Monthly display of new releases")
    public void Story_1() {
        pa = PageFactory.initElements(driver, PageActions.class);
        pa.validateMonths(driver);
        }

    @Test (description = "Submit email for reminder")
    public void Story_2(){
        pa = PageFactory.initElements(driver, PageActions.class);
        pa.submitEmail(driver);
    }

    @AfterMethod
    public void afterMethod(){
        driver.close();
    }

}

