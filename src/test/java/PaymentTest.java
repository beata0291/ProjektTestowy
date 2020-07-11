import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.TimeUnit;


public class PaymentTest extends CardTest {


    WebDriver driver;
    WebDriverWait wait;
    By alert = By.cssSelector(".woocommerce-store-notice__dismiss-link");
    By goToCash = By.cssSelector("[class='checkout-button button alt wc-forward']");
    By showLogin = By.cssSelector("[class='showlogin']");
    By userName = By.cssSelector("[id='username']");
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
    By createPassword = By.cssSelector("[id='account_password']");
    By strongPassword = By.cssSelector("[class='woocommerce-password-strength good']");



    @BeforeEach
    public void testSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedrivers.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
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




    @Test
        public void paymentByNoyLoggedUserTest() {
            navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
            addProductToCardBeforePayment();
            goToPayment();
            addDateToPayment();
        }

    @AfterEach
    public void closeDriver(){
        driver.quit();
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPassword)).sendKeys("Biedronka0291");
        wait.until(ExpectedConditions.visibilityOfElementLocated(strongPassword));
        assertTrue(driver.findElement(createPassword).isDisplayed());

    }
    private void goToPayment() {
        driver.findElement(goToCash).click();
        wait.until(ExpectedConditions.elementToBeClickable(showLogin));
    }


    private void logIn() {
        wait.until(ExpectedConditions.presenceOfElementLocated(userName)).clear();
        driver.findElement(userName).sendKeys("beata.dabrowska91@gmail.com");
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

}
