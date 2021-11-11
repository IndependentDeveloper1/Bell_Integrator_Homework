package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageUtils;

public class CitilinkProducts extends PageUtils implements Page {

    WebDriver driver;
    JavascriptExecutor executor;

    private String selectorGraphicProcessorCheckBox = "//div[contains(@data-meta-value, 'Графический процессор')]/div[2]//div[contains(@data-meta-value, '";
    private String selectorVideoCardHead = "//a[contains(@title, '";
    private String selectorVideoCardTail = "')]/../..//span[contains(@class, 'price_current-price')]";

    @FindBy(xpath = "//div[contains(@data-meta-value, 'Графический процессор')]//button")
    WebElement buttonShowAllGraphicProcessors;

    @FindBy(xpath = "//div[contains(@data-meta-value, 'Графический процессор')]/div[2]//input[contains(@type, 'search')]")
    WebElement searchGraphicProcessor;

    @FindBy(xpath = "//div[contains(@data-alias, 'price')]")
    WebElement priceFilter;

    public CitilinkProducts(WebDriver driver){
        this.driver = driver;
        initPage(driver);
        executor = (JavascriptExecutor)driver;
    }

    public void setGraphicProcessors(String cardName){
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonShowAllGraphicProcessors).build().perform();
        executor.executeScript("arguments[0].click();", buttonShowAllGraphicProcessors);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(searchGraphicProcessor));
        executor.executeScript("arguments[0].click();", searchGraphicProcessor);
        searchGraphicProcessor.clear();
        searchGraphicProcessor.sendKeys(cardName);
        WebElement checkBox = driver.findElement(By.xpath(selectorGraphicProcessorCheckBox + cardName + "')]"));
        executor.executeScript("arguments[0].click();", checkBox);
    }

    public void setSortPriceFilter(){
        executor.executeScript("arguments[0].click();", priceFilter);
    }

    public int getCheapestVideoCard(String cardName){
        WebElement videoCard = driver.findElement(By.xpath(selectorVideoCardHead + cardName + selectorVideoCardTail));
        String price = videoCard.getText().replace(" ", "");
        Allure.addAttachment(cardName, price);
        return Integer.valueOf(price);
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }
}
