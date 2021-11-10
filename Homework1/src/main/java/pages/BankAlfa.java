package pages;

import drivers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class BankAlfa implements Page, CurrencyExchangePage {

    @FindBy(xpath = "//div[contains(@id, 'footer')]//a[contains(@href, '/currency/')]/..")
    private WebElement exchangeRatesLink;

    @FindBy(xpath = "//button[contains(@data-test-id, 'currency-EUR')]")
    private WebElement buyEURButton;

    @FindBy(xpath = "//button[contains(@data-test-id, 'currency-USD')]")
    private WebElement buyUSDButton;

    @FieldName("Покупка евро")
    @FindBy(xpath = "//*[@id='alfa']/div/div[3]/div/div/div[2]/div/div[2]/div/table/tbody/tr[1]/td[2]")
    private WebElement euroBuy;

    @FieldName("Продажа евро")
    @FindBy(xpath = "//*[@id='alfa']/div/div[3]/div/div/div[2]/div/div[2]/div/table/tbody/tr[2]/td[2]")
    private WebElement euroSell;

    @FieldName("Покупка доллара")
    @FindBy(xpath = "//*[@id='alfa']/div/div[3]/div/div/div[2]/div/div[2]/div/table/tbody/tr[1]/td[2]")
    private WebElement dollarBuy;

    @FieldName("Продажа доллара")
    @FindBy(xpath = "//*[@id='alfa']/div/div[3]/div/div/div[2]/div/div[2]/div/table/tbody/tr[1]/td[2]")
    private WebElement dollarSell;

    private Map<String, Double> currencies;

    public BankAlfa(){
        initPage();
        currencies = new HashMap<>();
    }

    @Override
    public Map<String, Double> getCurrencies() {
        currencies.put("Доллар.Продажа", convertToNormalDouble(dollarSell.getText().substring(0, 5)));
        currencies.put("Евро.Продажа", convertToNormalDouble(euroSell.getText().substring(0, 5)));
        buyEURButton.click();
        currencies.put("Евро.Покупка", convertToNormalDouble(euroBuy.getText().substring(0, 5)));
        buyUSDButton.click();
        currencies.put("Доллар.Покупка", convertToNormalDouble(dollarBuy.getText().substring(0, 5)));
        return currencies;
    }

    @Override
    public void preActions() {
        WebElement body = WebDriverManager.getCurrentDriver().findElement(By.xpath("//body"));
        body.click();
        body.sendKeys(Keys.END);
        exchangeRatesLink.click();
    }

    @Override
    public boolean isPageLoaded() {
        return true;
    }
}
