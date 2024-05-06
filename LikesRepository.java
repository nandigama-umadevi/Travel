package io.nuggets.chicken_nugget.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import io.nuggets.chicken_nugget.entities.Likes;
import io.nuggets.chicken_nugget.entities.Post;
import io.nuggets.chicken_nugget.entities.User;

public interface LikesRepository extends JpaRepository<Likes, String> {

    @Override
    @NonNull
    public Optional<Likes> findById(@NonNull String id);

    public List<Likes> findByPost(Post post);

    public Optional<Likes> findByUserAndPost(User user, Post post);

    public void deleteByUserAndPost(User user, Post post);

}
