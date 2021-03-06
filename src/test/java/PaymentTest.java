import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;


public class PaymentTest  {

    CardTest test = new CardTest();

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
    By cardNumberFrame = By.cssSelector("[name='__privateStripeFrame8']");
    By expiryDateFrame = By.cssSelector("[name='__privateStripeFrame9']");
    By cvcFrame = By.cssSelector("[name='__privateStripeFrame10']");


    @BeforeEach
    public void testSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedrivers.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 6);
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
        logIn("beata.dabrowska91@wp.pl","Biedronka0291");
        payByCreditCard();
        termsAndConfirm();
        assertTrue(driver.findElement(confirmationOfDelivery).isDisplayed(),"Something was wrong. Delivery didn't compound.");
    }

    @Test
    public void paymentByNewUserWithoutCreatingAccountTest() {
        navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        addProductToCardBeforePayment();
        goToPayment();
        addDateToPayment("beata+0000@wp.pl");
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
        addDateToPayment("beata@wp.pl");
        creatingAccount();
        payByCreditCard();
        termsAndConfirm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationOfDelivery));
        assertTrue(driver.findElement(confirmationOfDelivery).isDisplayed(),"Something was wrong. Delivery didn't compound.");
        }

    @Test
    public void logTest() {
        navigateToPage("https://fakestore.testelka.pl/moje-konto/");
        logIn("beata@wp.pl","Biedronka0291");
        assertTrue(driver.findElement(By.cssSelector("[class='delete-me']")).isDisplayed(),"Something was wrong. Delivery didn't compound.");

    }

    @Test
    public void summaryTest() {
        navigateToPage("https://fakestore.testelka.pl/product/egipt-el-gouna/");
        addProductToCardBeforePayment();
        goToPayment();
        addDateToPayment("beata+0000@wp.pl");
        payByCreditCard();
        termsAndConfirm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationOfDelivery));

        String product = driver.findElement(By.cssSelector("[href='https://fakestore.testelka.pl/product/egipt-el-gouna/']")).getText();
        String sum = driver.findElement(By.cssSelector("[class='woocommerce-Price-amount amount']")).getText();
        String productQuantity = driver.findElement(By.cssSelector("[class='product-quantity']")).getText();


        assertAll(
                () -> Assertions.assertEquals("Egipt - El Gouna",product,"Somethining wrong. Product is different than " + product),
                () -> Assertions.assertEquals("3 400,00 zł",sum,"Sum is different than " + sum),
                () -> Assertions.assertEquals("× 1",productQuantity,"Quantity of product is different than " + productQuantity));
    }

    @AfterEach
    public void closeDriver(){
        driver.quit();
    }

    private void termsAndConfirm() {
        driver.findElement(terms).click();
        driver.findElement(confirm).click();
    }

    private void payByCreditCard() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
        fillingInCard(cardNumberFrame, numberOfCard,"4242424242424242");
        fillingInCard(expiryDateFrame, expiryDateCard, "0822");
        fillingInCard(cvcFrame, cvcCodeCard, "123");
        driver.switchTo().defaultContent();

    }

    private void fillingInCard(By frame, By locator, String tekst) {
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
        driver.findElements(locator).get(0).sendKeys(tekst);
    }

    private void addDateToPayment(String emailField) {
        driver.findElement(firstName).sendKeys("Beata");
        driver.findElement(secondName).sendKeys("Dąb");
        WebElement country = driver.findElement(By.cssSelector("[id='billing_country']"));
        Select dropdown = new Select(country);
        dropdown.selectByValue("PR");
        driver.findElement(adress).sendKeys("aaa");
        driver.findElement(postCard).sendKeys("87-896");
        driver.findElement(city).sendKeys("Kraków");
        driver.findElement(phone).sendKeys("789654123");
        driver.findElement(email).sendKeys(emailField);
    }

    private void creatingAccount() {
        driver.findElement(createCount).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(createPassword)).sendKeys("Biedronka0291");
        wait.until(ExpectedConditions.visibilityOfElementLocated(strongPassword));

}


    private void goToPayment() {
        driver.findElement(goToCash).click();
        wait.until(ExpectedConditions.elementToBeClickable(showLogin));
    }


    private void logIn(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(userName)).clear();
        driver.findElement(userName).sendKeys(username);
        driver.findElement(passwordName).sendKeys(password);
        driver.findElement(loginButton).click();

    }

    private void addProductToCardBeforePayment() {
        driver.findElement(test.addProductToCardFromProductPage).click();
        wait.until(ExpectedConditions.elementToBeClickable(test.viewCardFromProductPage)).click();
        wait.until(ExpectedConditions.elementToBeClickable(test.removeButton));
        }

    private void navigateToPage(String page) {
        driver.navigate().to(page);
    }

}
