package homework_19.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static java.lang.System.setProperty;

/**
 * Created by VSKryukov on 16.12.2016.
 */
public class TestBase {
    public WebDriver driver;



    @Before
    public void setUp() {
        String path = "./src/main/resources/driver/chrome/chromedriver.exe";
        setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }
    
    @After
    public void tearDown(){
        driver.quit();
    }
}
