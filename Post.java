package io.nuggets.chicken_nugget.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String postText;
    private String imageLink;

    private LocalDateTime createdAt;

    public Post(User user, String postText, String imageLink) {
        if((postText == null || postText.isEmpty()) && (imageLink == null || imageLink.isEmpty())) {
            throw new IllegalArgumentException("Post cannot be empty");
        }
        this.user = user;
        this.postText = postText;
        this.imageLink = imageLink;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public String getPostText() {
        return postText;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

}
