package ru.yandex;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import pages.YandexBeforeSearch;
import pages.YandexMarketCatalog;
import pages.YandexMarketMainPage;

import java.util.ArrayList;
import java.util.List;

public class Tests extends BaseTests {



    private final static String yandexAddr = "https://yandex.ru/";

    /**
     * Задание 1.3
     * @throws InterruptedException
     */
    @Feature("Проверка поиска ноутбукок")
    @DisplayName("Проверка результатов поиска c помощью Page Object")
    @Test
    public void testSearchNotebooks() throws InterruptedException {
        chromeDriver.get(yandexAddr);
        testMarketFromYandexMain("Маркет");
        testGoToCategory("Компьютеры", "Ноутбуки", chromeDriver);
        testSetPrice("10000","30000");
        List<String> productMakers = new ArrayList<>();
        productMakers.add("HP");
        productMakers.add("Lenovo");
        testSetProductMaker(productMakers);
        testChangeCountProductsOnPage("12");
        testCountProductsOnPage(12);
        testFindFirst();
    }

//region Шаги 1.3
    @Step("Проверяем, есть ли Маркет на странице Яндекса и переходим на него")
    @ParameterizedTest(name="{displayName} {arguments}")
    @CsvSource({"Маркет"})
    private void testMarketFromYandexMain(String name) {
        YandexBeforeSearch yandexBeforeSearch = new YandexBeforeSearch(chromeDriver);
        Assertions.assertTrue(yandexBeforeSearch.isExist(name));
        yandexBeforeSearch.marketClick();
    }

    @Step("Переход в категорию с подкатегорией")
    private void testGoToCategory(String category, String subCategory, WebDriver driver){
        YandexMarketMainPage yandexMarketMainPage = new YandexMarketMainPage(driver);
        Assertions.assertTrue(yandexMarketMainPage.goCategory(category, subCategory),
                "Не удалось перейти в категорию " + category +
                        " с выбранной подкатегорией " + subCategory);
    }

    @Step("Задаем параметр цены от {lowPrice}р до {highPrice}р")
    private void testSetPrice(String lowPrice, String highPrice){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(chromeDriver);
        Assertions.assertTrue(yandexMarketCatalog.setPrice(lowPrice, highPrice),
                "Не удалось задать цену в промежутке от " + lowPrice +
                "р до " + highPrice + " р");
    }

    @Step("Задаем производителей товаров: {productMakers}")
    @ParameterizedTest(name="{displayName} {arguments}")
    @CsvSource({"HP, Lenovo"})
    private void testSetProductMaker(List<String> productMakers){
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(chromeDriver);
        Assertions.assertTrue(yandexMarketCatalog.setProductMakers(productMakers),
                "Не удалось выбрать производителей");
    }

    @Step("Меняем число отображаемых продуктов на странице на {}")
    @ParameterizedTest(name="{displayName} {arguments}")
    @CsvSource({"12"})
    private void testChangeCountProductsOnPage(String countProducts) throws InterruptedException {
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(chromeDriver);
        Assertions.assertTrue(yandexMarketCatalog.setCountProductsOnPage(countProducts),
                "Не удалось изменить значение отображаемых товаров на " + countProducts);
    }

    @Step("Проверяем, сколько товаров на странице")
    @ParameterizedTest(name="{displayName} {arguments}")
    @CsvSource({"12"})
    private void testCountProductsOnPage(int count) throws InterruptedException {
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(chromeDriver);
        Assertions.assertTrue(yandexMarketCatalog.checkCountProducts(count),
                "Количество:" + count + " не совпадает с количеством на странице");
    }

    @Step("Берем название первого товара на странице и ищем в поиске")
    private void testFindFirst() throws InterruptedException {
        YandexMarketCatalog yandexMarketCatalog = new YandexMarketCatalog(chromeDriver);
        String firstProductName = yandexMarketCatalog.getNameProductById(0);
        Assertions.assertTrue(yandexMarketCatalog.searchProduct(firstProductName),
                "Товар с именем " + firstProductName + " не найден");
    }
//endregion
    //region Задание 1.4

    @Feature("Проверка поиска смартфонов")
    @DisplayName("Проверка результатов поиска c помощью Page Object")
    @Test
    public void testSearchSmartphones() throws InterruptedException {
        chromeDriver.get(yandexAddr);
        testMarketFromYandexMain("Маркет");
        testGoToCategory("Электроника", "Смартфоны", chromeDriver);
        //testSetPrice("10000","30000");
        List<String> productMakers = new ArrayList<>();
        productMakers.add("Apple");
        testSetProductMaker(productMakers);
        testChangeCountProductsOnPage("12");
        testCountProductsOnPage(12);
        testFindFirst();
    }


    //endregion





}
