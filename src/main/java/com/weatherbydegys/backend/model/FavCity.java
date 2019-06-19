package com.weatherbydegys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fav_city")
public class FavCity {
    @Id
    @NotNull(message = "ID of city cannot be empty")
    @Column(name = "ID", nullable = false)
    private Long id;
    @NotNull
    @NotBlank(message = "NAME of city cannot be empty")
    @Column(name = "NAME")
    private String name;
    @NotNull
    @NotBlank(message = "COUNTRY of city cannot be empty")
    @Column(name = "COUNTRY")
    private String country;
    @ManyToMany(mappedBy = "favCities", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    public FavCity() {}

    public FavCity(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavCity favCity = (FavCity) o;
        return id == favCity.id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.valueOf(id.toString());
        return result;
    }

    @Override
    public String toString() {
        return "FavCity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' + '}';
    }
}
