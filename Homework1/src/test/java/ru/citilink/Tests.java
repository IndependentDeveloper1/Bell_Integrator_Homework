package ru.citilink;

import Steps.StepsCitilink;
import drivers.WebDriverManager;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Tests extends BaseTests{

    @Feature("Проверка стоимости видеокарт")
    @DisplayName("Проверка сразницы цен видеокарт:")
    @ParameterizedTest
    @MethodSource({"testPriceVideoCardsFirst", "testPriceVideoCardsSecond"})
    public void testVideoCards(List<String> cardsName){
        chromeDriver.get("https://www.citilink.ru");
        StepsCitilink.gotoSubCategory("Ноутбуки и компьютеры", "Видеокарты", chromeDriver);
        Map<String, Integer> videocardsMinPrice = new HashMap<>();
        StepsCitilink.setSortPriceFilter(chromeDriver);
        for (String videoCardName : cardsName){
            StepsCitilink.serachGraphicProcessors(videoCardName, chromeDriver);
            videocardsMinPrice.put(videoCardName, StepsCitilink.getMinPrice(videoCardName, chromeDriver));
        }
        StepsCitilink.compareVideoCards(videocardsMinPrice, cardsName.get(0), cardsName.get(1));

    }

    public static Stream<Arguments> testPriceVideoCardsFirst(){
        return Stream.of(
                Arguments.of(Arrays.asList("GTX 1660TI", "RTX 3060"))
        );
    }

    public static Stream<Arguments> testPriceVideoCardsSecond(){
        return Stream.of(
                Arguments.of(Arrays.asList("RX 6600", "RX 6900XT"))
        );
    }
}
