package estu.ceng.edu;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainTest {

    private Signup signup;
    private Login login;

    @Before
    public void setUp() {
        signup = new Signup();
        login = new Login();

        // Reset static variables before each test
        AuthService.storedUsername = null;
        AuthService.storedEmail = null;
        AuthService.storedPassword = null;
    }

    @Test
    public void testPrintsOnSuccessfulLogin() throws Exception {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        signup.register("user", "user@example.com", "password123");
        login.login("user@example.com", "password123");

        System.setOut(originalOut); // restore stdout

        String result = output.toString();
        assertTrue(result.contains("Signup successful."));
        assertTrue(result.contains("Login successful."));
    }

    @Test(expected = Exception.class)
    public void testSignupWithShortPassword() throws Exception {
        signup.register("myusername", "examplemail@example.com", "123");
    }

    @Test(expected = Exception.class)
    public void testSignupWithExistingEmail() throws Exception {
        signup.register("myusername", "examplemail@example.com", "secret123");
        signup.register("anotherUser", "examplemail@example.com", "password123");
    }

    @Test(expected = Exception.class)
    public void testSignupWithExistingUsername() throws Exception {
        signup.register("myusername", "examplemail@example.com", "secret123");
        signup.register("myusername", "newexamplemail@example.com", "password123");
    }

    @Test(expected = Exception.class)
    public void testLoginWithUnregisteredEmail() throws Exception {
        login.login("notregistered@example.com", "password");
    }

    @Test(expected = Exception.class)
    public void testLoginWithWrongPassword() throws Exception {
        signup.register("myusername", "examplemail@example.com", "secret123");
        login.login("examplemail@example.com", "wrongpassword");
    }
}
