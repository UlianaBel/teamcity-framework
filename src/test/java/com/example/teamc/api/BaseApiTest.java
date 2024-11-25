package com.example.teamc.api;

import com.example.teamc.BaseTest;
import com.example.teamc.api.models.AuthModules;
import com.example.teamc.api.models.ServerAuthSettings;
import com.example.teamc.api.requests.ServerAuthRequest;
import com.example.teamc.api.spec.Specifications;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static com.example.teamc.api.generators.TestDataGenerator.generate;

public class BaseApiTest extends BaseTest {
    private final ServerAuthRequest serverAuthRequest = new ServerAuthRequest(Specifications.superUserSpec());
    private AuthModules authModules;
    private boolean perProjectPermissions;

    @BeforeSuite(alwaysRun = true)
    public void setUpServerAuthSettings() {
        // Получаем текущие настройки PerProjectPermissions
        perProjectPermissions = serverAuthRequest.read().getPerProjectPermissions();

        authModules = generate(AuthModules.class);
        // Обновляем значение PerProjectPermissions на true
        serverAuthRequest.update(ServerAuthSettings.builder()
                        .perProjectPermissions(true)
                        .modules(authModules)
                .build());
    }

    @AfterSuite
    public void cleanUpServerAuthSettings() {
        //Возвращаем настройки perProjectPermissions в исходное значение
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(perProjectPermissions)
                .modules(authModules)
                .build());
    }
}
