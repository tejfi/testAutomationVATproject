package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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


    public CalculatorPage(WebDriver driver) {
        this.driver = driver;
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
        waitForElement(valueAddedTaxRadioButton);
        driver.findElement(valueAddedTaxRadioButton).click();
        waitForElement(vatSum);
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
        waitForElement(grossPrice);
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
        waitForElement(netPrice);
        return driver
                .findElement(netPrice).getAttribute("value");
    }


    public String getVatTax() throws InterruptedException {
        clickVatTaxRadioButton();
        waitForElement(vatSum);
        return driver
                .findElement(vatSum).getAttribute("value");
    }

    private void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(200000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void clickPriceWithTaxRadioButton() {
        WebElement elem = getPriceWithTaxRadioButton();

        try {
            elem.click();
        } catch (ElementClickInterceptedException e) {
            driver.navigate().refresh();
            elem.click();
        }
    }
    private void clickVatTaxRadioButton() {
        WebElement elem = getVatTaxRadioButton();

        try {
            elem.click();
        } catch (ElementClickInterceptedException e) {
            driver.navigate().refresh();
            elem.click();
        }
    }

    private void waitForRadioButtonElement(By locator) {
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
        waitForElement(priceWithOutVatRadioButton);
        return driver.findElement(priceWithOutVatRadioButton);
    }

    public void waitForPageTitleVisibility() {
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofMillis(5000));
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_TEXT));
    }


    public String getGrossAmount() {
            clickPriceWithTaxRadioButton();
            waitForElement(grossPrice);
            return driver.findElement(grossPrice).getAttribute("value");
        }
}
