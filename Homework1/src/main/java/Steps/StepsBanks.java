package Steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.CurrencyExchangePage;

import java.util.Map;

public class StepsBanks {

    @Step("Получение курсов со страницы банка {bankName}")
    public static Map<String, Double> getBankCurrency(CurrencyExchangePage page, String bankName) {
        page.preActions();
        return page.getCurrencies();
    }

    @Step("Проверка, разница наилучшего и наихужшего курса валют не превышает 1 у./е.")
    public static void isDifferentBig(Map<String, Map<String, Double>> exchangesRates){
        Map<String, Double> lowRatesMap = exchangesRates.get("Альфа");
        Map<String, Double> highRatesMap = exchangesRates.get("Открытие");
        for (Map<String, Double> map : exchangesRates.values()){
            if (lowRatesMap.get("Доллар.Продажа") > map.get("Доллар.Продажа")){
                lowRatesMap = map;
            }
            if (highRatesMap.get("Доллар.Продажа") < map.get("Доллар.Продажа")){
                highRatesMap = map;
            }
        }
        Allure.addAttachment("Наименьший курс", lowRatesMap.toString());
        Allure.addAttachment("Наибольший курс", highRatesMap.toString());
        Assertions.assertTrue(highRatesMap.get("Доллар.Продажа") - lowRatesMap.get("Доллар.Продажа") < 1,
                "Разница между наилучшим и наихудшим курсами больше 1 у.е.");
    }

    @Step("Проверка, превышает ли курс покупки банком доллара {rate} р.")
    public static void isDollarHigh(double rate, Map<String, Map<String, Double>> exchangesRates){
        boolean result = exchangesRates.values().stream()
                .allMatch(x -> x.get("Доллар.Продажа") <= rate);
        Assertions.assertTrue(result, "Курс покупки банком доллара превышает " + rate);
    }
}
