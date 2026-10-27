package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class UserProfile {

    @Id
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role; // например, "USER" или "ADMIN"

    private Boolean active = true;

    private String avatarUrl;

    private Integer subscriptionsAmount;

    private String firstName;

    private String lastName;

    @ElementCollection
    private List<String> stack;

    private String city;

    private String description;

    public UserProfile() {
    }

    public UserProfile(Long id, String username, String password, String role, Boolean active, String avatarUrl, Integer subscriptionsAmount, String firstName, String lastName, List<String> stack, String city, String description) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
        this.avatarUrl = avatarUrl;
        this.subscriptionsAmount = subscriptionsAmount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.stack = stack;
        this.city = city;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getSubscriptionsAmount() {
        return subscriptionsAmount;
    }

    public void setSubscriptionsAmount(Integer subscriptionsAmount) {
        this.subscriptionsAmount = subscriptionsAmount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(avatarUrl, that.avatarUrl) && Objects.equals(subscriptionsAmount, that.subscriptionsAmount) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(active, that.active) && Objects.equals(stack, that.stack) && Objects.equals(city, that.city) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, avatarUrl, subscriptionsAmount, firstName, lastName, active, stack, city, description);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserProfile.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("active=" + active)
                .add("lastName='" + lastName + "'")
                .add("firstName='" + firstName + "'")
                .toString();
    }
}
