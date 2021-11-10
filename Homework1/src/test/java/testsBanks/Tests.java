package testsBanks;

import Steps.*;
import drivers.WebDriverManager;
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

public class Tests{

    /**
     * Тест с кейсом проверки курсов обмена валют в банках
     * @param rateDollar курс продажи доллара, используем для сравнения
     */
    @Feature("Проверка курсов банков")
    @DisplayName("Проверка банков с курсом продажи доллара {rateDollar}")
    @ParameterizedTest
    @MethodSource("banksRates")
    public void testCurrencyRates(double rateDollar){
        WebDriverManager.initChrome();
        Map<String, Map<String, Double>> currencyRatesBanks = new HashMap<>();

        WebDriverManager.getCurrentDriver().get("https://www.open.ru/");
        BankOpen bankOpen = new BankOpen();
        currencyRatesBanks.put("Открытие", StepsBanks.getBankCurrency(bankOpen, "Открытие"));

        WebDriverManager.getCurrentDriver().get("https://www.vtb.ru/");
        BankVTB bankVTB = new BankVTB();
        currencyRatesBanks.put("ВТБ", StepsBanks.getBankCurrency(bankVTB, "ВТБ"));

        WebDriverManager.getCurrentDriver().get("https://alfabank.ru/");
        BankAlfa bankAlfa = new BankAlfa();
        currencyRatesBanks.put("Альфа", StepsBanks.getBankCurrency(bankAlfa, "Альфа"));

        WebDriverManager.getCurrentDriver().get("https://www.sberbank.ru/");
        BankSberbank bankSberbank = new BankSberbank();
        currencyRatesBanks.put("Сбербанк", StepsBanks.getBankCurrency(bankSberbank, "Сбербанк"));

        System.out.println("Разница лучшего у худшего курсов не более 1 у.е.: " + StepsBanks.isDifferentBig(currencyRatesBanks));
        System.out.println("Продажа доллара банков не превышает " + rateDollar + ": " + StepsBanks.isDollarHigh(rateDollar, currencyRatesBanks));

        System.out.println("\nИнформация по банкам:\n");
        for(Map.Entry<String, Map<String, Double>> pair : currencyRatesBanks.entrySet()){
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
        WebDriverManager.killCurrentDriver();
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
