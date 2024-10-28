package com.example.teamc.api.models;

import com.example.teamc.api.annotations.Parameterizable;
import com.example.teamc.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseModel {
    @Random
    @Parameterizable
    private String id;
    @Random
    @Parameterizable
    private String name;
    private String locator;
}