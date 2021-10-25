package ru.open;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import pages.GoogleAfterSearch;
import pages.GoogleBeforeSearch;
import pages.OpenBankMain;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tests extends BaseTests{

    private final static String googleAddr = "https://www.google.com/";

    /**
     * Задание 1.2
     * @throws ParseException
     */
    @Feature("Проверка результатов поиска")
    @DisplayName("Поиск сайта банка Открытие, и информации на нём об обмене валют")
    @Test
    public void testOpenBankPO() throws ParseException {
        chromeDriver.get(googleAddr);
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.find("Открытие");
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        Assertions.assertTrue(googleAfterSearch.getElements()
                        .stream()
                        .anyMatch(a -> a.getText().contains("https://www.open.ru")),
                "После ввода Открытие сайт 'Банк Открытие' в выдаче не найден");

        chromeDriver.get("https://www.open.ru");
        OpenBankMain openBankMain = new OpenBankMain(chromeDriver);
        List<WebElement> currencyExchanges = openBankMain.getCurrencyExchange();

        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        List<Double> rateValues = new ArrayList<>();
        for (WebElement e : currencyExchanges){
            Number number = format.parse(e.getText());
            rateValues.add(number.doubleValue());
        }
        Assertions.assertTrue(rateValues.get(0) < rateValues.get(1) &&
                rateValues.get(2) < rateValues.get(3),
                "Курс продажи меньше курса покупки для USD, либо EUR");
    }
}
