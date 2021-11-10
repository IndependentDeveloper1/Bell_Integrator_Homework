package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class BankSberbank implements Page, CurrencyExchangePage {

    @FindBy(xpath = "(//a[contains(@data-cga_click_extra_link, 'Курсы валют')])[1]")
    private WebElement exchangeRatesLink;

    @FieldName("Покупка евро")
    @FindBy(xpath = "(//table[contains(@class, 'rates-table-component')])[1]/tbody/tr[7]/td[3]/div/div")
    private WebElement euroBuy;

    @FieldName("Продажа евро")
    @FindBy(xpath = "(//table[contains(@class, 'rates-table-component')])[1]/tbody/tr[7]/td[4]/div/div")
    private WebElement euroSell;

    @FieldName("Покупка доллара")
    @FindBy(xpath = "(//table[contains(@class, 'rates-table-component')])[1]/tbody/tr[2]/td[3]/div/div")
    private WebElement dollarBuy;

    @FieldName("Продажа доллара")
    @FindBy(xpath = "(//table[contains(@class, 'rates-table-component')])[1]/tbody/tr[2]/td[4]/div/div")
    private WebElement dollarSell;

    private Map<String, Double> currencies;

    public BankSberbank(){
        initPage();
        currencies = new HashMap<>();
    }

    @Override
    public Map<String, Double> getCurrencies() {
        currencies.put("Доллар.Продажа", convertToNormalDouble(dollarSell.getText()));
        currencies.put("Евро.Продажа", convertToNormalDouble(euroSell.getText()));
        currencies.put("Евро.Покупка", convertToNormalDouble(euroBuy.getText()));
        currencies.put("Доллар.Покупка", convertToNormalDouble(dollarBuy.getText()));
        return currencies;
    }

    @Override
    public void preActions() {
        exchangeRatesLink.click();
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }
}
