import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsControllerTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        UserDetails userDetails = new UserDetails("John", "Doe", "johndoe", "123456", "john@example.com", 70, 180);
        when(userDetailsService.signup(any(UserDetails.class))).thenReturn(userDetails);

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.createUser("John", "Doe", "johndoe", "123456", "john@example.com", 70, 180);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map<?, ?>);
        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertTrue(responseBody.containsKey("user"));
        assertEquals(userDetails, responseBody.get("user"));
    }

    @Test
    public void testCreateUser_Failure_InvalidDetails() {
        // Arrange
        when(userDetailsService.signup(any(UserDetails.class))).thenThrow(new IllegalArgumentException("Invalid user details"));

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.createUser("John", "Doe", null, "123456", "john@example.com", 70, 180);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testGetUserByName_Success() {
        // Arrange
        String userName = "johndoe";
        UserDetails userDetails = new UserDetails("John", "Doe", "johndoe", "123456", "john@example.com", 70, 180);
        when(userDetailsService.getUserByName(userName)).thenReturn(userDetails);

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.getUserByName(userName);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof UserDetails);
        assertEquals(userDetails, responseEntity.getBody());
    }

    @Test
    public void testGetUserByName_Failure_UserNotFound() {
        // Arrange
        String userName = "nonexistentuser";
        when(userDetailsService.getUserByName(userName)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.getUserByName(userName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    // Add more test methods as needed

}

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsControllerTest {

    @InjectMocks
    private UserDetailsController userDetailsController;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin_Success() {
        // Arrange
        String authToken = "randomAuthToken";
        UserDetails userDetails = new UserDetails("John", "Doe", "johndoe", "123456", "john@example.com", 70, 180);
        when(userDetailsService.login("johndoe", "123456")).thenReturn(authToken);
        when(userDetailsService.getUserByName("johndoe")).thenReturn(userDetails);

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.login("johndoe", "123456");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map<?, ?>);
        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertTrue(responseBody.containsKey("authToken"));
        assertEquals(authToken, responseBody.get("authToken"));
        assertTrue(responseBody.containsKey("user"));
        assertEquals(userDetails, responseBody.get("user"));
    }

    @Test
    public void testLogin_Failure_InvalidCredentials() {
        // Arrange
        when(userDetailsService.login("johndoe", "wrongpassword")).thenThrow(new IllegalArgumentException("Invalid credentials"));

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.login("johndoe", "wrongpassword");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testLogout_Success() {
        // Arrange
        String userName = "johndoe";

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.logout(userName);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map<?, ?>);
        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertEquals("ok", responseBody.get("status"));
    }

    @Test
    public void testLogout_Failure_InvalidUserName() {
        // Arrange
        String userName = "nonexistentuser";

        // Act
        ResponseEntity<?> responseEntity = userDetailsController.logout(userName);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    // Add more test methods as needed

}




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private UserDetailsDTO userDetailsDTO;

    @BeforeEach
    void setUp() {
        userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUsername("testUser");
        userDetailsDTO.setPassword("password123");
        // Set other user details as needed
    }

    @Test
    void testSignup_Success() {
        UserDetails userEntity = mapToEntity(userDetailsDTO);
        when(userDetailsRepository.save(any())).thenReturn(userEntity);

        UserDetailsDTO savedUser = userDetailsService.signup(userDetailsDTO);

        assertNotNull(savedUser);
        assertEquals(userDetailsDTO.getUsername(), savedUser.getUsername());
        // Add more assertions for other fields
    }

    @Test
    void testSignup_Failure() {
        when(userDetailsRepository.save(any())).thenReturn(null);

        UserDetailsDTO savedUser = userDetailsService.signup(userDetailsDTO);

        assertNull(savedUser);
        // Add more assertions as needed for failure scenarios
    }

    @Test
    void testLogin_Success() {
        UserDetails userEntity = mapToEntity(userDetailsDTO);
        when(userDetailsRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.of(userEntity));

        UserDetailsDTO loggedInUser = userDetailsService.login("testUser", "password123");

        assertNotNull(loggedInUser);
        assertEquals(userDetailsDTO.getUsername(), loggedInUser.getUsername());
        // Add more assertions for other fields
    }

    @Test
    void testLogin_Failure() {
        when(userDetailsRepository.findByUsernameAndPassword(any(), any())).thenReturn(Optional.empty());

        UserDetailsDTO loggedInUser = userDetailsService.login("testUser", "wrongPassword");

        assertNull(loggedInUser);
        // Add more assertions as needed for failure scenarios
    }

    @Test
    void testLogout_Success() {
        // Mocking void method
        doNothing().when(userDetailsRepository).deleteByUsername(any());

        // Call the logout method
        userDetailsService.logout("testUser");

        // Since it's void method, just verify that it was called with the correct username
        verify(userDetailsRepository, times(1)).deleteByUsername("testUser");
    }

    private UserDetails mapToEntity(UserDetailsDTO userDetailsDTO) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(userDetailsDTO.getUsername());
        userDetails.setPassword(userDetailsDTO.getPassword());
        // map other fields as needed
        return userDetails;
    }
}



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignup() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("testUser");
        userDetails.setPassword("testPassword");

        when(userDetailsRepository.findUserByUsername(any(String.class))).thenReturn(null);
        when(userDetailsRepository.save(any(UserDetails.class))).thenReturn(userDetails);

        UserDetails savedUser = userService.signup(userDetails);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("testUser", savedUser.getUserName());
    }

    @Test
    public void testLogin() {
        String userName = "testUser";
        String password = "testPassword";

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(userName);
        userDetails.setPassword(password);

        when(userDetailsRepository.findUserByUsername(userName)).thenReturn(Optional.of(userDetails));

        String authToken = userService.login(userName, password);

        Assertions.assertNotNull(authToken);
    }

    @Test
    public void testLogout() {
        String userName = "testUser";

        when(userDetailsRepository.findUserByUsername(userName)).thenReturn(Optional.of(new UserDetails()));

        userService.logout(userName);

        // Add assertions if required
    }
}
@Test
public void testLogout() {
    String userName = "testUser";

    UserDetails user = new UserDetails();
    user.setUserName(userName);

    when(userDetailsRepository.findUserByUsername(userName)).thenReturn(Optional.of(user));

    userService.logout(userName);

    // Assert that user's authToken is null after logout
    Assertions.assertNull(user.getAuthToken(), "User's authToken should be null after logout");
}

