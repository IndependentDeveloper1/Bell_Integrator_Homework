package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CitilinkMainPage implements Page{

    WebDriver driver;

    @FindBy(xpath = "//button[contains(@data-label, 'Каталог товаров')]")
    WebElement buttonCategories;

    private String selectorCategories = "//a[contains(@data-title, '";


    public CitilinkMainPage(WebDriver driver){
        this.driver = driver;
        initPage(driver);
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }

    public void goSubCat(String categoryName, String subCategoryName){
        buttonCategories.click();
        Actions actions = new Actions(driver);
        String categoryXPath = selectorCategories + categoryName + "')]/..";
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(categoryXPath)));
        WebElement category = driver.findElement(By.xpath(categoryXPath));
        actions.moveToElement(category).perform();
        actions.build();
        String subCategoryXPath = selectorCategories + subCategoryName + "')]/..";
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath(subCategoryXPath)));
        WebElement subCategory = driver.findElement(By.xpath(subCategoryXPath));
        subCategory.click();
    }

}
