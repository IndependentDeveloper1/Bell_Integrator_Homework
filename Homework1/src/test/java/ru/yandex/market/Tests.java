package ru.yandex.market;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import Steps.StepsYandex;
import Steps.StepsYandexMarket;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Tests extends BaseTests {
    /**
     * Задание 1.3
     */
    @Feature("Проверка поиска ноутбуков")
    @DisplayName("Проверка результатов поиска c помощью Page Object")
    @ParameterizedTest
    @MethodSource("searchNotebooksProvider")
    public void testSearchNotebooks(String nameYandexService, String category,
                                    String subCategory, int lowPrice, int highPrice,
                                    List<String> productMakers,
                                    int countOnPage){
        chromeDriver.get("https://yandex.ru/");
        StepsYandex.testMarketFromYandex(nameYandexService, chromeDriver);
        StepsYandexMarket.testGoToCategory(category, subCategory, chromeDriver);
        StepsYandexMarket.testSetPrice(lowPrice, highPrice, chromeDriver);
        StepsYandexMarket.testSetProductMaker(productMakers, chromeDriver);
        StepsYandexMarket.testChangeCountProductsOnPage(countOnPage, chromeDriver);
        StepsYandexMarket.testCountProductsOnPage(countOnPage, chromeDriver);
        StepsYandexMarket.testFindFirst(chromeDriver);
    }

    /**
     * Задание 1.4
     */
    @Feature("Проверка поиска смартфонов")
    @DisplayName("Проверка результатов поиска c помощью Page Object")
    @ParameterizedTest
    @MethodSource("searchSmartphonesProvider")
    public void testSearchSmartphones(String nameYandexService, String category,
                                      String subCategory, int lowPrice, int highPrice,
                                      List<String> productMakers,
                                      int countOnPage){
        chromeDriver.get("https://yandex.ru/");
        StepsYandex.testMarketFromYandex(nameYandexService, chromeDriver);
        StepsYandexMarket.testGoToCategory(category, subCategory, chromeDriver);
        StepsYandexMarket.testSetProductMaker(productMakers, chromeDriver);
        StepsYandexMarket.testChangeCountProductsOnPage(countOnPage, chromeDriver);
        StepsYandexMarket.testCountProductsOnPage(countOnPage, chromeDriver);
        StepsYandexMarket.testAllProducts(productMakers, chromeDriver);
    }

    public static Stream<Arguments> searchNotebooksProvider(){
        return Stream.of(
                Arguments.of("Маркет", "Компьютеры", "Ноутбуки",
                        10000, 30000, Arrays.asList("HP", "Lenovo"), 12)
        );
    }

    public static Stream<Arguments> searchSmartphonesProvider(){
        return Stream.of(
                Arguments.of("Маркет", "Электроника", "Смартфоны",
                        0, 0, Arrays.asList("Apple"), 12)
        );
    }
}
