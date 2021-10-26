package com.google;

import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTests extends SelenideBaseTest{

    private String googleAddr = "https://www.google.com/";
    private String selectorSearchGoogle = "Гладиолус";

    /**
     * Выполнение первого задания с использованием selenide
     */
    @Test
    public void testGladiolus(){
        open(googleAddr);
        $(By.name("q")).setValue(selectorSearchGoogle).pressEnter();
        ElementsCollection resultSearch = $$(By.xpath("//div[contains(@class, 'g')]"));
        Assertions.assertTrue(resultSearch.find(text("Шпажник - Википедия")).isDisplayed(),
                "Не найдена статья на википедии");
    }
}
