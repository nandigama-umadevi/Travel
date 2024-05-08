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
