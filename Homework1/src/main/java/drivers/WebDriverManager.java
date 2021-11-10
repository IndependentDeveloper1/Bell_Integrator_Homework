package drivers;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverManager {

    private static WebDriver currentDriver;

    public static WebDriver getCurrentDriver() {
        return currentDriver;
    }

    public static void initChrome() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments(List.of("start-maximized", "disable-infobars", "--no-sandbox"));
        try {
            currentDriver = new ChromeDriver(options);
        } catch (SessionNotCreatedException e) {
            System.out.println("Данный драйвер не совместим с текущим браузером. Используйте другой драйвер");
        }
        setDriverDefaultSettings();
    }

    private static void setDriverDefaultSettings() {
        currentDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        currentDriver.manage().deleteAllCookies();
    }

    public static void killCurrentDriver() {
        if (currentDriver != null) {
            currentDriver.quit();
            currentDriver = null;
        }
    }
}
