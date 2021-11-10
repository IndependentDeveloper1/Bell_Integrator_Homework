package Steps;

import io.qameta.allure.Step;
import pages.CurrencyExchangePage;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class StepsBanks {

    @Step("Получение курсов со страницы банка {bankName}")
    public static Map<String, Double> getBankCurrency(CurrencyExchangePage page, String bankName) {
        page.preActions();
        return page.getCurrencies();
    }

    @Step("Проверка, разница наилучшего и наихужшего курса валют не превышает 1 у./е.")
    public static boolean isDifferentBig(Map<String, Map<String, Double>> exchangesRates){
        Map<String, Double> lowRatesMap = exchangesRates.get("Альфа");
        Map<String, Double> highRatesMap = exchangesRates.get("Открытие");
        for (Map<String, Double> map : exchangesRates.values()){
            if (lowRatesMap.get("Доллар.Продажа").doubleValue() > map.get("Доллар.Продажа").doubleValue()){
                lowRatesMap = map;
            }
            if (highRatesMap.get("Доллар.Продажа").doubleValue() < map.get("Доллар.Продажа").doubleValue()){
                highRatesMap = map;
            }
        }
        System.out.println("Лучший курс: \n" + lowRatesMap.toString());
        System.out.println("Худший курс: \n" + highRatesMap.toString());
        return (highRatesMap.get("Доллар.Продажа").doubleValue() - lowRatesMap.get("Доллар.Продажа").doubleValue() < 1);
    }

    @Step("Проверка, превышает ли курс покупки банком доллара {rate} р.")
    public static boolean isDollarHigh(double rate, Map<String, Map<String, Double>> exchangesRates){
        boolean result = exchangesRates.values().stream()
                .allMatch(x -> x.get("Доллар.Продажа") <= rate);
        return result;
    }
}
