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
import pages.YandexMarketPO;

public class Tests extends BaseTests {



    private final static String yandexAddr = "https://yandex.ru/";

    @Feature("Проверка ")
    @DisplayName("Проверка результатов поиска c помощью Page Object")
    @Test
    public void testSearchPO() {
        chromeDriver.get(yandexAddr);
        testMarketFromYandexMain("Маркет");
        testGoToCategory("Компьютеры", "Ноутбуки", chromeDriver);
        testSetPrice("10000","30000","Ноутбуки");
    }


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
        YandexMarketPO yandexMarketPO = new YandexMarketPO(driver);
        Assertions.assertTrue(yandexMarketPO.goCategory(category, subCategory),
                "Не удалось перейти в категорию " + category +
                        " с выбранной подкатегорией " + subCategory);
    }

    @Step("Задаем параметр цены")
    private void testSetPrice(String lowPrice, String highPrice, String pageName){
        YandexMarketPO yandexMarketPO = new YandexMarketPO(chromeDriver);
        yandexMarketPO.setPrice(lowPrice, highPrice, pageName);
    }

}
