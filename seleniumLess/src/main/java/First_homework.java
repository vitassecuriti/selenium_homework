import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static java.lang.System.setOut;
import static java.lang.System.setProperty;

/**
 * Created by VSKryukov on 16.11.2016.
 */
public class First_homework {

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
            out.println(" - NumberItemMenuTop - " + i);
            WebElement w = driver.findElement(By.cssSelector("#app-:nth-of-type(" + i + ")"));
            //*[@id="app-"]/a  //*[@id="app-"]
            out.println(" - CliCK OnTOpMenuItemNumber - " + i);
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

            out.println("Сортировка зонн - " + codeCountries.get(i));
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

    @Test
    public void checkProductsStyles(){

        //Find and check productBox
        driver.get("http://localhost/litecard/en/");
        Map<String,String> itemFromMainPage = new HashMap<>();
        WebElement we =  driver.findElement(By.cssSelector("div#box-campaigns li:first-child"));
        itemFromMainPage.put("regular-price-tag", we.findElement(By.cssSelector(".regular-price")).getTagName());
        itemFromMainPage.put("regular-price", we.findElement(By.cssSelector(".regular-price")).getText());
        itemFromMainPage.put("regular-price-color", we.findElement(By.cssSelector(".regular-price")).getCssValue("color"));
        itemFromMainPage.put("regular-price-font-size", we.findElement(By.cssSelector(".regular-price")).getCssValue("font-size").replace("px",""));
        itemFromMainPage.put("campaign-price-tag", we.findElement(By.cssSelector(".campaign-price")).getTagName());
        itemFromMainPage.put("campaign-price", we.findElement(By.cssSelector(".campaign-price")).getText());
        itemFromMainPage.put("campaign-price-color", we.findElement(By.cssSelector(".campaign-price")).getCssValue("color"));
        itemFromMainPage.put("campaign-price-font-size", we.findElement(By.cssSelector(".campaign-price")).getCssValue("font-size").replace("px", ""));
        itemFromMainPage.put("product-name", we.findElement(By.cssSelector(".name")).getText());

        //Checks in main page
        Assert.assertEquals("Постоянная цена не зачеркнута",itemFromMainPage.get("regular-price-tag"),"s");
        Assert.assertEquals("Постоянная цена отличается по цвету от заданной",itemFromMainPage.get("regular-price-color"),"rgba(119, 119, 119, 1)");
        Assert.assertEquals("Акционная цена не жирная",itemFromMainPage.get("campaign-price-tag"),"strong");
        Assert.assertEquals("Акционная цена отличается по цвету от заданной",itemFromMainPage.get("campaign-price-color"),"rgba(204, 0, 0, 1)");
        Assert.assertTrue("Акционная цена не больше постоянной", Float.parseFloat(itemFromMainPage.get("regular-price-font-size")) < Float.parseFloat(itemFromMainPage.get("campaign-price-font-size")));


        driver.findElement(By.cssSelector("div#box-campaigns li:first-child")).click();

        Map<String,String> itemFromProductPage = new HashMap<>();

        itemFromProductPage.put("regular-price-tag", driver.findElement(By.cssSelector("div.price-wrapper .regular-price")).getTagName());
        itemFromProductPage.put("regular-price", driver.findElement(By.cssSelector("div.price-wrapper .regular-price")).getText());
        itemFromProductPage.put("regular-price-color", driver.findElement(By.cssSelector("div.price-wrapper .regular-price")).getCssValue("color"));
        itemFromProductPage.put("regular-price-font-size", driver.findElement(By.cssSelector("div.price-wrapper .regular-price")).getCssValue("font-size").replace("px",""));
        itemFromProductPage.put("campaign-price-tag", driver.findElement(By.cssSelector("div.price-wrapper .campaign-price")).getTagName());
        itemFromProductPage.put("campaign-price", driver.findElement(By.cssSelector("div.price-wrapper .campaign-price")).getText());
        itemFromProductPage.put("campaign-price-color", driver.findElement(By.cssSelector("div.price-wrapper .campaign-price")).getCssValue("color"));
        itemFromProductPage.put("campaign-price-font-size", driver.findElement(By.cssSelector("div.price-wrapper .campaign-price")).getCssValue("font-size").replace("px", ""));
        itemFromProductPage.put("product-name", driver.findElement(By.cssSelector("h1.title")).getText());

        //Checks in product's page
        Assert.assertEquals("Постоянная цена не зачеркнута",itemFromProductPage.get("regular-price-tag"),"s");
        Assert.assertEquals("Постоянная цена отличается по цвету от заданной",itemFromProductPage.get("regular-price-color"),"rgba(102, 102, 102, 1)");
        Assert.assertEquals("Акционная цена не жирная",itemFromProductPage.get("campaign-price-tag"),"strong");
        Assert.assertEquals("Акционная цена отличается по цвету от заданной",itemFromProductPage.get("campaign-price-color"),"rgba(204, 0, 0, 1)");
        Assert.assertTrue("Акционная цена не больше постоянной", Float.parseFloat(itemFromProductPage.get("regular-price-font-size")) < Float.parseFloat(itemFromProductPage.get("campaign-price-font-size")));

        Assert.assertEquals("Открыта страница не требуемого товара либо некорректное имя товара",itemFromMainPage.get("product-name"),itemFromProductPage.get("product-name"));

        Assert.assertEquals("Наименования товара не совпадают",itemFromMainPage.get("product-name"),itemFromProductPage.get("product-name"));
        Assert.assertEquals("Постоянные цены товара не совпадают",itemFromMainPage.get("regular-price"),itemFromProductPage.get("regular-price"));
        Assert.assertEquals("Акционные цены товара не совпадают",itemFromMainPage.get("campaign-price"),itemFromProductPage.get("campaign-price"));
    }

