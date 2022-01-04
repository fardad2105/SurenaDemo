package com.fy.surena.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String content;
    private boolean active;
    private String createAtDate;
    private String updateAtDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
    joinColumns = {@JoinColumn(name = "role_id")},
    inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();

    public Role() {
    }

    public Role(int id, String title, String description, String content, boolean active, String createAtDate, String updateAtDate, Set<Permission> permissions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.active = active;
        this.createAtDate = createAtDate;
        this.updateAtDate = updateAtDate;
        this.permissions = permissions;
    }

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

    public String getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate(String createAtDate) {
        this.createAtDate = createAtDate;
    }

    public String getUpdateAtDate() {
        return updateAtDate;
    }

    public void setUpdateAtDate(String updateAtDate) {
        this.updateAtDate = updateAtDate;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
