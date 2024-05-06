package io.nuggets.chicken_nugget.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import io.nuggets.chicken_nugget.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

    @Override
    @NonNull
    public Optional<User> findById(@NonNull String id);

    public Optional<User> findByUserName(String userName);

    public Optional<User> findByAuthToken(String authToken);

    public List<User> findByUserNameContainingIgnoreCase(String username);
}
