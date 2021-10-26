package com.google;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;


public class SelenideBaseTest {

    @BeforeEach
    public void option(){
        Configuration.timeout = 6000;
        Configuration.startMaximized = true;
    }
}
