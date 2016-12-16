package homework_19.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by VSKryukov on 16.12.2016.
 */
public class Utils {
    public Utils() {
    }

    public WebElement getElementByCellText(WebDriver driver, By locator, String text){
        WebElement element = null;
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement cell : elements ){
            if (cell.getText().equals(text)){
                element = cell;
            }
        }
        return element;
    }
}
