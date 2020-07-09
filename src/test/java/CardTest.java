import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CardTest {
    WebDriver driver;
    WebDriverWait wait;
    String productID = "386";
    String[] products = {"386", "393", "391", "50", "389"};

    By alert = By.cssSelector(".woocommerce-store-notice__dismiss-link");
    By addProductToCardFromProductPage = By.cssSelector("button[name='add-to-cart']");
    By addProductToCardFromCategoryPage = By.cssSelector("a[href='?add-to-cart="+productID+"']");
    By viewCardFromProductPage = By.cssSelector("[class='woocommerce-message']>.button");
    By viewCardFromCategoryPage = By.cssSelector("[class='added_to_cart wc-forward']");
    By removeButton = By.cssSelector("td[class='product-remove']>a");
    By countInCard = By.cssSelector("input[class='input-text qty text']");
    By removeConfirm = By.cssSelector("[class='woocommerce-message']>a");

    int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String[] productPages = {"/egipt-el-gouna/","/wspinaczka-via-ferraty/","/wspinaczka-island-peak/",
            "/fuerteventura-sotavento/", "/grecja-limnos/", "/windsurfing-w-karpathos/",
            "/wyspy-zielonego-przyladka-sal/", "/wakacje-z-yoga-w-kraju-kwitnacej-wisni/",
            "/wczasy-relaksacyjne-z-yoga-w-toskanii/", "/yoga-i-pilates-w-hiszpanii/"};

    @BeforeEach
    public void testSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedrivers.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS); //załadowanie strony
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // czekanie na webelement
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().setSize(new Dimension(1290, 730));
        driver.manage().window().setPosition(new Point(8, 30));
        driver.navigate().to("https://fakestore.testelka.pl");
        driver.findElement(alert).click();
//driver.findElements(By.cssSelector("tralala")).get(0); ->wyszukanie listy elementów o selektorze tralala.
// i biore pierwszy element czyli elementu o indeksie 0
    }


    @Test
    public void addProductToCardFromProductPageTest() {
        navigate("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        addProductToCardAndView();
        String count = driver.findElement(countInCard).getAttribute("value");
        assertAll(
                () -> assertTrue(driver.findElement(removeButton).isDisplayed(),"Somethining wrong. Product wasn't added to Card"),
                () -> Assertions.assertEquals("1", count,"Count from card is incorrect. It should be one product"));
    }

    @Test
    public void addProductToCardFromCategoryPageTest() {
        navigate("https://fakestore.testelka.pl/product-category/windsurfing/");
        addProductToCardAndViewFromCategoryPage();
        String count = driver.findElement(countInCard).getAttribute("value");
        assertAll(
                () -> assertTrue(driver.findElement(removeButton).isDisplayed(),"Somethining wrong. Product wasn't added to Card"),
                () -> Assertions.assertEquals("1", count,"Count from card is incorrect. It should be one product"));
        }

    @Test
    public void addTenProductsToCardTest() {
        navigate("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        clearForCount();
        addCountOfProduct("10");
        addProductToCardAndView();
        String count = driver.findElement(countInCard).getAttribute("value");
        assertAll(
                () -> assertTrue(driver.findElement(removeButton).isDisplayed()),
                () -> Assertions.assertEquals("10", count));
    }

    @Test
    public void addTenTimeOneProductTest() {
        navigate("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        for (int value : values) {
            addProductToCardFromProductPage();
            wait.until(ExpectedConditions.elementToBeClickable(viewCardFromProductPage));
        }
        viewCardFromProductPage();
        String count = driver.findElement(countInCard).getAttribute("value");
        assertAll(
                () -> assertTrue(driver.findElement(removeButton).isDisplayed()),
                () -> Assertions.assertEquals("10", count));
    }

        @Test
        public void changeCountFromCardTest() {
        navigate("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        addProductToCardAndView();
        clearForCount();
        addCountOfProduct("5");
            String count = driver.findElement(countInCard).getAttribute("value");
            assertAll(
                    () -> assertTrue(driver.findElement(removeButton).isDisplayed()),
                    () -> Assertions.assertEquals("5", count));
        }

        @Test
        public void removeProductFromCard () {
            navigate("https://fakestore.testelka.pl/product/egipt-el-gouna/");
            addProductToCardAndView();
            removeProduct();
            assertTrue(driver.findElement(removeConfirm).isDisplayed());
        }

        @Test
        public void addFiveDifferentProductToCard () {
       navigate("https://fakestore.testelka.pl/product-category/windsurfing/");

    String[] products2 = products.clone();

    while ( products2.length>0){
        String element = products2[0];
        System.out.println("element "+ element);
        System.out.printf("%n");
        System.out.println("products2.length " + products2.length);
        System.out.printf("%n");
        products2 = ArrayUtils.remove(products2,0);
        System.out.printf("-----");
        System.out.printf("%n");
        By addAllProductToCardFromCategoryPage = By.cssSelector("a[href='?add-to-cart="+element+"']");
        driver.findElement(addAllProductToCardFromCategoryPage).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCardFromCategoryPage));
        }
            viewCardFromCategoryPage();
         //   System.out.println("products.length poza petla " + products.length);
       //     System.out.println("products2.length poza petla " + products2.length);
            By countOfButton = By.cssSelector("a[class='remove']");
            wait.until(ExpectedConditions.elementToBeClickable(countOfButton));
            assertEquals(5, driver.findElements(countOfButton).size(), "Nie ma hehe");

    }
    @Test
    public void addFiveDifferentProductToCard2 () {
        driver.navigate().to("https://fakestore.testelka.pl/product-category/windsurfing/");

        for (String element : products) {
            By addAllProductToCardFromCategoryPage = By.cssSelector("a[href='?add-to-cart=" + element + "']");
            driver.findElement(addAllProductToCardFromCategoryPage).click();
            wait.until(ExpectedConditions.elementToBeClickable(viewCardFromCategoryPage));
        }
    }


@Test
    public void addTenTDifferentProductToCard() {
    for (String productPage : productPages) {
        navigate("https://fakestore.testelka.pl/product" + productPage);
        addProductToCardFromProductPage();
    }

}
    @AfterEach
    public void closeDriver(){
        driver.quit();
    }

    protected void navigate(String page) {
        driver.navigate().to(page);
    }


    private void addProductToCardAndView() {
        addProductToCardFromProductPage();
        viewCardFromProductPage();

    }

    private void removeProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        driver.findElement(removeButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(removeConfirm));
    }

    private void viewCardFromProductPage() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCardFromProductPage));
        driver.findElement(viewCardFromProductPage).click();
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
    }

    private void addProductToCardFromProductPage() {
        driver.findElement(addProductToCardFromProductPage).click();
    }
    private void addProductToCardAndViewFromCategoryPage() {
        addToProductFromCategoryPare();
        viewCardFromCategoryPage();

    }

    private void viewCardFromCategoryPage() {
        driver.findElement(viewCardFromCategoryPage).click();
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
    }

    private void addToProductFromCategoryPare() {
        driver.findElement(addProductToCardFromCategoryPage).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCardFromCategoryPage));
    }

    private void addCountOfProduct(String number) {
        driver.findElement(countInCard).sendKeys(number);
    }

    private void clearForCount() {
        driver.findElement(countInCard).clear();
    }



}