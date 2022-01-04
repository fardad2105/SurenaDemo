package com.fy.surena.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fy.surena.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {


    @JsonProperty("id")
    private Long id;

    @NotNull
    @NotBlank(message = "username cannot be empty")
    @JsonProperty("username")
    private String username;

    @NotNull
    @NotBlank(message = "password cannot be empty")
    @Length(min = 8, message = "Password must be at least 6 characters")
    @JsonProperty("password")
    private String password;

    @NotNull
    @NotBlank(message = "firstname cannot be empty")
    @JsonProperty("firstname")
    private String firstname;

    @NotNull
    @NotBlank(message = "lastname cannot be empty")
    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("create_date")
    private String CreateDate;

    @JsonProperty("modified_date")
    private String  ModifiedDate;

}
