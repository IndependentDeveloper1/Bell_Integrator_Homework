package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class YandexBeforeSearch {
    protected WebDriver chromeDriver;

    protected WebElement searchField;
    protected WebElement searchButton;
    protected WebElement marketButton;

    public YandexBeforeSearch(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        searchField = chromeDriver.findElement(((By.xpath("//input[@id='text']"))));
        searchButton = chromeDriver.findElement((By.xpath("//button[@class='button mini-suggest__button button_theme_search button_size_search i-bem button_js_inited']")));
        marketButton = chromeDriver.findElement(By.xpath("//a[@data-id='market']"));
    }

    public void find(String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }

    // Проверка, сущестует ли элемент на странице
    public boolean isExist(String name){
        return chromeDriver.findElement(By.xpath("//div[@class='services-new__item-title' and text()='" + name + "']")).getText().equals(name);
    }

    public void marketClick(){
        marketButton.click();
    }


}
