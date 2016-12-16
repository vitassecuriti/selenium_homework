package homework_19.utils.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by VSKryukov on 16.12.2016.
 */
public class MainPageObject {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPageObject(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, 3);
    }

    public MainPageObject open(){
        driver.get("http://localhost/litecard/en/");
        return this;
    }

    public ProductPageObject getAnyOneProduct(){
        driver.findElement(By.cssSelector("div#box-most-popular li:first-child")).click();
        return new ProductPageObject(driver);
    }

    public MainPageObject addSomeProductToCart(Integer count){
        for (int i=1;i<=count;i++){

            //WebElement counterElem = driver.findElement(By.cssSelector("div#cart span.quantity"));
            ProductPageObject productPageObject = getAnyOneProduct();
            productPageObject = productPageObject.addProduct();
            wait.until(ExpectedConditions.attributeContains((driver.findElement(By.cssSelector("div#cart span.quantity"))),
                    "textContent", String.valueOf(i)));
            productPageObject.toHome();

        }
        return this;
    }


    public CartPageObject checkOut (){
        driver.findElement(By.cssSelector("a.link[href $= 'checkout']")).click();
        return new CartPageObject(driver);
    }


}
