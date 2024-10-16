package com.example.teamc.api;

import com.example.teamc.api.generators.IdGenerator;
import com.example.teamc.api.generators.TestDataGenerator;
import com.example.teamc.api.models.Project;
import com.example.teamc.api.requests.CheckedRequests;
import com.example.teamc.api.requests.unchecked.UncheckedBase;
import com.example.teamc.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamc.api.enums.Endpoint.PROJECTS;
import static com.example.teamc.api.enums.Endpoint.USERS;
import static com.example.teamc.api.generators.IdGenerator.generateLongId;
import static com.example.teamc.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {

    @Test(description = "User should be able to create a project", groups = {"Positive", "CRUD"})
    public void userCreatesProjectTest() {
        // Создаем пользователя
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        //Отправляем запрос на создание проекта, используя данные из testData
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name is not correct");
        softy.assertEquals(testData.getProject().getId(), createdProject.getId(), "Project ID is not correct");
    }

    @Test(description = "User should not be able to create a project with the same ID", groups = {"Negative", "CRUD"})
    public void userCreatesTwoProjectsWithSameIdTest() {
        // Генерируем второй проект с тем же ID, что и у тестового проекта
        var projectWithSameId = generate(Arrays.asList(testData.getProject()), Project.class, testData.getProject().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        // Отправляем запрос на создание первого проекта
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Отправляем запрос на создание второго проекта с тем же ID
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"%s\" is already used by another project".formatted(testData.getProject().getId())));
    }

    @Test(description = "User should not be able to create a project with the same name", groups = {"Negative", "CRUD"})
    public void userCreatesTwoProjectsWithSameNameTest() {
        // Генерируем новый уникальный ID для второго проекта
        String newProjectId = TestDataGenerator.Utils.generateUniqueId();
        // Создаем проект с тем же именем, что и тестовый проект, но с новым уникальным ID
        var projectWithSameName = generate(
                Arrays.asList(testData.getProject()),
                Project.class,
                newProjectId,
                testData.getProject().getName()
        );

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Отправляем запрос на создание первого проекта
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Отправляем запрос на создание второго проекта с тем же именем
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameName)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: %s".formatted(testData.getProject().getName())));
    }

    @Test(description = "User should not be able to create a project with empty fields", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyFieldsTest() {
        // Создаем проект с пустыми полями
        Project emptyProject = new Project("", "", null);

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Отправляем запрос на создание проекта
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(emptyProject)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));
    }

    @Test(description = "User should not be able to create a project with empty ID", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyIdTest() {
        // Создаем проект с заполненным name и пустым id
        Project projectWithEmptyId = new Project("", testData.getProject().getName(), null); // предполагаем, что id должен быть уникальным

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Отправляем запрос на создание проекта
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithEmptyId)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty")); // замените текст на соответствующее сообщение об ошибке
    }

    @Test(description = "User should not be able to create a project with invalid ID", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithInvalidIdTest() {
        // Генерируем проект с недопустимым ID
        String invalidId = IdGenerator.generateInvalidId(); // Используем метод генерации ID
        Project projectWithInvalidId = new Project(invalidId, testData.getProject().getName(), null); // name заполнено

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Отправляем запрос на создание проекта
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithInvalidId)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"" + invalidId + "\" is invalid")); // ожидаем сообщение об ошибке
    }

    @Test(description = "User should not be able to create a project with too long ID", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithTooLongIdTest() {
        // Генерируем ID длиной больше 225 символов
        String longId = generateLongId(226); // Метод генерации ID длиной 226 символов
        Project projectWithLongId = new Project(longId, testData.getProject().getName(), null); // name заполнено

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Отправляем запрос на создание проекта
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithLongId)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR) // Ожидаем статус 500
                .body(Matchers.containsString("Project ID \"" + longId + "\" is invalid")); // Проверяем сообщение об ошибке
    }
}
