package Steps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.YandexBeforeSearch;

public class StepsYandex {

    @Step("Проверяем, есть ли Маркет на странице Яндекса и переходим на него")
    public static void testMarketFromYandex(String name, WebDriver driver){
        YandexBeforeSearch yandexBeforeSearch = new YandexBeforeSearch(driver);
        Assertions.assertTrue(yandexBeforeSearch.isExist(name));
        yandexBeforeSearch.marketClick();
    }
}
