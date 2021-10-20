package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class YandexAfterSearch extends YandexBeforeSearch {

    private List<WebElement> results;
    WebDriverWait wait = new WebDriverWait(chromeDriver, 120);

    public YandexAfterSearch(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    public List<WebElement> getElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='search-result']")));
        results=chromeDriver.findElements(By.xpath("//li[@id='search-result']"));
        return results;
    }
}
