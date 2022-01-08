package com.fy.surena.mapstruct.dtos.request;

import com.fy.surena.model.Permission;

import java.time.LocalDate;
import java.util.Set;

public class RoleRequestDto {

    private int id;
    private String title;
    private String description;
    private String content;
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
