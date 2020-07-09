import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.concurrent.TimeUnit;


public class PaymentTest extends CardTest {

    CardTest card = new CardTest();

    WebDriver driver;
    WebDriverWait wait;
    By alert = By.cssSelector(".woocommerce-store-notice__dismiss-link");
    By goToCash = By.cssSelector("[class='checkout-button button alt wc-forward']");
    By showLogin = By.cssSelector("[class='showlogin']");
    By username = By.cssSelector("[id='username']");
    By password = By.cssSelector("[id='password']");
    By loginButton = By.cssSelector("[name='login']");
    By buyAndPayButton = By.cssSelector("[id='place_order']");
    By firstName = By.cssSelector("[id='billing_first_name']");
    By secondName = By.cssSelector("[id='billing_last_name']");
    By adress = By.cssSelector("[id='billing_address_1']");
    By city = By.cssSelector("[id='billing_city']");
    By phone = By.cssSelector("[id='billing_phone']");
    By email = By.cssSelector("[id='billing_email']");
    By createCount = By.cssSelector("[id='createaccount']");
    By postCard = By.cssSelector("[id='billing_postcode']");
    By createPassword = By.cssSelector("[class='create-account']");


    @BeforeEach
    public void testSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedrivers.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().setSize(new Dimension(1290, 730));
        driver.manage().window().setPosition(new Point(8, 30));
        driver.navigate().to("https://fakestore.testelka.pl");
        driver.findElement(alert).click();

    }

    @Test
    public void paymentByLoggedUserTest() {
        navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        addProductToCardBeforePayment();
        goToPayment();
        driver.findElement(showLogin).click();
        logIn();
        assertTrue(driver.findElement(buyAndPayButton).isDisplayed());
    }

    private void goToPayment() {
        driver.findElement(goToCash).click();
        wait.until(ExpectedConditions.elementToBeClickable(showLogin));
    }

    @Test
        public void paymentByNoyLoggedUserTest() {
            navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
            addProductToCardBeforePayment();
            goToPayment();
            addDateToPayment();


        }

    private void addDateToPayment() {
        driver.findElement(firstName).sendKeys("Beata");
        driver.findElement(secondName).sendKeys("Dąb");
      WebElement country= driver.findElement(By.cssSelector("[id='billing_country']"));
        Select dropdown = new Select(country);
        dropdown.selectByValue("PR");
        driver.findElement(adress).sendKeys("aaa");
        driver.findElement(postCard).sendKeys("87-896");
        driver.findElement(city).sendKeys("Kraków");
        driver.findElement(phone).sendKeys("789654123");
        driver.findElement(email).sendKeys("beata@wp.pl");
        driver.findElement(createCount).click();
      /*  WebElement strongPassword = By.cssSelector("[class='woocommerce-password-strength good']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(strongPassword)
        Assertions.assertTrue((strongPassword) */

    }


    private void logIn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(username)).clear();
        driver.findElement(username).sendKeys("beata.dabrowska91@gmail.com");
        driver.findElement(password).sendKeys("Biedronka0291");
        driver.findElement(loginButton).click();

    }

    private void addProductToCardBeforePayment() {
        driver.findElement(addProductToCardFromProductPage).click();
        wait.until(ExpectedConditions.elementToBeClickable(viewCardFromProductPage)).click();
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));

    }

    private void navigateToPage(String page) {
        driver.navigate().to(page);
    }





    @AfterEach
    public void closeDriver(){
        driver.quit();
    }
}
