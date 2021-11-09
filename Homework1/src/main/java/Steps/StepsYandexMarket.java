package Steps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.YandexMarketCatalog;
import pages.YandexMarketMainPage;

import java.util.List;

public class StepsYandexMarket {

    @Step("Переход в категорию с подкатегорией")
    public static void testGoToCategory(String category, String subCategory, WebDriver driver){
        YandexMarketMainPage yandexMarketMainPage = new YandexMarketMainPage(driver);
        Assertions.assertTrue(yandexMarketMainPage.goCategory(category, subCategory),
                "Не удалось перейти в категорию " + category +
                        " с выбранной подкатегорией " + subCategory);
    }

    @Step("Задаем параметр цены от {lowPrice}р до {highPrice}р")
    public static void testSetPrice(int lowPrice, int highPrice, WebDriver driver){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(driver);
        Assertions.assertTrue(yandexMarketCatalog.setPrice(lowPrice, highPrice),
                "Не удалось задать цену в промежутке от " + lowPrice +
                        "р до " + highPrice + " р");
    }

    @Step("Задаем производителей товаров: {productMakers}")
    public static void testSetProductMaker(List<String> productMakers, WebDriver driver){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(driver);
        Assertions.assertTrue(yandexMarketCatalog.setProductMakers(productMakers),
                "Не удалось выбрать производителей");
    }

    @Step("Меняем число отображаемых продуктов на странице на {}")
    public static void testChangeCountProductsOnPage(int countProducts, WebDriver driver){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(driver);
        Assertions.assertTrue(yandexMarketCatalog.setCountProductsOnPage(countProducts),
                "Не удалось изменить значение отображаемых товаров на " + countProducts);
    }

    @Step("Проверяем, сколько товаров на странице")
    public static void testCountProductsOnPage(int count, WebDriver driver){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(driver);
        Assertions.assertTrue(yandexMarketCatalog.checkCountProducts(count),
                "Количество:" + count + " не совпадает с количеством на странице");
    }

    @Step("Берем название первого товара на странице и ищем в поиске")
    public static void testFindFirst(WebDriver driver){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(driver);
        String firstProductName = yandexMarketCatalog.getNameProductByNumber(1);
        Assertions.assertTrue(yandexMarketCatalog.searchProduct(firstProductName),
                "Товар с именем " + firstProductName + " не найден");
    }

    @Step("Проверка всех результатов поиска")
    public static void testAllProducts(List<String> productMakers, WebDriver driver){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(driver);
        Assertions.assertTrue(yandexMarketCatalog.checkAllProducts(productMakers),
                "Среди результатов не были найдены производители " + productMakers.toString());
    }
}
