package Steps;

import drivers.WebDriverManager;
import io.qameta.allure.Step;

public class Steps {

    @Step("Перейти на страницу {url}")
    public static void goPage(String url) {
        WebDriverManager.getCurrentDriver().get(url);
    }

}
