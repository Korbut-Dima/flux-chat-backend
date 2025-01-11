package com.fluxchat.fluxchat.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fluxchat.fluxchat.enteties.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    @NotBlank(message = "Номер телефону обов'язковий", groups = CreateValidationGroup.class)
    private String phone;
    @JsonProperty
    @NotBlank(message = "Поштова скринька обов'язкова", groups = CreateValidationGroup.class)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty
    @Enumerated(EnumType.STRING)
    private Role role;
}
