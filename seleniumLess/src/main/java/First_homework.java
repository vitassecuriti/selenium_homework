import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by VSKryukov on 16.11.2016.
 */
public class First_homework {

    private WebDriver driver;

    @Before
    public void setUp(){
        String path = "./src/main/resources/driver/chrome/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
    }

    @Test
    public void simpleTest(){
        driver.get("https://www.google.ru");
        WebElement input = driver.findElement(By.xpath("//*[@id=\"lst-ib\"]"));
        input.click();
        input.sendKeys("Selenium");
        driver.findElement(By.xpath("//*[@id=\"sblsbb\"]/button")).click();
    }

    @Test
    public void TestloginPage(){
        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div[2]/a[5]")).click();
    }


    @After
    public void tear_down(){
       driver.quit();
    }

}
