package com.weatherbydegys.backend.dto;

import com.weatherbydegys.backend.model.Role;

import java.time.LocalDateTime;
import java.util.Set;

public class UserDTO {
    private long id;
    private String uid;
    private String name;
    private String email;
    private String photoURL;
    private LocalDateTime lastVisit;
    private Set<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public LocalDateTime getLastVisit() {return lastVisit; }

    public void setLastVisit(LocalDateTime lastVisit) {this.lastVisit = lastVisit; }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
