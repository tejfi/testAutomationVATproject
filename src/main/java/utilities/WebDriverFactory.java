package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.lang.System.*;

public class WebDriverFactory {

    public static WebDriver createWebDriver(BrowserType browserType) {

        WebDriver driver;

        String webDriverPath = getenv("WEBDRIVER_PATH");

        switch (browserType) {
            case CHROME:
                setProperty("webdriver.chrome.driver", webDriverPath + "/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                setProperty("webdriver.gecko.driver", webDriverPath + "/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser type: " + browserType);
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        return driver;
    }

}
