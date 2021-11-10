package pages;

import java.util.Map;

public interface CurrencyExchangePage {
    Map<String, Double> getCurrencies();
    void preActions();

    default Double convertToNormalDouble(String currencyString) {
        return Double.parseDouble(currencyString.replaceAll(",", ".").trim());
    }

}
