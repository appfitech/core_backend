package com.fitech.app.users.domain.model;

import com.fitech.app.users.domain.entities.User;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for {@link User}
 */
public class UserDto implements Serializable {
    private Integer id;
    @Size(max = 45)
    private String username;
    private Integer type;
    @Size(max = 45)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isEmailVerified;
    private String emailVerificationToken;
    private LocalDateTime emailTokenExpiresAt;
    private PersonDto person;
    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isEmailVerified=" + isEmailVerified +
                ", emailVerificationToken='" + emailVerificationToken + '\'' +
                ", emailTokenExpiresAt=" + emailTokenExpiresAt +
                ", person=" + person +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public LocalDateTime getEmailTokenExpiresAt() {
        return emailTokenExpiresAt;
    }

    public void setEmailTokenExpiresAt(LocalDateTime emailTokenExpiresAt) {
        this.emailTokenExpiresAt = emailTokenExpiresAt;
    }

    public boolean hasDifferentUserName(String username){
        return this.getUsername()!= null && !this.getUsername().equals(username);
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}