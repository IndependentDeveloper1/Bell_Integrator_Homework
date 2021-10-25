package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Задание 1.1
 */
public class GoogleAfterSearch extends GoogleBeforeSearch {

    private List<WebElement> results;
    WebDriverWait wait = new WebDriverWait(chromeDriver, 120);

    public GoogleAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    public List<WebElement> getElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='g']")));
        results=chromeDriver.findElements(By.xpath("//*[@class='g']"));
        return results;
    }
}
