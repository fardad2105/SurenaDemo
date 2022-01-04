package com.fy.surena.mapstruct.dtos.response;

import com.fy.surena.mapstruct.dtos.request.RoleRequestDto;

public class RoleResponseDto extends RoleRequestDto {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
