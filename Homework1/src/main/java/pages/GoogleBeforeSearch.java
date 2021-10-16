package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleBeforeSearch {

    protected WebDriver chromeDriver;
    protected WebElement searchField;

    protected WebElement searchButton;

    public GoogleBeforeSearch(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        searchField = chromeDriver.findElement(((By.className("gLFyf"))));
        searchButton = chromeDriver.findElement((By.xpath("//*[(@class='gNO89b') or (@class='Tg7LZd')]")));
    }

    public  void find(String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }
}
