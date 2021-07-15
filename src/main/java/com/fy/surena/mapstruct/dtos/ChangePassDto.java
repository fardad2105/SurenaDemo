package com.fy.surena.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangePassDto {

    @NotNull
    @NotBlank(message = "new password cannot be empty")
    @JsonProperty("newPassword")
    private String newPass;

    @NotNull
    @NotBlank(message = "old password cannot be empty")
    @JsonProperty("oldPassword")
    private String oldPass;
}