    @Test
    public void checkRegistrationNewUser(){

        driver.get("http://localhost/litecard/en/");

        driver.findElement(By.cssSelector("div#box-account-login a[href $= 'create_account']")).click();

        String strData = String.valueOf(Math.round(Math.random() * 10000000000d) );
        String firstName = "NewUserFirstName " + strData;
        String lastName = "NewUserLastName " + strData;
        String address1 = "NewUserAddress1 " + strData;
        String postCode = "T7S 1R3";
        String country = "Canada";
        String city = "Whitecourt";
        String zoneCode = "Ontario";
        String eMail = "test@" + strData + "test.ru";
        String tel = "+7"+strData;
        String pass = "12345asdf123";

        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[name='address1']")).sendKeys(address1);
        driver.findElement(By.cssSelector("input[name='postcode']")).sendKeys(postCode);
        driver.findElement(By.cssSelector("input[name='city']")).sendKeys(city);
        selectOption(By.cssSelector(".select2-container"), country);

        Select zoneCodSelect = new Select(driver.findElement(By.cssSelector("select[name='zone_code']")));
        zoneCodSelect.selectByVisibleText(zoneCode);


        driver.findElement(By.cssSelector("input[name='email']")).sendKeys(eMail);
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys(tel);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(pass);
        driver.findElement(By.cssSelector("input[name='confirmed_password']")).sendKeys(pass);
        driver.findElement(By.cssSelector("button[name='create_account']")).click();

        //System.out.println("Заполнили все");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("div#box-account a[href $= 'logout']")))).click();

        //System.out.println(" logout");

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("input[name='email']")))).sendKeys(eMail);
        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(pass);
        driver.findElement(By.cssSelector("button[name='login']")).click();

       // System.out.println("Enter data and login");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("div#box-account a[href $= 'logout']")))).click();
        //System.out.println(" logout");
        //out.println("eMail   - "  +  eMail + " :  pass - " + pass);
    }

    public void selectOption(By s2ConteinerLocator, String optionText) {

        driver.findElement(s2ConteinerLocator).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".select2-dropdown"))));

        driver.findElement(By.cssSelector(".select2-dropdown input")).sendKeys(optionText);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".select2-results .select2-results__option")))).click();

    }


    @Test
    public void checkAddNewproduct() {

        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecard/admin/?app=catalog&doc=catalog");

        String rndStrID = String.valueOf(Math.round(Math.random() * 1000d) );
        String newProductName = "newProductName_" + rndStrID;
        String newProductCode = "newProductCode_" + rndStrID;
        String subcategory = "Subcategory";
        String sold_out_status = "Temporary sold out";

        driver.findElement(By.cssSelector("div a[href $= 'edit_product']")).click();

        //Status
        checkRadioButton(By.cssSelector("div#tab-general input[name='status'][value='1']"));
        //Name
        driver.findElement(By.cssSelector("span.input-wrapper input[name='name[en]']")).sendKeys(newProductName);
        //Code
        driver.findElement(By.cssSelector("input[name='code']")).sendKeys(newProductCode);
        //Categories
        for (int i=0; i< driver.findElements(By.cssSelector("div.input-wrapper input[name='categories[]']")).size(); i++){
            checkRadioButton(By.cssSelector("input[name='categories[]'][value='" + i + "']"));
        }
        //Default Category
        simpeSelectOption(By.cssSelector("select[name='default_category_id']"), subcategory);
        //Product Groups
        for (int i=1; i<= driver.findElements(By.cssSelector("div.input-wrapper input[name='product_groups[]']")).size(); i++){
            checkRadioButton(By.cssSelector("input[name='product_groups[]'][value='1-" + i + "']"));
        }
        //Quantity
        WebElement quantity = driver.findElement(By.cssSelector("input[name='quantity']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', 5.55)", quantity);
        //Sold Out Status
        simpeSelectOption(By.cssSelector("select[name='sold_out_status_id']"), sold_out_status);
        //Upload Images
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("pictures/test.png").getFile());
        driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(file.getAbsolutePath());
        //Date Valid From
        driver.findElement(By.cssSelector("input[name='date_valid_from']")).sendKeys("01.12.2016");
        //Date Valid To
        driver.findElement(By.cssSelector("input[name='date_valid_to']")).sendKeys("01.01.2017");

        //Tab Information
        driver.findElement(By.cssSelector("div.tabs a[href='#tab-information']")).click();
        //Manufacturer
        simpeSelectOption(By.cssSelector("select[name='manufacturer_id']"), "ACME Corp.");
        //Supplier
       // simpeSelectOption(By.cssSelector("select[name='supplier_id']"), "ForTestHomework12");
        //Keywords
        driver.findElement(By.cssSelector("input[name='keywords']")).sendKeys("ForTestHomework12");
        //Short Description
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("ForTestHomework12");
        //Description
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("ForTestHomework12");
        //Head Title
        driver.findElement(By.cssSelector("input[name='head_title[en]']")).sendKeys("ForTestHomework12");
        //Meta Description
        driver.findElement(By.cssSelector("input[name='meta_description[en]']")).sendKeys("ForTestHomework12");


        //Tab Prices
        driver.findElement(By.cssSelector("div.tabs a[href='#tab-prices']")).click();
        //Purchase Price
        WebElement purchase_price = driver.findElement(By.cssSelector("input[name='purchase_price']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', 25.55)", purchase_price);
        simpeSelectOption(By.cssSelector("select[name='purchase_price_currency_code']"), "Euros");
        //Save
        driver.findElement(By.cssSelector("button[name='save']")).click();


        List<WebElement> products = driver.findElements(By.cssSelector("tr.row"));
        boolean IsExists = false;

        for (int i=2; i<=products.size()+1;i++){

            WebElement productElem = driver.findElement(By.cssSelector("tr.row:nth-of-type(" + i +") td:nth-of-type(3)"));
            String str = productElem.getAttribute("textContent").trim();
            if (str.equals(newProductName)){
                IsExists = true;
            }

        }

        Assert.assertTrue("Новый продукт не найден в списке продуктов", IsExists);

    }

    public void checkRadioButton(By locator){
        WebElement checkedElement = driver.findElement(locator);
        String checked = checkedElement.getAttribute("checked");
        if (checked == null){
            checkedElement.click();
        }
    }

    public void simpeSelectOption(By locator, String option){
        Select selectCategory = new Select(driver.findElement(locator));
        selectCategory.selectByVisibleText(option);
    }


    @Test
    public void checkAddDeleteFromCart(){
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.get("http://localhost/litecard/en/");

       // driver.findElement(By.cssSelector("div#box-most-popular li:first-child")).click();

        //counter
        int counter = Integer.parseInt(driver.findElement(By.cssSelector("span.quantity")).getAttribute("textContent"));
        Assert.assertTrue("Корзина не пуста в начале теста!", counter==0 );

        //add To cart
        wait = new WebDriverWait(driver, 3);
        for (int i=1;i<=3;i++){
            driver.findElement(By.cssSelector("div#box-most-popular li:first-child")).click();
            WebElement counterElem = driver.findElement(By.cssSelector("div#cart span.quantity"));

            //if need to select size
            List<WebElement> selectSize = driver.findElements(By.cssSelector("select[name='options[Size]']"));
            if (selectSize.size()>0){
                Select select = new Select(driver.findElement(By.cssSelector("select[name='options[Size]']")));
                select.selectByIndex(1);
            }

            WebElement addProductButton = driver.findElement(By.cssSelector("button[name='add_cart_product']"));
            addProductButton.click();
            wait.until(ExpectedConditions.attributeContains(counterElem, "textContent", String.valueOf(i)));
            driver.findElement(By.cssSelector("nav#site-menu li.general-0")).click();
            wait.until(ExpectedConditions.stalenessOf(addProductButton));
        }

        //enter to cart
        driver.findElement(By.cssSelector("a.link[href $= 'checkout']")).click();

        //delete from cart and checks
        List<WebElement> shortcuts = driver.findElements(By.cssSelector("ul.shortcuts li.shortcut"));
        for (int i=1; i<=shortcuts.size(); i++) {
            if (i!=shortcuts.size()) {
                driver.findElement(By.cssSelector("ul.shortcuts li.shortcut:first-child")).click();
            }
            String ItemName = driver.findElement(By.cssSelector("ul.items li:first-child div a")).getAttribute("textContent");
            WebElement productSumCheck = getElementByCellText(By.cssSelector("table.dataTable.rounded-corners td.item"), ItemName);

            if (i!=shortcuts.size()) {
                driver.findElement(By.cssSelector("ul.shortcuts li.shortcut:first-child")).click();
            }
            driver.findElement(By.cssSelector("button[name='remove_cart_item']")).click();
            wait.until(ExpectedConditions.stalenessOf(productSumCheck));

            Assert.assertTrue("Товар не удален из корзины", getElementByCellText(By.cssSelector("table.dataTable.rounded-corners td.item"), ItemName) == null);
        }
    }

    public WebElement getElementByCellText(By locator, String text){
        WebElement element = null;
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement cell : elements ){
            if (cell.getText().equals(text)){
                element = cell;
            }
        }
        return element;
    }

    @Test
    public void checkWorkWindows(){
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.get("http://localhost/litecard/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.get("http://localhost/litecard/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("a.button[href $= 'edit_country']")).click();

        String windowHandle = driver.getWindowHandle();
        for (int i=0; i<driver.findElements(By.cssSelector("form i.fa.fa-external-link")).size();i++) {
            WebElement webElement = driver.findElements(By.cssSelector("form i.fa.fa-external-link")).get(i);
            wait.until(ExpectedConditions.visibilityOf(webElement));
            webElement.click();

            String s = driver.getWindowHandles().stream().collect(Collectors.toList()).get(1);

            driver.switchTo().window(s);
            List<WebElement> listH1 = null;
            String curUrl = driver.getCurrentUrl();
            if (curUrl.contains("en.wikipedia.org")){
                listH1 = driver.findElements(By.cssSelector("h1#firstHeading"));
            } else if (curUrl.contains("informatica.com")){
                listH1 = driver.findElements(By.cssSelector("div.marketo-formContent.narrow > h1"));
            }
            Assert.assertTrue("Искомое окно не открыто, либо открыто не искомое окно",listH1.size()>0);
            driver.close();
            wait.until(ExpectedConditions.numberOfWindowsToBe(1));
            driver.switchTo().window(windowHandle);
            WebElement element = driver.findElement(By.cssSelector("input[name='name']"));
            element.click();
            element.clear();
            element.sendKeys(String.valueOf(i));
            element.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.cssSelector("input[name='name']")),"value"));
            String value = driver.findElement(By.cssSelector("input[name='name']")).getAttribute("value");
            Assert.assertTrue("", driver.findElement(By.cssSelector("input[name='name']")).getAttribute("value").equals(String.valueOf(i)));
        }
    }



    @After
    public void tear_down(){
       driver.quit();
    }

}
