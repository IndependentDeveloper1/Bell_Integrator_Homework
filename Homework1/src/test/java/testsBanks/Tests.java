package testsBanks;

import Steps.*;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.BankAlfa;
import pages.BankOpen;
import pages.BankSberbank;
import pages.BankVTB;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Tests extends BaseTests{

    /**
     * Тест с кейсом проверки курсов обмена валют в банках
     * @param rateDollar курс продажи доллара, используем для сравнения
     */
    @Feature("Проверка курсов банков")
    @DisplayName("Проверка банков с курсом продажи доллара {rateDollar}")
    @ParameterizedTest
    @MethodSource("banksRates")
    public void testCurrencyRates(double rateDollar){
        Map<String, Map<String, Double>> currencyRatesBanks = new HashMap<>();

        chromeDriver.get("https://www.open.ru/");
        BankOpen bankOpen = new BankOpen(chromeDriver);
        currencyRatesBanks.put("Открытие", StepsBanks.getBankCurrency(bankOpen, "Открытие"));

        chromeDriver.get("https://www.vtb.ru/personal/platezhi-i-perevody/obmen-valjuty/");
        BankVTB bankVTB = new BankVTB(chromeDriver);
        currencyRatesBanks.put("ВТБ", StepsBanks.getBankCurrency(bankVTB, "ВТБ"));

        chromeDriver.get("https://alfabank.ru/currency/");
        BankAlfa bankAlfa = new BankAlfa(chromeDriver);
        currencyRatesBanks.put("Альфа", StepsBanks.getBankCurrency(bankAlfa, "Альфа"));

        chromeDriver.get("https://www.sberbank.ru/ru/quotes/currencies");
        BankSberbank bankSberbank = new BankSberbank(chromeDriver);
        currencyRatesBanks.put("Сбербанк", StepsBanks.getBankCurrency(bankSberbank, "Сбербанк"));

        StepsBanks.isDifferentBig(currencyRatesBanks);
        StepsBanks.isDollarHigh(rateDollar, currencyRatesBanks);

    }

    /**
     * Входные параметры для теста
     * @return Аргументы для теста
     */
    public static Stream<Arguments> banksRates(){
        return Stream.of(
                Arguments.of(72.1)
        );
    }
}
