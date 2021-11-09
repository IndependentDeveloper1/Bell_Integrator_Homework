package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMarketCatalog {

    WebDriver driver;
    WebDriverWait wait;

    public YandexMarketCatalog(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    private String selectorPriceFrom = "//input[@id='glpricefrom']";
    private String selectorPriceTo = "//input[@id='glpriceto']";
    private String selectorMaker = "//div[contains(@data-zone-name, 'search-filter')]//span[contains(., '";
    private String selectorSearchMaker = "//fieldset[contains(@data-autotest-id, '7893318')]//input[1]";
    private String selectorButtonCountProductsOnPage = "//button[contains(text(), 'Показывать по ";
    private String selectorProducts = "//article[contains(@data-autotest-id, 'product-snippet')][not(.//div[contains(., 'Нет в продаже')])]";
    private String selectorURL = ".//a[@href and @title]";
    private String selectorNextPage = "//a[contains(@aria-label,'Следующая страница')]";
    private String selectorLoading = "//div[contains(@role, 'main')]/div[2]";
    private String selectorShowAllProductMakers = "//fieldset[contains(@data-autotest-id, '7893318')]//button";
    private String selectorSearchButton = "//button[contains(@data-r, 'search-button')]";
    private String selectorSearchField = "//input[contains(@id, 'header-search')]";
    private String selectorShowOnPage = "//span[contains(text(),'Показывать по')]/../../..";

    /**
     * Устанавливаем диапозон цены
     * @param lowPrice цена От
     * @param highPrice цена До
     * @return boolean поличилось ли выполнить действие
     */
    public boolean setPrice(int lowPrice, int highPrice){
            WebElement priceFrom = driver.findElement(By.xpath(selectorPriceFrom));
            WebElement priceTo = driver.findElement(By.xpath(selectorPriceTo));
            priceFrom.click();
            priceFrom.sendKeys(String.valueOf(lowPrice));
            priceTo.click();
            priceTo.sendKeys(String.valueOf(highPrice));
            return true;
    }

    /**
     * Устанавливаем производителей товаров
     * @param productMakers лист с производителями
     * @return boolean получилось ли выполнить дейсвие
     */
    public boolean setProductMakers(List<String> productMakers){
            WebElement showAllMakersButton = driver.findElement(By.xpath(selectorShowAllProductMakers));
            showAllMakersButton.click();
            for(String productMaker : productMakers){
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorSearchMaker)));
                WebElement searchMaker = driver.findElement(By.xpath(selectorSearchMaker));
                searchMaker.click();
                searchMaker.sendKeys(Keys.CONTROL + "a");
                searchMaker.sendKeys(Keys.DELETE);
                searchMaker.clear();
                searchMaker.sendKeys(productMaker);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorMaker + productMaker + "')]")));
                WebElement firstMakerCheckBox = driver.findElement(By.xpath(selectorMaker + productMaker + "')]"));
                firstMakerCheckBox.click();
            }
            return true;
    }

    /**
     * Устанавливаем количство отображаемых элементов на странице
     * @param countProducts
     * @return
     * @throws InterruptedException
     */
    public boolean setCountProductsOnPage(int countProducts) {
        waitForInvisibility(3);
        Actions action = new Actions(driver);
        WebElement buttonOpen = driver.findElement(By.xpath(selectorShowOnPage));
        wait.until(ExpectedConditions.elementToBeClickable(buttonOpen));
        action.moveToElement(driver.findElement(By.xpath(selectorShowOnPage))).perform();
        waitForInvisibility(3);
        buttonOpen.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", buttonOpen);
        WebElement buttonChangeCount = driver.findElement(By.xpath(selectorButtonCountProductsOnPage + countProducts + "')]"));
        executor.executeScript("arguments[0].click();", buttonChangeCount);
        return true;
    }

    /**
     * Проверяем число товаров на странице
     * @param count Ожидаемое количество товаров
     * @return результат операции
     */
    public boolean checkCountProducts(int count) {
        waitForInvisibility(3);
        List<WebElement> products = driver.findElements(By.xpath(selectorProducts));
        return count == products.size();
    }

    /**
     * Получаем товары со страницы
     * @return мапа с товарами
     */
    private List<Map<String, Object>> getProducts() {
        List<WebElement> products = driver.findElements(By.xpath(selectorProducts));
        List<Map<String, Object>> productsMaps = new ArrayList<>();
        for (WebElement product : products){
            productsMaps.add(Map.of(
                    "WEB_ELEMENT", product,
                    "URL", product.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    "NAME_PRODUCT", product.findElement(By.xpath(selectorURL)).getAttribute("title")
            ));
        }
        return productsMaps;
    }

    /**
     * Получаем товар со страницы в зависимости от его порядкового томера
     * @param number Порядковый номер, начинаая с 1
     * @return Название товара
     */
    public String getNameProductByNumber(int number) {
        number--;
        List<Map<String, Object>> productsMaps = getProducts();
        return productsMaps.get(number).get("NAME_PRODUCT").toString();
    }

    /**
     * Ищем товар в каталоге по имени
     * @param productName Наименование товара
     * @return Результат операции
     */
    public boolean searchProduct(String productName) {
            WebElement search = driver.findElement(By.xpath(selectorSearchField));
            search.click();
            search.sendKeys(productName);
            WebElement searchButton = driver.findElement(By.xpath(selectorSearchButton));
            searchButton.click();
            List<Map<String, Object>> allProducts = getProducts();
            WebElement desiredProduct = (WebElement) allProducts.stream()
                    .filter(x -> x.get("NAME_PRODUCT").toString().equals(productName))
                    .findFirst()
                    .get().get("WEB_ELEMENT");
            return desiredProduct.isDisplayed();
    }

    /**
     * Проверяем все товары на соответствие с необходимыми производителями
     * @param makers лист производителей товаров
     * @return Результат операции
     */
    public boolean checkAllProducts(List<String> makers) {
        List<Map<String, Object>> products = getProducts();
        if(driver.findElement(By.xpath(selectorNextPage)).isEnabled()){
            Actions action = new Actions(driver);
            WebElement buttonNext = driver.findElement(By.xpath(selectorNextPage));
            while(isElementPresent(By.xpath(selectorNextPage))){
                action.moveToElement(buttonNext).perform();
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", buttonNext);
                products.addAll(getProducts());
                waitForInvisibility(3);
                try{buttonNext = driver.findElement(By.xpath(selectorNextPage));}
                catch (WebDriverException e) {break;}
            }
        }
        boolean match = false;
        if (makers.size() == 1){
            match = products.stream()
                .allMatch(x -> x.get("NAME_PRODUCT").toString().contains(makers.get(0)));
        }
        else{
            for (String maker : makers) {
                match = products.stream().
                        anyMatch(x -> x.get("NAME_PRODUCT").toString().contains(maker));
            }
        }
        return match;
    }

    /**
     * Ожидание изчезновения подгрузки товаров
     * @param maxSeconds максимальное время ожидания
     */
    public void waitForInvisibility(int maxSeconds) {
        Long startTime = System.currentTimeMillis();
        try {
            WebElement webElement = driver.findElement(By.xpath(selectorLoading));
            while (System.currentTimeMillis() - startTime < maxSeconds * 1000 && webElement.isDisplayed()) {}
        } catch (WebDriverException e) {
            return;
        }
    }

    /**
     * Проверка существует ли элемент на странице
     * @param element By элемента
     * @return да/нет
     */
    public boolean isElementPresent(By element) {
        try {
            driver.findElement(element);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
