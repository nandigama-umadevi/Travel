package io.nuggets.chicken_nugget.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import io.nuggets.chicken_nugget.entities.Following;
import io.nuggets.chicken_nugget.entities.User;


public interface FollowingRepository extends JpaRepository<Following, String> {

    @Override
    @NonNull
    public Optional<Following> findById(@NonNull String id);

    public Optional<Following> findByUserAndFollower(User user, User follower);

    public List<Following> findByUser(User user);

    @Query("SELECT f.user FROM Following f WHERE f.follower = :follower")
    public List<User> findFollowedUsersByFollower(@Param("follower") User follower);

    public List<User> findUsersByFollower(User follower);

    public List<User> findFollowersByUser(User user);

    public void deleteByUserAndFollower(User user, User follower);
}


// public Page<Post> getPostsForHomePage(User user, int page, int size) {
//     List<User> followedUsers = followingRepository.findFollowedUsersByUser(user);
//     Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//     return postRepository.findByUserInOrderByCreatedAtDesc(followedUsers, pageable);
// }


// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import java.util.List;

// @Repository
// public interface PostRepository extends JpaRepository<Post, Long> {

//     Page<Post> findByUserInOrderByCreatedAtDesc(List<User> users, Pageable pageable);
// }