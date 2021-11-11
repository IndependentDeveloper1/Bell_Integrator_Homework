package Steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.CitilinkMainPage;
import pages.CitilinkProducts;

import java.util.List;
import java.util.Map;

public class StepsCitilink {

    @Step("Переход к категории {categoryName} и подкатегории {subCategoryName}")
    public static void gotoSubCategory(String categoryName, String subCategoryName, WebDriver driver){
        CitilinkMainPage citilinkMainPage = new CitilinkMainPage(driver);
        citilinkMainPage.goSubCat(categoryName, subCategoryName);
    }

    @Step("Поиск видеокарты {cardName}")
    public static void serachGraphicProcessors(String cardName, WebDriver driver){
        CitilinkProducts citilinkProducts = new CitilinkProducts(driver);
        citilinkProducts.setGraphicProcessors(cardName);
    }

    @Step("Установка фильтра по увеличению стоимости")
    public static void setSortPriceFilter(WebDriver driver){
        CitilinkProducts citilinkProducts = new CitilinkProducts(driver);
        citilinkProducts.setSortPriceFilter();
    }

    @Step("Получение минимальной цены на видеокарту {cardName}")
    public static int getMinPrice(String cardName, WebDriver driver){
        CitilinkProducts citilinkProducts = new CitilinkProducts(driver);
        int minPrice = citilinkProducts.getCheapestVideoCard(cardName);
        Assertions.assertTrue(minPrice != 0, "Видеокарта " + cardName + " не найдена");
        return minPrice;
    }

    @Step("Сравнение цен на видеокарты. {lowPriceCard} должна быть дешевле {highPriceCard}")
    public static void compareVideoCards(Map<String, Integer> videoCards, String lowPriceCard, String highPriceCard){
        Allure.addAttachment("Видеокарты: ", videoCards.toString());
        Assertions.assertTrue(videoCards.get(lowPriceCard) < videoCards.get(highPriceCard));
    }

}
