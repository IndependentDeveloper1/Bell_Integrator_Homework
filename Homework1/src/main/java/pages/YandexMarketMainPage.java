package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMarketMainPage {

    /**
     * Обозначаем драйвер и ожидание
     */
    protected WebDriver driver;
    WebDriverWait wait;

    /**
     * Обозначаем селекторы
     */
    private String selectorNameCategories = ".//span";
    private String selectorURL = ".//a[@href]";
    private String selectorCategories = "//li[contains (@data-zone-name, 'category-link')]";
    private String selectorButtonCategories = "//div[contains(@data-apiary-widget-id, '/header/catalogEntrypoint')]";
    private String selectorMoreInCategories = "//ul[contains(@data-autotest-id,'subItems')]/li[6]";
    private String selectorSubCategories = "//ul[contains(@data-autotest-id, 'subItems')]//div[contains(@data-zone-name,'link')]";

    /**
     * Переменная для хранения текущей страницы
     */
    private String pageName = "";

    /**
     * Объявляем категории и подкатегории
     */
    private List<WebElement> categories;
    private List<Map<String, Object>> categoriesMaps = new ArrayList<>();
    private List<WebElement> subCategories;
    private List<Map<String, Object>> subCategoriesMaps = new ArrayList<>();


    /**
     * Конструктор
     *
     * @param driver
     */
    public YandexMarketMainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    /**
     * Отображаем на экране все категории выбранной категории
     */
    public void openAllGategories() {
        WebElement buttonCategories = driver.findElement(By.xpath(selectorButtonCategories));
        buttonCategories.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorCategories)));
        categories = driver.findElements(By.xpath(selectorCategories));
    }

    /**
     * Получаем все категории на странице
     *
     * @return лист с мапами всех категорий
     */
    public List<Map<String, Object>> getCategoriesMaps() {
        openAllGategories();
        for (WebElement element : categories) {
            categoriesMaps.add(Map.of(
                    "WEB_ELEMENT", element,
                    "URL", element.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    "NAME_CATEGORY", element.findElement(By.xpath(selectorNameCategories)).getText()
            ));
        }
        return categoriesMaps;
    }

    /**
     * Переход к нужной категории
     *
     * @param categoryName Название категории
     * @return boolean получилось ли выполнить действие
     */
    public boolean goCategory(String categoryName) {
        goPage("Яндекс.Маркет");
        categoriesMaps = getCategoriesMaps();
        WebElement category = (WebElement) categoriesMaps.stream()
                .filter(x -> x.get("NAME_CATEGORY").toString().contains(categoryName))
                .findFirst()
                .get().get("WEB_ELEMENT");
        category.click();
        Assertions.assertTrue(goPage(categoryName),
                "Не удалось перейти на страницу с именем " + categoryName);
        return true;
    }

    /**
     * Переход к нужной категории
     *
     * @param categoryName    Название категории
     * @param subCategoryName Название подкатегории
     * @return boolean получилось ли выполнить действие
     */
    public boolean goCategory(String categoryName, String subCategoryName) {
        goPage("Яндекс.Маркет");
        categoriesMaps = getCategoriesMaps();
        WebElement category = (WebElement) categoriesMaps.stream()
                .filter(x -> x.get("NAME_CATEGORY").toString().contains(categoryName))
                .findFirst()
                .get().get("WEB_ELEMENT");
        Actions action = new Actions(driver);
        action.moveToElement(category).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorMoreInCategories)));
        List<WebElement> moreElements = driver.findElements(By.xpath(selectorMoreInCategories));
        try {
            moreElements.forEach(WebElement::click);
        } catch (ElementClickInterceptedException e) {
            System.out.println("Ошибка! Необходимые элементы перекрыты");
        }
        subCategoriesMaps = getSubCategoriesMaps();
        WebElement subCategory = (WebElement) subCategoriesMaps.stream()
                .filter(x -> x.get("NAME_CATEGORY").toString().contains(subCategoryName))
                .findFirst()
                .get().get("WEB_ELEMENT");
        subCategory.click();
        Assertions.assertTrue(goPage(subCategoryName),
                "Не удалось перейти на страницу с именем " + subCategoryName);
        return true;
    }


    /**
     * Получаем список категорий
     *
     * @return лист с webelement категорий
     */
    public List<WebElement> getSubCategories() {
        subCategories = driver.findElements(By.xpath(selectorSubCategories));
        return subCategories;
    }

    /**
     * Получаем преобразованный список подкатегорий
     *
     * @return лист с мапами подкатегорий
     */
    public List<Map<String, Object>> getSubCategoriesMaps() {
        for (WebElement element : getSubCategories()) {
            categoriesMaps.add(Map.of(
                    "WEB_ELEMENT", element,
                    "URL", element.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    "NAME_CATEGORY", element.findElement(By.xpath(selectorURL)).getText()
            ));
        }
        return categoriesMaps;
    }

    /**
     * Переклюбчаем драйвер на нужную страницу
     *
     * @param pageName название страницы, на которую необходимо перейти
     * @return boolean получилось ли выпонить действие
     */
    private boolean goPage(String pageName) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs) {
            driver.switchTo().window(tab);
            if (driver.getTitle().contains(pageName))
                return true;
        }
        return false;
    }
}
