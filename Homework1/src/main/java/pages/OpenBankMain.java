package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OpenBankMain {

    private List<WebElement> results;
    protected WebDriver chromeDriver;
    protected WebElement currencyExchange;

    public OpenBankMain(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        currencyExchange = chromeDriver.findElement(By.xpath("//*[@class='main-page-exchange__table main-page-exchange__table--online']"));
    }

    public List<WebElement> getCurrencyExchange(){
        results = currencyExchange.findElements(By.xpath("//*[@class='main-page-exchange__rate']"));
        return results;
    }
}
