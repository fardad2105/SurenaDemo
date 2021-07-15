package com.fy.surena.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserInfoUpdateDto {

    @NotNull
    @NotBlank(message = "firstname cannot be empty")
    @JsonProperty("firstname")
    private String firstname;

    @NotNull
    @NotBlank(message = "lastname cannot be empty")
    @JsonProperty("lastname")
    private String lastname;
}
