import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import static java.lang.Thread.sleep;

/**
 * Created by VSKryukov on 10.12.2016.
 */
public class RemoteTestHomework {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setPlatform(Platform.WINDOWS);
        capability.setCapability("build", "JUnit - Sample");
        driver = new RemoteWebDriver(
                new URL("https://vitas2:VAp3oYTjNL33whV8z3LU@hub-cloud.browserstack.com/wd/hub"),
                capability
        );
    }

    @Test
    public void testSimple() throws Exception {
        driver.get("http://www.google.com");
        System.out.println("Page title is: " + driver.getTitle());
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();

        driver.get("http://www.yandex.ru");
        sleep(10000);


        driver.get("http://www.rambler.ru");
        sleep(10000);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
