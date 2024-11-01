package com.example.teamc.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BuildTypePage extends BasePage {
    private static final String CONFIG_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=createBuildTypeMenu";

    public SelenideElement title = $("span[class*='BuildConfigurationHeader']");

    public static BuildTypePage open(String projectId) {
        return Selenide.open(CONFIG_URL.formatted(projectId), BuildTypePage.class);
    }
}
