import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CalculatorPage;
import utilities.BrowserType;
import utilities.WebDriverFactory;

@Test()
public class VatCalculatorTest {
    private CalculatorPage calculatorPage;
    private WebDriver driver;

    @BeforeClass
    public void init() {

        driver = WebDriverFactory.createWebDriver(BrowserType.CHROME);
        driver.get("https://www.calkoo.com/en/vat-calculator");
        calculatorPage = new CalculatorPage(driver);
        calculatorPage.clickOnConsentButton();
        calculatorPage.waitForPageTitleVisibility();
    }

    @Test()
    public void grossAmountTest() {

        String netAmount = "100";
        String country = "Egypt";
        String vatRate = "5";

        calculatorPage.selectCountry(country);
        calculatorPage.selectVatRate(vatRate);
        calculatorPage.enterNetAmount(netAmount);
        String calculatedGrossAmount = calculatorPage.getGrossAmount();

        double netAmountValue = Double.parseDouble(netAmount);
        double vatRateValue = Double.parseDouble(vatRate.replace("%", "")) / 100.0;
        double expectedGrossAmountValue = netAmountValue + (netAmountValue * vatRateValue);
        String expectedGrossAmount = String.format("%.2f", expectedGrossAmountValue);

        Assert.assertEquals(calculatedGrossAmount, expectedGrossAmount
                , "Gross Amount Test Passed!");

    }

    @Test()
    public void netAmountTest() {
        CalculatorPage calculatorPage = new CalculatorPage(driver);

        String grossAmount = "120";
        String country = "France";
        String vatRate = "20";

        calculatorPage.selectCountry(country);
        calculatorPage.selectVatRate(vatRate);
        calculatorPage.enterGrossAmount(grossAmount);

        String calculatedNetAmount = calculatorPage.getNetAmount();

        double grossAmountValue = Double.parseDouble(grossAmount);
        double vatRateValue = Double.parseDouble(vatRate.replace("%", "")) / 100.0;
        double expectedNetAmountValue = grossAmountValue / (1 + vatRateValue);
        String expectedNetAmount = String.format("%.2f", expectedNetAmountValue);

        Assert.assertEquals(calculatedNetAmount, expectedNetAmount, "Net Amount Test Passed!");
    }

    @Test()
    public void valueAddedTaxTest() throws InterruptedException {
        CalculatorPage calculatorPage = new CalculatorPage(driver);

        String grossAmount = "6350";
        String netAmount = "5000";
        String country = "Hungary";
        String vatRate = "27";

        calculatorPage.selectCountry(country);
        calculatorPage.selectVatRate(vatRate);
        calculatorPage.enterGrossAmount(grossAmount);

        String actualVatTax = calculatorPage.getVatTax();

        String expectedVatTax = String.format("%.2f",
                (Double.parseDouble(grossAmount) - Double.parseDouble(netAmount)));

        Assert.assertEquals(actualVatTax, expectedVatTax, "Value Added Tax Test Passed!");
    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }

}
