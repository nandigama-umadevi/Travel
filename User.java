package io.nuggets.chicken_nugget.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.nuggets.chicken_nugget.utils.Validation;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    private String userName;
    private String fullName;
    private String password;
    private String bio;
    private String email;
    private String profilePic;

    private String authToken;

    public User(String userName, String password, String fullName) {
        Validation.checkEmpty(userName, "UserName");
        Validation.checkEmpty(password, "Password");
        Validation.checkEmpty(fullName, "FullName");
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        Validation.checkEmpty(userName, "UserName");
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        Validation.checkEmpty(fullName, "FullName");
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        Validation.checkEmpty(password, "Password");
        this.password = password;
    }

    public void setAuthToken(String authToken) {
        Validation.checkEmpty(authToken, "AuthToken");
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void logout() {
        this.authToken = null;
    }

    public boolean validatePassword(String password) {
        Validation.checkEmpty(password, "Password");
        return this.password.equals(password);
    }

    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setProfilePic(String proilePic) {
        this.profilePic = proilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

}
