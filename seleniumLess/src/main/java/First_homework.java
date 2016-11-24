import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//        String path = "C:\\selenium_homework\\seleniumLess\\src\\main\\resources\\driver.firefox\\geckodriver.exe";
//        System.setProperty("webdriver.gecko.driver", path);
//        DesiredCapabilities dcap = new DesiredCapabilities();
//        driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\Program Files\\Nightly\\firefox.exe")), new FirefoxProfile(), dcap);
    }

    @Test
    public void simpleTest(){
        driver.get("http://google.com");
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

    @Test
    public void TestClickAllLinks(){
        //LogIn
        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();


        //Find and check links from left part
        driver.get("http://localhost/litecard/admin/?app");
        List<WebElement> leftListTopLink = driver.findElements(By.cssSelector("ul#box-apps-menu a[href ^=http]"));
       // System.out.println(" - leftListTopLinkCount - " + leftListTopLink.size());

        //Click TopItemMenu
        for (int i = 1; i <= leftListTopLink.size(); i++){
       //  WebElement w = driver.findElement(By.xpath("//*[@id=\"app-\"]["+ i +"]"));
            System.out.println(" - NumberItemMenuTop - " + i);
            WebElement w = driver.findElement(By.cssSelector("#app-:nth-of-type(" + i + ")"));
            //*[@id="app-"]/a  //*[@id="app-"]
            System.out.println(" - CliCK OnTOpMenuItemNumber - " + i);
            w.click();

            //Check h1 exists
            int countH1 = driver.findElements(By.cssSelector("td#content h1")).size();
            Assert.assertTrue("Заголовок отсутствует на странице", countH1 > 0);

            //Click subItems if exists
            List<WebElement> listSubItems = driver.findElements(By.cssSelector("#app-:nth-of-type(" + i + ") li"));
            if (listSubItems.size()==0) {
            } else {
                //System.out.println(" - subItemsCount - " + listSubItems.size());
                for (int j = 1; j <= listSubItems.size(); j++) {
                    WebElement subItem = driver.findElement(By.cssSelector("#app-:nth-of-type(" + i + ") li:nth-of-type(" + j +")"));
                    //System.out.println( "ClickMenutopItem - " + i + " - ClickSubItemNumber - " + j);
                    subItem.click();
                }
            }
        }

        //LogOut
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div[2]/a[5]")).click();
    }

    @Test
    public void checkStickersMainPage(){
        //LogIn
        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Find and check productBox
        driver.get("http://localhost/litecard/en/");
        List<WebElement> productBox = driver.findElements(By.cssSelector("div.box"));
         //System.out.println(" - countProductBox - " + productBox.size());

        for (int i = 1; i <= productBox.size(); i++){

          //  System.out.println(" - NumberBroductBox - " + i);

            int countUl = driver.findElements(By.cssSelector("div.box:nth-of-type(" + i + ") ul.products")).size();
            if (countUl > 0) {

                //check subItems if exists
                List<WebElement> listProductItem = driver.findElements(By.cssSelector("div.box:nth-of-type(" + i + ") li"));
                if (listProductItem.size() != 0) {
                //System.out.println(" - subItemsCount - " + listProductItem.size());
                    for (int j = 1; j <= listProductItem.size(); j++) {
                        int stickersCount = driver.findElements(By.cssSelector("div.box:nth-of-type(" + i + ") li:nth-of-type(" + j + ") div[class ^=sticker]")).size();
                      //  System.out.println( "ClickMenutopItem - " + i + " - ClickSubItemNumber - " + j);
                        Assert.assertTrue("Продукт без стикера", stickersCount == 1);

                    }
                }
            }
        }

    }



    @After
    public void tear_down(){
       driver.quit();
    }

}
