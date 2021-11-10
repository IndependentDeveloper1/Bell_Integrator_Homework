package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class BankSberbank implements Page, CurrencyExchangePage {

    WebDriver driver;

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

    public BankSberbank(WebDriver driver){
        this.driver = driver;
        initPage(driver);
        currencies = new HashMap<>();
    }

    @Override
    public Map<String, Double> getCurrencies() {
        currencies.put("Доллар.Продажа", convertToNormalDouble(dollarSell.getText()));
        currencies.put("Евро.Продажа", convertToNormalDouble(euroSell.getText()));
        currencies.put("Евро.Покупка", convertToNormalDouble(euroBuy.getText()));
        currencies.put("Доллар.Покупка", convertToNormalDouble(dollarBuy.getText()));
        Allure.addAttachment("Курсы банка", currencies.toString());
        return currencies;
    }

    @Override
    public void preActions(){}

    @Override
    public boolean isPageLoaded() {
        return true;
    }
}
