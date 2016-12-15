import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.setOut;
import static java.lang.System.setProperty;

/**
 * Created by VSKryukov on 15.12.2016.
 */
public class HomeworkWithLogs {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp(){
        String path = "./src/main/resources/driver/chrome/chromedriver.exe";
        setProperty("webdriver.chrome.driver", path);

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
   }


   @Test
    public void checkLogs(){
       driver.get("http://localhost/litecard/admin/");
       driver.findElement(By.name("username")).sendKeys("admin");
       driver.findElement(By.name("password")).sendKeys("admin");
       driver.findElement(By.name("login")).click();

       driver.get("http://localhost/litecard/admin/?app=catalog&doc=catalog&category_id=1");

       List<WebElement> elements = driver.findElements(By.cssSelector("tr.row td:nth-of-type(3)"));
       for (int i = 2; i <= elements.size() + 1; i++) {
           driver.get("http://localhost/litecard/admin/?app=catalog&doc=catalog&category_id=1");
           if ((driver.findElements(By.cssSelector("tr.row:nth-of-type(" + i + ") td:nth-of-type(3) i"))).size() == 0){
               driver.findElement(By.cssSelector("tr.row:nth-of-type(" + i + ") td:nth-of-type(3) a")).click();
               wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("h1"))));
               List<LogEntry> browser = driver.manage().logs().get("browser").getAll();
               browser.forEach(logEntry -> System.out.println(logEntry.toString()));
               Assert.assertTrue("Логи браузера не пусты", browser.size()==0);

           }
         }
       System.out.println("Test End");

   }

    @After
    public void tear_down(){
        driver.quit();
    }
}


