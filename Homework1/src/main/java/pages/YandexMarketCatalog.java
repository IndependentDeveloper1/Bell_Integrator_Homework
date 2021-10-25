package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class YandexMarketCatalog {

    WebDriver driver;
    WebDriverWait wait;

    public YandexMarketCatalog(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    // Селектор цена товара от
    private String selectorPriceFrom = "//input[@id='glpricefrom']";
    // Селектор цена товара до
    private String selectorPriceTo = "//input[@id='glpriceto']";
    // Селектор названия производителя товара
    private String selectorMaker = "//div[contains(@class, '_1exhF')]/span[contains(text(), '";
    // Селектор поиска по названию производителя товара
    private String selectorSearchMaker = "//input[@class='_1JYTt']";
    // sel button count
    private String selectorButtonCountProductsOnPageOpen = "//button[contains(@class, 'vLDMf')]";
    // sel button count on page
    private String selectorButtonCountProductsOnPage = "//button[contains(text(), 'Показывать по ";
    // sel products
    private String selectorProducts = "//article[contains(@class, '_2vCnw cia-vs cia-cs') or contains(@class, 'LYpqx _61qCP _3LAO7 cia-vs cia-cs')]";
    // sel name product
    private String selectorProductName = ".//span[contains(@data-tid, 'ce80a508')]";
    // Селектор ссылки
    private String selectorURL = ".//a[@href and @title]";
    // Селектор следующей страницы
    private String selectorNextPage = "//a[contains(@aria-label,'Следующая страница')]";

    /**
     * Устанавливаем диапозон цены
     * @param lowPrice цена От
     * @param highPrice цена До
     * @return boolean поличилось ли выполнить действие
     */
    public boolean setPrice(String lowPrice, String highPrice){
        try{
            WebElement priceFrom = driver.findElement(By.xpath(selectorPriceFrom));
            WebElement priceTo = driver.findElement(By.xpath(selectorPriceTo));
            priceFrom.click();
            priceFrom.sendKeys(lowPrice);
            priceTo.click();
            priceTo.sendKeys(highPrice);
            return true;
        }
        catch (WebDriverException e){
            System.out.println("Ошибка: " + e);
            return false;
        }
    }

    /**
     * Устанавливаем производителей товаров
     * @param productMakers лист с производителями
     * @return boolean получилось ли выполнить дейсвие
     */
    public boolean setProductMakers(List<String> productMakers){

        try{
            WebElement showAllMakersButton = driver.findElement(By.xpath("//button[@class='_1KpjX _2Wg9r']"));
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
        catch (WebDriverException e){
            Assertions.fail(e);
            return false;
        }
    }

    /**
     * Устанавливаем количство отображаемых элементов на странице
     * @param countProducts
     * @return
     * @throws InterruptedException
     */
    public boolean setCountProductsOnPage(String countProducts) throws InterruptedException {
        try {
            Actions action = new Actions(driver);
            WebElement buttonOpen = driver.findElement(By.xpath(selectorButtonCountProductsOnPageOpen));
            TimeUnit.SECONDS.sleep(2);
            wait.until(ExpectedConditions.elementToBeClickable(buttonOpen));
            action.moveToElement(driver.findElement(By.xpath("//div[contains(@class, '_3JNss _1BSH6 v3cFc')]"))).perform();
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", buttonOpen);
            TimeUnit.SECONDS.sleep(3);
            WebElement buttonChangeCount = driver.findElement(By.xpath(selectorButtonCountProductsOnPage + countProducts + "')]"));
            action.moveToElement(buttonChangeCount).click().perform();
            return true;
        }
        catch (WebDriverException e){
            Assertions.fail(e);
            return false;
        }
    }

    /**
     * Проверяем число товаров на странице
     * @param count Ожидаемое количество товаров
     * @return результат операции
     * @throws InterruptedException
     */
    public boolean checkCountProducts(int count) throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        List<WebElement> products = driver.findElements(By.xpath(selectorProducts));
        return count == products.size();
    }

    /**
     * Получаем товары со страницы
     * @return мапа с товарами
     * @throws InterruptedException
     */
    private List<Map<String, Object>> getProducts() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        List<WebElement> products = driver.findElements(By.xpath(selectorProducts));
        List<Map<String, Object>> productsMaps = new ArrayList<>();
        for (WebElement product : products){
            productsMaps.add(Map.of(
                    // Элемент категории
                    "WEB_ELEMENT", product,
                    // Ссылка
                    "URL", product.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    // Название
                    "NAME_PRODUCT", product.findElement(By.xpath(selectorURL)).getAttribute("title")
            ));
        }
        return productsMaps;
    }

    /**
     * Получаем товар со страницы в зависимости от его порядкового томера
     * @param number Порядковый номер, начинаая с 1
     * @return Название товара
     * @throws InterruptedException
     */
    public String getNameProductByNumber(int number) throws InterruptedException {
        number--;
        List<Map<String, Object>> productsMaps = getProducts();
        return productsMaps.get(number).get("NAME_PRODUCT").toString();
    }

    /**
     * Ищем товар в каталоге по имени
     * @param productName Наименование товара
     * @return Результат операции
     * @throws InterruptedException
     */
    public boolean searchProduct(String productName) throws InterruptedException {
        try{
            WebElement search = driver.findElement(By.id("header-search"));
            search.click();
            search.sendKeys(productName);
            WebElement searchButton = driver.findElement(By.xpath("//span[contains(@class, 'JqPid')]"));
            searchButton.click();
            List<Map<String, Object>> allProducts = getProducts();
            WebElement desiredProduct = (WebElement) allProducts.stream()
                    .filter(x -> x.get("NAME_PRODUCT").toString().equals(productName))
                    .findFirst()
                    .get().get("WEB_ELEMENT");
            return desiredProduct.isDisplayed();
        }
        catch (WebDriverException e){
            Assertions.fail(e);
            return false;
        }
    }

    /**
     * Проверяем все товары на соответствие с необходимым производителем
     * @param makerName Имя производителя товара
     * @return Результат операции
     * @throws InterruptedException
     */
    public boolean checkAllProducts(String makerName) throws InterruptedException {
        List<Map<String, Object>> products = getProducts();
        if(driver.findElement(By.xpath(selectorNextPage)).isEnabled()){
            Actions action = new Actions(driver);
            WebElement buttonNext = driver.findElement(By.xpath(selectorNextPage));
            while(driver.findElement(By.xpath(selectorNextPage)).isEnabled()){
                action.moveToElement(buttonNext).perform();
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", buttonNext);
                TimeUnit.SECONDS.sleep(3);
                products.addAll(getProducts());
                try{buttonNext = driver.findElement(By.xpath(selectorNextPage));}
                catch (WebDriverException e) {System.out.println("Последняя сраница пройдена"); break;}
            }
        }
        boolean match = products.stream()
                .allMatch(x -> x.get("NAME_PRODUCT").toString().contains(makerName));
        return match;
    }
}
