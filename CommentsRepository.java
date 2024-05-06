package io.nuggets.chicken_nugget.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import io.nuggets.chicken_nugget.entities.Comments;
import io.nuggets.chicken_nugget.entities.Post;
import io.nuggets.chicken_nugget.entities.User;

public interface CommentsRepository extends JpaRepository<Comments, String> {

    @Override
    @NonNull
    public Optional<Comments> findById(@NonNull String id);

    public List<Comments> findByPostOrderByCreatedAtDesc(Post post);

    public void deleteByUserAndPost(User user, Post post);

}
