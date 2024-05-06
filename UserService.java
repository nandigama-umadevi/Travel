package io.nuggets.chicken_nugget.services;

import io.nuggets.chicken_nugget.dao.UserRepository;
import io.nuggets.chicken_nugget.dao.FollowingRepository;
import io.nuggets.chicken_nugget.entities.Following;
import io.nuggets.chicken_nugget.entities.User;
import io.nuggets.chicken_nugget.exceptions.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowingRepository followingRepository;

    // get user by id
    public User getUserById(String id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            throw new CustomException("Invalid userId");
        }
    }

    // creating new user
    public User createUser(User user) {
        User result = userRepository.save(user);
        return result;
    }

    public void updateFullName(User user, String fullName) {
        user.setFullName(fullName);
        userRepository.save(user);
    }

    public void updateBio(User user, String bio) {
        user.setBio(bio);
        userRepository.save(user);
    }

    public void updateEmail(User user, String email) {
        user.setEmail(email);
        userRepository.save(user);
    }

    public void updateProfilePic(User user, String profilePic) {
        user.setProfilePic(profilePic);
        userRepository.save(user);
    }

    public void updateUserName(User user, String userName) {
        Optional<User> userOptional = this.userRepository.findByUserName(userName);
        if(userOptional.isPresent()) {
            throw new CustomException("UserName already exists");
        }
        user.setUserName(userName);
        userRepository.save(user);
    }

    public void updatePassword(User user, String currPassword, String newPassword) {
        if(user.validatePassword(currPassword)){
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new CustomException("Invalid Password");
        }
    }

    public User getUserByUserName(String userName) {
        Optional<User> userOptional = this.userRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            throw new CustomException("Invalid userName");
        }
    }

    public List<User> searchUserByUserName(String userName) {
        List<User> users = userRepository.findByUserNameContainingIgnoreCase(userName);
        return users;
    }

    public User getUserByAuthToken(String authToken) {
        Optional<User> userOptional = this.userRepository.findByAuthToken(authToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user;
        } else {
            throw new CustomException("Invalid authToken");
        }
    }

    public String login(String userName, String password) {
        Optional<User> userOptional = this.userRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.validatePassword(password)) {
                String authToken = UUID.randomUUID().toString();
                user.setAuthToken(authToken);
                this.userRepository.save(user);
                return authToken;
            } else {
                throw new CustomException("Invalid password");
            }
        } else {
            throw new CustomException("Invalid username");
        }
    }

    public void logout(String authToken) {
        Optional<User> userOptional = this.userRepository.findByAuthToken(authToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.logout();
            this.userRepository.save(user);
        }
    }

    public void followUser(User user, User follower) {
        Optional<Following> followOptional = followingRepository.findByUserAndFollower(user, follower);
        if(!followOptional.isPresent()) {
            Following following = new Following(user, follower);
            followingRepository.save(following);
        } else {
            throw new CustomException("Already Following the user");
        }
    }

    public void unFollowUser(User user, User follower) {
        Optional<Following> followOptional = followingRepository.findByUserAndFollower(user, follower);
        if(followOptional.isPresent()) {
            followingRepository.deleteByUserAndFollower(user, follower);
        } else {
            throw new CustomException("You are not following the user");
        }
    }
}
