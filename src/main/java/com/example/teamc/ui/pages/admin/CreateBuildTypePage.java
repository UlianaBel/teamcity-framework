package com.example.teamc.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamc.ui.pages.ProjectPage;

import static com.codeborne.selenide.Selenide.*;

public class CreateBuildTypePage extends CreateBasePage {
    private static final String BUILD_CONFIG_SHOW_MODE = "createBuildTypeMenu";

    protected SelenideElement emptyBuildTypeName = $x("//span[@id='error_buildTypeName' and contains(text(), 'Build configuration name must not be empty')]");

    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_CONFIG_SHOW_MODE), CreateBuildTypePage.class);
    }

    public CreateBuildTypePage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public ProjectPage setupBuildType(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return page(ProjectPage.class);
    }

    public CreateBuildTypePage setupBuildTypeWithError(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        emptyBuildTypeName.should(Condition.appear, BASE_WAITING);
        return this;
    }
}