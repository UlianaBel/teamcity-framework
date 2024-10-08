package com.example.teamc.api.enums;

import com.example.teamc.api.models.BaseModel;
import com.example.teamc.api.models.BuildType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;

}
