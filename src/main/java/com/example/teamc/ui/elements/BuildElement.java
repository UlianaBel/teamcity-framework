package com.example.teamc.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildElement extends  BasePageElement {
    private SelenideElement name;
    private SelenideElement link;
    private SelenideElement button;

    public BuildElement(SelenideElement element) {
        super(element);
        this.name = find("span[class*='BuildPath__item']");
        this.link = find("a");
        this.button = find("button");
    }
}