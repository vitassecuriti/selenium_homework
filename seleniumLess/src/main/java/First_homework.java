import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
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


    @Test
    public void checkSortCountriesAndZone(){
        //LogIn
        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Find countries    ----------------------------------------------------------------
        driver.get("http://localhost/litecard/admin/?app=countries&doc=countries");
        List<WebElement> countries = driver.findElements(By.cssSelector("tr.row"));
        List<String> countrieName = new ArrayList<>();
        List<String> codeCountries = new ArrayList<String>();

       for (int i=2; i<=countries.size() + 1;i++){

         WebElement countrueElem = driver.findElement(By.cssSelector("tr.row:nth-of-type(" + i +") td:nth-of-type(5)"));
           String str = countrueElem.getAttribute("textContent");
           //System.out.println("Countrie name is - " + str);
           countrieName.add(str);
           int countZone = 0;

           try {
               countZone = Integer.parseInt(driver.findElement(By.cssSelector("tr.row:nth-of-type(" + i + ") td:nth-of-type(6)")).getAttribute("textContent"));
           } catch (Exception ex){
               //System.out.println("не удалось преобразовать значение в ");
           }

           if (countZone > 0){
               codeCountries.add(driver.findElement(By.cssSelector("tr.row:nth-of-type(" + i + ") td:nth-of-type(4)")).getAttribute("textContent"));
           }

       }
        //check sort list countries
        List<String> sortedListCountries = new ArrayList<>();
        sortedListCountries.addAll(countrieName);
        Collections.sort(sortedListCountries);

        Assert.assertEquals("Страны не отсортированы по алфавиту", countrieName, sortedListCountries);

        //check sort geo zone from countries, if count zone > 0
        for (int i = 0; i<codeCountries.size();i++) {
            //System.out.println("Countries code - " + codeCountries.get(i));
            driver.get("http://localhost/litecard/admin/?app=countries&doc=edit_country&country_code=" + codeCountries.get(i));
            List<WebElement> zoneListWE = driver.findElements(By.cssSelector("td input[name $= '][id]']"));
            List<String> zoneId = new ArrayList<>();
            List<String> geoZoneName = new ArrayList<>();
            zoneListWE.forEach(we -> zoneId.add(we.getAttribute("value")));
            for (String id : zoneId) {
                WebElement countrueElem = driver.findElement(By.cssSelector("td input[name = 'zones[" + id + "][name]']"));
                String str = countrueElem.getAttribute("value");
              //  System.out.println("Zone name is - " + str);
                geoZoneName.add(str);
            }

            System.out.println("Сортировка зонн - " + codeCountries.get(i));
            //check sort list geoZone
            List<String> sortedListZones = new ArrayList<>();
            sortedListZones.addAll(geoZoneName);
            Collections.sort(sortedListZones);

            Assert.assertEquals("Геозоны не отсортированы по алфавиту", geoZoneName, sortedListZones);
           // System.out.println("Сортировка зонн  окончание- " + codeCountries.get(i));
        }

        //Check sort Zone
        driver.get("http://localhost/litecard/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> zones = driver.findElements(By.cssSelector("tr.row"));
        List<String> zonesNameList = new ArrayList<>();

        for (int i=2; i<=zones.size()+1;i++){

            WebElement zoneElem = driver.findElement(By.cssSelector("tr.row:nth-of-type(" + i +") td:nth-of-type(3)"));
            String str = zoneElem.getAttribute("textContent");
            //System.out.println("Zone name is - " + str);
            zonesNameList.add(str);

        }
        //check sort list Zone
        List<String> sortedListZones = new ArrayList<>();
        sortedListZones.addAll(zonesNameList);
        Collections.sort(sortedListZones);

        Assert.assertEquals("Зоны не отсортированы по алфавиту", zonesNameList, sortedListZones);

    }


    @After
    public void tear_down(){
       driver.quit();
    }

}
