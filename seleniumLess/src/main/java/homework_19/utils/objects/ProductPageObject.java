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
public class ProductPageObject {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPageObject(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 3);
    }

    public ProductPageObject addProduct() {
        List<WebElement> selectSize = driver.findElements(By.cssSelector("select[name='options[Size]']"));
        if (selectSize.size() > 0) {
            Select select = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
            select.selectByIndex(1);
        }
        WebElement addProductButton = driver.findElement(By.cssSelector("button[name='add_cart_product']"));
        addProductButton.click();
        return this;
    }

    public MainPageObject toHome(){
        WebElement addProductButton = driver.findElement(By.cssSelector("button[name='add_cart_product']"));
        driver.findElement(By.cssSelector("nav#site-menu li.general-0")).click();
        wait.until(ExpectedConditions.stalenessOf(addProductButton));
        return new MainPageObject(driver);
    }


}
