package com.example.teamc.api;

import org.testng.annotations.Test;
import org.apache.http.HttpStatus;
import io.restassured.RestAssured;
import com.example.teamc.api.models.User;
import com.example.teamc.api.spec.Specifications;

public class BuildConfigurationTest extends BaseApiTest{
    @Test
    public void buildConfigurationTest(){
        var user = User.builder()
                .username("admin")
                .password("admin")
                .build();

        var token = RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(user))
                .get("/authenticationTest.html?crsf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();

        System.out.println(token);
    }
}


