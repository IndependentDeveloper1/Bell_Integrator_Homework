package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YandexMarketPO {

    // Драйвер
    protected WebDriver driver;
    // Ожидание подгрузки элемента
    WebDriverWait wait;

    // Селектор названий категорий
    private String selectorNameCategories = ".//span";
    //Селектор ссылки категории
    private String selectorURL = ".//a[@href]";
    //Селектор категорий
    private String selectorCategories = "//li[contains (@class, '_1hPrb cia-cs') or contains(@class, '_1hPrb CvDqK cia-cs')]";
    // Селектор кнопки всех категорий
    private String selectorButtonCategories = "//button[@class='_2AMPZ _3CFK9 _2VvV8 _3WgR5']";
    // Селектор "Еще" в категориях
    private String selectorMoreInCategories = "//span[@class='_2qvOO _19m_j _3kgUl _2Wxcq']";
    // Селектор подкатегорий
    private String selectorSubCategories = "//li[@data-tid='97b9932a']";
    // Селектор цена товара от
    private String selectorPriceFrom = "//input[@id='glpricefrom']";
    // Селектор цена товара до
    private String selectorPriceTo = "//input[@id='glpriceto']";

    // Какая страница должна быть открыта
    private String pageName = "";


    // Категории
    private List<WebElement> categories;
    private List<Map<String, Object>> categoriesMaps = new ArrayList<>();
    // Подкатегории
    private List<WebElement> subCategories;
    private List<Map<String, Object>> subCategoriesMaps = new ArrayList<>();


    // Конструктор
    public YandexMarketPO(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    // Открыть выбор всех катеорий
    public void openAllGategories(){
        WebElement buttonCategories = driver.findElement(By.xpath(selectorButtonCategories));
        buttonCategories.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorCategories)));
        categories = driver.findElements(By.xpath(selectorCategories));
    }

    // Вернуть все категории в виде мап
    public List<Map<String, Object>> getCategoriesMaps(){
        openAllGategories();
        for (WebElement element : categories){
            categoriesMaps.add(Map.of(
                    // Элемент категории
                    "WEB_ELEMENT", element,
                    // Ссылка
                    "URL", element.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    // Название
                    "NAME_CATEGORY", element.findElement(By.xpath(selectorNameCategories)).getText()
            ));
        }
        return categoriesMaps;
    }

