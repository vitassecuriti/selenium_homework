package homework_19.utils.objects;

import homework_19.utils.Utils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by VSKryukov on 16.12.2016.
 */
public class CartPageObject {
    private WebDriver driver;
    private WebDriverWait wait;

    public CartPageObject(WebDriver driver) {
        this.driver = driver;
        wait  = new WebDriverWait(driver, 3);
    }

    public CartPageObject cleanCart() {
        List<WebElement> shortcuts = driver.findElements(By.cssSelector("ul.shortcuts li.shortcut"));
        for (int i = 1; i <= shortcuts.size(); i++) {
            if (i != shortcuts.size()) {
                driver.findElement(By.cssSelector("ul.shortcuts li.shortcut:first-child")).click();
            }
            String ItemName = driver.findElement(By.cssSelector("ul.items li:first-child div a")).getAttribute("textContent");
            Utils utils = new Utils();
            WebElement productSumCheck = utils.getElementByCellText(driver, By.cssSelector("table.dataTable.rounded-corners td.item"), ItemName);

            if (i != shortcuts.size()) {
                driver.findElement(By.cssSelector("ul.shortcuts li.shortcut:first-child")).click();
            }
            driver.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
            wait.until(ExpectedConditions.stalenessOf(productSumCheck));

            Assert.assertTrue("Товар не удален из корзины", utils.getElementByCellText(driver, By.cssSelector("table.dataTable.rounded-corners td.item"), ItemName) == null);
        }
        return  this;
    }


}
