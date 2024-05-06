package io.nuggets.chicken_nugget.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuggets.chicken_nugget.exceptions.CustomException;
import io.nuggets.chicken_nugget.services.UserService;
import io.nuggets.chicken_nugget.entities.User;

@RestController
public class authController {

    @Autowired
    private UserService userService;

    @GetMapping("/signUp")
    public ResponseEntity<?> Register(@RequestParam String username, @RequestParam String password,
            @RequestParam String fullName) {
        try {
            User user = new User(username, password, fullName);
            User responseUser = userService.createUser(user);
            Map<String, User> response = new HashMap<>();
            response.put("user", responseUser);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> Login(@RequestParam String userName, @RequestParam String password) {
        try {
            String authToken = userService.login(userName, password);
            User user = userService.getUserByUserName(userName);
            Map<String, Object> response = new HashMap<>();
            response.put("authToken", authToken);
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> Logout(@RequestParam String authToken) {
        try {
            userService.logout(authToken);
            Map<String, String> response = new HashMap<>();
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }
    
}