//    // Получеаем мапы из NavigationTree
//    public List<Map<String, Map<String, Object>>> getNavigationCategoryMaps(){
//        navigationCategory = driver.findElements(By.xpath(selectorNavigationCategories));
//        for (WebElement element : navigationCategory){
//            if (element.findElements(By.xpath("//div[@data-zone-data]")).size() == 2){
//                // У некоторых списков есть кнопка "Свернуть", поэтому разворачиваем
//                element.findElement(By.xpath("//div[@data-zone-data][2]")).click();
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-zone-data][2]")));
//            }
//            navigationCategoriesMaps.add(Map.of(
//                    // Название ветки
//                    element.findElement(By.xpath("//a[@class='egKyN _2jA_3 _2EJs7']")).getText(),
//                    // Его мапа со всеми элементами
//                    Map.of(
//                            // Класс  с элементами
//                            "WEB_ELEMENT", element.findElement(By.xpath("//div[@class='_3VMnE']")),
//                            // Ссылка на подкатегорию
//                            "URL", element.findElement(By.xpath(selectorURL)).getAttribute("href"),
//                            // Название подкатегории
//                            "NAME_CATEGORY", element.findElement(By.xpath(selectorURL)).getText())
//                    )
//            );
//        }
//        return navigationCategoriesMaps;
//    }


    // Поиск среди категорий, в отбразившихся категориях выбирается нужная, затем переход на новою страницу
    public boolean goCategory(String categoryName){
        pageName = "Яндекс.Маркет";
        // На всякий случай переходим на страницу яндекс маркет
        goPage(pageName);
        // Получаем мапы с категориями
        categoriesMaps = getCategoriesMaps();
        // Отбираем элемент с нужной категорией
        WebElement category = (WebElement) categoriesMaps.stream()
                .filter(x -> x.get("NAME_CATEGORY").toString().contains(categoryName))
                .findFirst()
                .get().get("WEB_ELEMENT");
        // Нажимаем на нужную категорию, откроется новая вкладка
        category.click();
        // Переход на страницу с выбранной категорией
        Assertions.assertTrue(goPage(categoryName),
                "Не удалось перейти на страницу с именем " + categoryName);
        return true;
    }

    // Перегрузка метода, с подкатегорией
    public boolean goCategory(String categoryName, String subCategoryName){
        pageName = "Яндекс.Маркет";
        // На всякий случай переходим на страницу яндекс маркет
        goPage(pageName);
        // Получаем мапы с категориями
        categoriesMaps = getCategoriesMaps();
        // Отбираем элемент с нужной категорией
        WebElement category = (WebElement) categoriesMaps.stream()
                .filter(x -> x.get("NAME_CATEGORY").toString().contains(categoryName))
                .findFirst()
                .get().get("WEB_ELEMENT");
        // Наводим мышку к нужной категории
        Actions action = new Actions(driver);
        action.moveToElement(category).build().perform();
        // Ждём, пока прогрузится
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectorMoreInCategories)));
        //Разворачиваем все "Ещё"
        List<WebElement> moreElements = driver.findElements(By.xpath(selectorMoreInCategories));
        try{
            moreElements.forEach(WebElement::click);
        }
        catch (ElementClickInterceptedException e){
            System.out.println("Ошибка! Необходимые элементы перекрыты");
        }
        // Из списка подкатегорий получаем нужную
        subCategoriesMaps = getSubCategoriesMaps();
        WebElement subCategory = (WebElement) subCategoriesMaps.stream()
                .filter(x -> x.get("NAME_CATEGORY").toString().contains(subCategoryName))
                .findFirst()
                .get().get("WEB_ELEMENT");
        // Нажимаем на нужную подкатегорию, откроется новая вкладка
        subCategory.click();
        // Переход на страницу в выбранной подкатегорией с проверкой
        Assertions.assertTrue(goPage(subCategoryName),
                "Не удалось перейти на страницу с именем " + subCategoryName);
        return true;
    }


    // Получение подкатегорий, в зависимости от категории
    public List<WebElement> getSubCategories(){
        subCategories = driver.findElements(By.xpath(selectorSubCategories));
        return subCategories;
    }

    // Получение мапы подкатегорий
    public List<Map<String, Object>> getSubCategoriesMaps(){
        for (WebElement element : getSubCategories()){
            categoriesMaps.add(Map.of(
                    // Элемент категории
                    "WEB_ELEMENT", element,
                    // Ссылка
                    "URL", element.findElement(By.xpath(selectorURL)).getAttribute("href"),
                    // Название
                    "NAME_CATEGORY", element.findElement(By.xpath(selectorURL)).getText()
            ));
        }
        return categoriesMaps;
    }

    // Пробуем перейти на необходимую вкладку в браузере
    private boolean goPage(String pageName){
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs){
            driver.switchTo().window(tab);
            if (driver.getTitle().contains(pageName))
                return true;
        }
        return false;
    }

    // Пробуем задать цену к товарам
    public boolean setPrice(String lowPrice, String highPrice, String pageName){
        Assertions.assertTrue(goPage(pageName), "не удалось перейти на страницу " + pageName);
        WebElement priceFrom = driver.findElement(By.xpath(selectorPriceFrom));
        WebElement priceTo = driver.findElement(By.xpath(selectorPriceTo));
        priceFrom.click();
        priceFrom.sendKeys(lowPrice);
        priceTo.click();
        priceTo.sendKeys(lowPrice);
        return true;
    }

//    // Переход к подкатегории
//    public boolean goSubCategory(String subCategoryName){
//        WebElement subCategory = null;
//        for (Map<String, Map<String, Object>> element : navigationCategoriesMaps){
//            subCategory = (WebElement) element.values().stream()
//                    .filter(x -> x.get("NAME_CATEGORY").toString().contains(subCategoryName))
//                    .findFirst()
//                    .get().get("WEB_ELEMENT");
//        }
//        if (!(subCategory == null)) {
//            subCategory.findElement(By.xpath(selectorURL)).click();
//            List<String> tabs = new ArrayList<>(driver.getWindowHandles());
//            for (String tab : tabs){
//                driver.switchTo().window(tab);
//                if (driver.getTitle().contains(subCategoryName))
//                    return true;
//            }
//        }
//        Assertions.fail();
//        return false;
//    }



}
//          Переход к странице
//        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
//        for (String tab : tabs){
//            driver.switchTo().window(tab);
//            if (driver.getTitle().contains(pageName))
//                return true;
//        }

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@asd]"))); ожидание для дебага
