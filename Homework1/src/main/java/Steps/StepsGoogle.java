package Steps;

import drivers.WebDriverManager;
import io.qameta.allure.Step;
import pages.GooglePageWithSearchPOFactory;

public class StepsGoogle {

    @Step("Ввести в поисковую строку значение и найти")
    public static void findSearchGoogle(GooglePageWithSearchPOFactory googlePageWithSearchPOFactory,
                                  String searchField){
        googlePageWithSearchPOFactory.find(searchField);
    }

    @Step("Перейти на страницу {pageName}")
    public static void goPageGoogle(GooglePageWithSearchPOFactory googlePageWithSearchPOFactory,
                                    String pageName){
        googlePageWithSearchPOFactory.goPage(pageName);
    }
}
