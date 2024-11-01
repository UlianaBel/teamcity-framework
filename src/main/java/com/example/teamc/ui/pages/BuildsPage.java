package com.example.teamc.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamc.ui.elements.BuildElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuildsPage extends BasePage {
    private static final String BUILDS_URL = "/favorite/builds";

    private ElementsCollection buildElements = $$("div[class*='BuildDetails__container']");

    public static BuildsPage open() {
        return Selenide.open(BUILDS_URL, BuildsPage.class);
    }
    private SelenideElement header = $(".FavoriteBuildsPage__wrapper--TA > div");

    public BuildsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public List<BuildElement> getBuilds() {
        return generatePageElements(buildElements, BuildElement::new);
    }

}