package com.example.teamc.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerAuthSettings extends BaseModel {
  Boolean perProjectPermissions;
  AuthModules modules;
}
