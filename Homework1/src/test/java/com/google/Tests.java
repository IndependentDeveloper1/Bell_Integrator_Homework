package com.google;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.GoogleAfterSearch;
import pages.GoogleBeforeSearch;
import pages.GooglePF;

public class Tests extends BaseTests {

    private final static String googleAddr = "https://www.google.com/";

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью Page Object")
    @Test
    public void testSearchPO(){
        chromeDriver.get(googleAddr);
        GoogleBeforeSearch googleBeforeSearch = new GoogleBeforeSearch(chromeDriver);
        googleBeforeSearch.find("Гладиолус");
        GoogleAfterSearch googleAfterSearch = new GoogleAfterSearch(chromeDriver);
        Assertions.assertTrue(googleAfterSearch.getElements()
                .stream()
                .anyMatch(a -> a.getText().contains("https://ru.wikipedia.org ")),
                "После ввода Гладиолус статья Википпедии в выдаче не найдена");
    }

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью Page Factory")
    @Test
    public void testSearchPF(){
        chromeDriver.get(googleAddr);
        GooglePF googlePF = PageFactory.initElements(chromeDriver, GooglePF.class);
        googlePF.find("Гладиолус");
        Assertions.assertTrue(googlePF.getElements()
                        .stream()
                        .anyMatch(a -> a.getText().contains("https://ru.wikipedia.org ")),
                "После ввода Гладиолус статья Википпедии в выдаче не найдена");
    }
}
