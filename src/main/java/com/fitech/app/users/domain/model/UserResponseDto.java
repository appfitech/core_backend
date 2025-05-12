package com.fitech.app.users.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class UserResponseDto implements Serializable {
    private Integer id;
    private String username;
    private Integer type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isEmailVerified;
    private PersonDto person;
    private boolean isPremium = false;
    private PremiumBy premiumBy = PremiumBy.NONE;

    public UserResponseDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public PremiumBy getPremiumBy() {
        return premiumBy;
    }

    public void setPremiumBy(PremiumBy premiumBy) {
        this.premiumBy = premiumBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isEmailVerified=" + isEmailVerified +
                ", person=" + person +
                ", isPremium=" + isPremium +
                ", premiumBy=" + premiumBy +
                '}';
    }
} 