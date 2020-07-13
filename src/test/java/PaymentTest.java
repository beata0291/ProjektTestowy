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
    By passwordName = By.cssSelector("[id='password']");
    By loginButton = By.cssSelector("[name='login']");
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
    By loadingIcon = By.cssSelector(".blockOverlay");
    By expiryDateCard = By.cssSelector("[autocomplete='cc-exp']");
    By cvcCodeCard = By.cssSelector("[autocomplete='cc-csc']");
    By numberOfCard = By.cssSelector("[autocomplete='cc-number']");
    By terms = By.cssSelector("[name='terms']");
    By confirm = By.cssSelector("[id='place_order']");
    By confirmationOfDelivery = By.cssSelector("[id='post-7']");


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
    public void paymentByExistingUserTest() {
        navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        addProductToCardBeforePayment();
        goToPayment();
        driver.findElement(showLogin).click();
        logIn("beata.dabrowska92@wp.pl","Biedronka0291");
        payByCreditCard();
        termsAndConfirm();
        assertTrue(driver.findElement(confirmationOfDelivery).isDisplayed(),"Something was wrong. Delivery didn't compound.");
    }




    @Test
        public void paymentByNewUserWithoutCreatingAccountTest() {
            navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
            addProductToCardBeforePayment();
            goToPayment();
            addDateToPayment();
            payByCreditCard();
            termsAndConfirm();
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationOfDelivery));
            assertTrue(driver.findElement(confirmationOfDelivery).isDisplayed(),"Something was wrong. Delivery didn't compound.");

        }

        @Test
        public void paymentWithCreatingNewAccountTest(){
            navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
            addProductToCardBeforePayment();
            goToPayment();
            addDateToPayment();


        }
@Test
    public void logTest() {
        navigateToPage("https://fakestore.testelka.pl/moje-konto/");
        logIn(" beata.dabrowska91@gmail.com","Biedronka0291");
        assertTrue(driver.findElement(By.cssSelector("[class='delete-me']")).isDisplayed(),"This user does not exist");

    }


    private void termsAndConfirm() {
        driver.findElement(terms).click();
        driver.findElement(confirm).click();
    }

    private void payByCreditCard() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
        driver.switchTo().frame("__privateStripeFrame8");
        driver.findElements(numberOfCard).get(0).sendKeys("4242424242424242");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("__privateStripeFrame9");
        driver.findElement(expiryDateCard).sendKeys("0822");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("__privateStripeFrame10");
        driver.findElement(cvcCodeCard).sendKeys("123");
        driver.switchTo().defaultContent();
    }

    @AfterEach
    public void closeDriver(){
        driver.quit();
    }

    private void addDateToPayment() {
        driver.findElement(firstName).sendKeys("Beata");
        driver.findElement(secondName).sendKeys("Dąb");
        WebElement country = driver.findElement(By.cssSelector("[id='billing_country']"));
        Select dropdown = new Select(country);
        dropdown.selectByValue("PR");
        driver.findElement(adress).sendKeys("aaa");
        driver.findElement(postCard).sendKeys("87-896");
        driver.findElement(city).sendKeys("Kraków");
        driver.findElement(phone).sendKeys("789654123");
        driver.findElement(email).sendKeys("beata@wp.pl");
    }

    private void creatingAccount() {
        driver.findElement(createCount).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPassword)).sendKeys("Biedronka0291");
        wait.until(ExpectedConditions.visibilityOfElementLocated(strongPassword));
        assertTrue(driver.findElement(createPassword).isDisplayed());
}


    private void goToPayment() {
        driver.findElement(goToCash).click();
        wait.until(ExpectedConditions.elementToBeClickable(showLogin));
    }
    private  void createCount() {
        driver.findElement(createCount).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPassword)).sendKeys("Biedronka0291");
        wait.until(ExpectedConditions.visibilityOfElementLocated(strongPassword));
        assertTrue(driver.findElement(createPassword).isDisplayed());

    }


    private void logIn(String username, String password) {
        wait.until(ExpectedConditions.presenceOfElementLocated(userName)).clear();
        driver.findElement(userName).sendKeys(username);
        driver.findElement(passwordName).sendKeys(password);
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
//    @Test
//    public void getSummedNumbers() {
//        navigate("https://fakestore.testelka.pl/product/egipt-el-gouna/");
//        addProductToCardAndView();
//        clearForCount();
//        int numberOfProductsInPLN = addCountOfProduct("20");
//       System.out.print("Dodano " + numberOfProductsInPLN + " zł produktów");
//       int numberOfProductsInEUR = numberOfProductsInPLN * 4;
//        System.out.print("Dodano " + numberOfProductsInEUR + " eur produktów");
//    }