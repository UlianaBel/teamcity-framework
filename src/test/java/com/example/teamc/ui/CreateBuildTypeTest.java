package com.example.teamc.ui;

import com.codeborne.selenide.Condition;
import com.example.teamc.api.enums.Endpoint;
import com.example.teamc.api.models.Project;
import com.example.teamc.api.models.BuildType;
import com.example.teamc.api.requests.CheckedRequests;
import com.example.teamc.api.spec.Specifications;
import com.example.teamc.ui.pages.ProjectPage;
import com.example.teamc.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

import static com.example.teamc.api.enums.Endpoint.PROJECTS;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test(description = "User should be able to create build type", groups = {"Positive"})
    public void userCreatesBuildType() {
        // Подготовка окружения
        loginAs(testData.getUser());

        // Создание проекта через API
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Взаимодействие с UI для создания билд-типа
        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType, "Build Type was not created");

        // Проверка на UI
        ProjectPage.open(createdProject.getId())
                .build.shouldHave(Condition.exactText(testData.getBuildType().getName()));

        // Лишнее на проверку build type в favorite, жалко удалять
//        var buildExists = BuildsPage.open()
//                .getBuilds().stream()
//                .anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName())); // Проверка на наличие билд-типа
//        softy.assertTrue(buildExists, "Build Type does not exist in the list");

    }

    @Test(description = "User should not be able to create build configuration without build type name", groups = {"Negative"})
    public void userFailsToCreateBuildTypeWithoutBuildTypeName() {
        // Подготовка окружения
        loginAs(testData.getUser());

        // Создание проекта через API
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Попытка создать билд-конфигурацию c пустым именем
        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildTypeWithError("");
    }
}