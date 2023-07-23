package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.time.Duration;

public class CalculatorPage {
    private final WebDriver driver;
    private static final String PAGE_TITLE_TEXT = "Value-Added Tax (VAT) Calculator";
    By netPrice = By.id("NetPrice");
    private By vatSum;
    private By grossPrice;
    private By countrySelect;
    private By priceWithOutVatRadioButton;
    private By valueAddedTaxRadioButton;
    private By priceWithTaxRadioButton;
    private By pageTitle;

    Logger logger = LoggerFactory.getLogger(CalculatorPage.class);
    private Long WAIT_DURATION_IN_MS = 200000L;

    public CalculatorPage(WebDriver driver) {
        this.driver = driver;
        logger.info(MessageFormat.format("Current  URL of page: {0}", driver.getCurrentUrl()));
        initializeElements();
    }

    public WebElement getVatRateElem(String vatRate) {
        String vatRateElem = String.format("//label[@for='VAT_%s']", vatRate);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(vatRateElem)));
        return driver.findElement(By.xpath(vatRateElem));
    }

    private void initializeElements() {
        priceWithOutVatRadioButton = By.xpath("//label[@for='F1']");
        valueAddedTaxRadioButton = By.xpath("//label[@for='F2']");
        priceWithTaxRadioButton = By.xpath("//label[@for='F3']");
        vatSum = By.id("VATsum");
        grossPrice = By.id("Price");
        countrySelect = By.xpath("//select[@name='Country']");
        pageTitle = By.xpath(String.format("//title[text()='%s']", PAGE_TITLE_TEXT));

    }

    public void enterNetAmount(String netAmount) {
        getNetPriceInputElem()
                .sendKeys(netAmount);
    }

    private WebElement getNetPriceInputElem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));

        wait
                .until(ExpectedConditions
                        .presenceOfElementLocated(netPrice));

        return driver.findElement(netPrice);

    }

    public void enterVatAmount(String vatAmount) {

        getVatInputElem()
                .sendKeys(vatAmount);
    }

    private WebElement getVatInputElem() {
        waitForElement(valueAddedTaxRadioButton, WAIT_DURATION_IN_MS);
        driver.findElement(valueAddedTaxRadioButton).click();
        waitForElement(vatSum, WAIT_DURATION_IN_MS);
        return driver.findElement(vatSum);
    }

    public void enterGrossAmount(String grossAmount) {
        clickPriceWithTaxRadioButton();
        WebElement elem = getGrossAmountInputElem();
        elem.clear();
        elem
                .sendKeys(grossAmount);
    }

    private WebElement getGrossAmountInputElem() {
        waitForElement(grossPrice, WAIT_DURATION_IN_MS);
        return driver.findElement(grossPrice);
    }

    public void clickOnConsentButton() {
        WebElement consentButton = driver
                .findElement(By
                        .cssSelector(".fc-cta-consent.fc-primary-button"));
        consentButton.click();
    }

    public void selectCountry(String countryName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.elementSelectionStateToBe(countrySelect, false));
        driver.findElement(countrySelect)
                .findElement(By
                        .xpath("//option[text()='" + countryName + "']")).click();
    }

    public void selectVatRate(String vatRate) {
        getVatRateElem(vatRate).click();
    }

    public String getNetAmount() {
        getPriceWithOutTaxRadioButton().click();
        waitForElement(netPrice, WAIT_DURATION_IN_MS);
        return driver
                .findElement(netPrice).getAttribute("value");
    }


    public String getVatTax() {
        clickVatTaxRadioButton();
        waitForElement(vatSum, WAIT_DURATION_IN_MS);
        return driver
                .findElement(vatSum).getAttribute("value");
    }

    private void waitForElement(By locator, Long durationInMillis) {
        logger.info(MessageFormat
                .format("Waiting {0} ms for elem to be visible: {1}", durationInMillis, locator));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(durationInMillis));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void clickPriceWithTaxRadioButton() {
        WebElement elem = getPriceWithTaxRadioButton();

        try {
            logger.info("Click on PriceWithTaxRadioButton");
            elem.click();
        } catch (ElementClickInterceptedException e) {
            logger.info(MessageFormat
                    .format("ElementClickInterceptedException happened: {0}", e));
            logger.info("Page refresh");
            driver.navigate().refresh();
            logger.info("Click on PriceWithTaxRadioButton");
            elem.click();
        }
    }

    private void clickVatTaxRadioButton() {
        WebElement elem = getVatTaxRadioButton();

        try {
            logger.info("Click on VatTaxRadioButton");
            elem.click();
        } catch (ElementClickInterceptedException e) {
            logger.info(MessageFormat
                    .format("ElementClickInterceptedException happened: {0}", e));
            logger.info("Page refresh");
            driver.navigate().refresh();
            logger.info("Click on VatTaxRadioButton");
            elem.click();
        }
    }

    private void waitForRadioButtonElement(By locator) {
        logger.info(MessageFormat
                .format("Waiting for elem: {0}", locator));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(200000));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement getPriceWithTaxRadioButton() {
        waitForRadioButtonElement(priceWithTaxRadioButton);
        return driver.findElement(priceWithTaxRadioButton);
    }

    private WebElement getVatTaxRadioButton() {
        waitForRadioButtonElement(valueAddedTaxRadioButton);
        return driver.findElement(valueAddedTaxRadioButton);
    }

    private WebElement getPriceWithOutTaxRadioButton() {
        waitForElement(priceWithOutVatRadioButton, WAIT_DURATION_IN_MS);
        return driver.findElement(priceWithOutVatRadioButton);
    }

    public void waitForPageTitleVisibility() {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofMillis(5000));
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_TEXT));
    }


    public String getGrossAmount() {
        clickPriceWithTaxRadioButton();
        waitForElement(grossPrice, WAIT_DURATION_IN_MS);
        return driver.findElement(grossPrice).getAttribute("value");
    }
}
