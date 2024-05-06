package io.nuggets.chicken_nugget.services;

import io.nuggets.chicken_nugget.entities.User;
import io.nuggets.chicken_nugget.entities.Comments;
import io.nuggets.chicken_nugget.entities.Likes;
import io.nuggets.chicken_nugget.entities.Post;
import io.nuggets.chicken_nugget.exceptions.CustomException;
import io.nuggets.chicken_nugget.dao.CommentsRepository;
import io.nuggets.chicken_nugget.dao.PostRepository;
import io.nuggets.chicken_nugget.dao.LikesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired PostRepository postRepository;

    @Autowired CommentsRepository commentsRepository;

    @Autowired LikesRepository likesRepository;

    // get post by id
    public Post getPostById(String id) {
        Optional<Post> postOptional = this.postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            return post;
        } else {
            throw new CustomException("Invalid postId");
        }
    }

    // creating new post
    public Post createPost(Post post) {
        Post result = postRepository.save(post);
        return result;
    }

    public List<Post> getPosts(User user) {
        List<Post> posts = postRepository.findByUserOrderByCreatedAtDesc(user);
        return posts;
    }

    public List<Post> getHomePosts(List<User> users) {
        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(users);
        return posts;
    }

    public Comments commentOnPost(String comment, Post post, User user) {
        Comments comments = new Comments(user, post, comment);
        commentsRepository.save(comments);
        return comments;
    }

    public void deleteComment(String commentId, User user) {
        Optional<Comments> commentOptional = commentsRepository.findById(commentId);
        if(commentOptional.isPresent()) {
            Comments comment = commentOptional.get();
            if(comment.getUser() == user) {
                commentsRepository.deleteById(commentId);
            } else {
                throw new CustomException("Permission Denied");
            }
        } else {
            throw new CustomException("Invalid CommentId");
        }
    }

    public Likes likePost(Post post, User user) {
        Likes likes = new Likes(user, post);
        likesRepository.save(likes);
        return likes;
    }

    public void unlikePost(Post post, User user) {
        Optional<Likes> likeOptional = likesRepository.findByUserAndPost(user, post);
        if(likeOptional.isPresent()) {
            likesRepository.deleteByUserAndPost(user, post);
        } else {
            throw new CustomException("Post not found");
        }
    }

}
