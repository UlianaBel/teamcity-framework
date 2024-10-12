package com.example.teamc.api.models;

import com.example.teamc.api.annotations.Optional;
import com.example.teamc.api.annotations.Parameterizable;
import com.example.teamc.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovy.cli.Option;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel {
    private String id;
    @Random
    private String name;
    @Parameterizable
    private Project project;
    @Optional
    private Steps steps;
}
