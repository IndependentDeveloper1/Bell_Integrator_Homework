package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class BankOpen implements Page, CurrencyExchangePage{

    @FindBy(xpath = "//div[@role = 'tab' and contains(., 'Стандартный курс')]")
    private WebElement standardCurrencyButton;

    @FindBy(xpath = "//div[@id ='rc-tabs-1-panel-defaultRate']//tr[3]/td[2]//span")
    private WebElement euroBuy;

    @FindBy(xpath = "//div[@id ='rc-tabs-1-panel-defaultRate']//tr[3]/td[4]//span")
    private WebElement euroSell;

    @FieldName("Покупка доллара")
    @FindBy(xpath = "//div[@id ='rc-tabs-1-panel-defaultRate']//tr[2]/td[2]//span")
    private WebElement dollarBuy;

    @FindBy(xpath = "//div[@id ='rc-tabs-1-panel-defaultRate']//tr[2]/td[4]//span")
    private WebElement dollarSell;

    private Map<String, Double> currencies;

    public BankOpen() {
        initPage();
        currencies = new HashMap<>();
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
        standardCurrencyButton.click();
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }
}
