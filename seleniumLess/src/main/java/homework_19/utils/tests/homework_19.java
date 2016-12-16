package homework_19.utils.tests;

import homework_19.utils.TestBase;
import homework_19.utils.objects.CartPageObject;
import homework_19.utils.objects.MainPageObject;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by VSKryukov on 16.12.2016.
 */
public class homework_19 extends TestBase{


    @Before
    public void st(){
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 3);
    }

    @Test
    public void testPageObject(){

        MainPageObject mainPageObject = new MainPageObject(driver);
        mainPageObject.open().addSomeProductToCart(3);
        CartPageObject cartPageObject = mainPageObject.checkOut();
        cartPageObject.cleanCart();
    }


}
