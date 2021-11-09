package Steps;

import drivers.WebDriverManager;
import io.qameta.allure.Step;
import pages.CurrencyExchangePage;


public class Steps {

    @Step("Перейти на страницу {url}")
    public static void goPage(String url) {
        WebDriverManager.getCurrentDriver().get(url);
    }

    @Step("Получение курса {currencyType} со страницы банка")
    public static Double getBankCurrency(CurrencyExchangePage page, String currencyType) {
        page.preActions();
        return page.getCurrencies().get(currencyType);
    }




}
