package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class BankVTB implements Page, CurrencyExchangePage {

    @FindBy(xpath = "//div[contains(@class, 'card service-card')][.//div[contains(., 'Курсы валют')]]")
    private WebElement exchangeRatesLink;

    @FindBy(xpath = "(//li[contains(@class, 'simple-tab__heads-item')])[5]/a/span")
    private WebElement cardBuyExchanges;

    @FieldName("Покупка евро")
    @FindBy(xpath = "(//td[contains(@ng-if, 'moneyRate.FromCurrencyCode')])[1]")
    private WebElement euroBuy;

    @FieldName("Продажа евро")
    @FindBy(xpath = "(//td[contains(@ng-if, 'moneyRate.FromCurrencyCode')])[2]")
    private WebElement euroSell;

    @FieldName("Покупка доллара")
    @FindBy(xpath = "(//td[contains(@ng-if, 'moneyRate.FromCurrencyCode')])[3]")
    private WebElement dollarBuy;

    @FieldName("Продажа доллара")
    @FindBy(xpath = "(//td[contains(@ng-if, 'moneyRate.FromCurrencyCode')])[4]")
    private WebElement dollarSell;

    private Map<String, Double> currencies;

    public BankVTB(){
        initPage();
        currencies = new HashMap<>();
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }

    @Override
    public Map<String, Double> getCurrencies() {
        currencies.put("Доллар.Покупка", convertToNormalDouble(dollarBuy.getText()));
        currencies.put("Доллар.Продажа", convertToNormalDouble(dollarSell.getText()));
        currencies.put("Евро.Покупка", convertToNormalDouble(euroBuy.getText()));
        currencies.put("Евро.Продажа", convertToNormalDouble(euroSell.getText()));
        return currencies;
    }

    @Override
    public void preActions() {
        exchangeRatesLink.click();
        cardBuyExchanges.click();
    }
}
