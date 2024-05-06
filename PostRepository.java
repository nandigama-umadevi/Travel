package io.nuggets.chicken_nugget.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import io.nuggets.chicken_nugget.entities.Post;
import io.nuggets.chicken_nugget.entities.User;

public interface PostRepository extends JpaRepository<Post, String> {
    
    @Override
    @NonNull
    public Optional<Post> findById(@NonNull String id);

    public List<Post> findByUserOrderByCreatedAtDesc(User user);

    public List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);

    public void deleteById(@NonNull String id);

}
