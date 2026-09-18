package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.requests.authReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.responses.authReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.RegisterReaderResponse;
import ng.martG.eLibrary.services.auth.AuthReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthReaderServiceTest {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private AuthReaderService service;

    private  RegisterReaderRequest register;

    @BeforeEach
    public void setUp() {
        readerRepository.deleteAll();
        register = new RegisterReaderRequest();
        register.setFullName("Martins Emmanuel");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail.com");

    }

    @Test
    public void testThatReaderRegisters_AndReaderIsRegistered() {
        RegisterReaderResponse bob = service.registerReader(register);

        assertEquals(1 , readerRepository.count());
        assertEquals("Martins Emmanuel" , bob.getFullName());

    }

    @Test
    public void testThatAReaderRegistersWithADuplicateUsername_AnErrorIsThrown() {
        service.registerReader(register);

        register.setFullName("Gold Emmanuel");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("gbemi09@gmail.com");

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(1 , readerRepository.count());

    }

    @Test
    public void testThatAReaderRegistersWithATwoDifferentUsername_ReaderIsRegistered() {
        service.registerReader(register);

        register.setFullName("Gold Emmanuel");
        register.setUsername("levit99");
        register.setPassword("correct");
        register.setEmail("gbemi09@gmail.com");

        service.registerReader(register);

        assertEquals(2 , readerRepository.count());

    }

    @Test
    public void testThatAReaderRegistersWithADuplicateEmail_AnErrorIsThrown() {
        service.registerReader(register);

        register.setFullName("Diamond John");
        register.setUsername("save_09");
        register.setPassword("incorrect");
        register.setEmail("ajulogbemi09@gmail.com");

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(1 , readerRepository.count());

    }

    @Test
    public void testThatReaderRegistersWithEmailNotEndingWithAtGmailDotcom_AndReaderIsNotRegistered() {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Heaven kay");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@");

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(0 , readerRepository.count());

    }

    @Test
    public void testThatReaderLoginWithEmailAndPassword_ReaderIsLoggedin() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("ajulogbemi09@gmail.com");
        login.setPassword("correct");

        LoginReaderResponse response = service.loginReader(login);
        assertTrue(response.isLoggedIn());

    }

    @Test
    public void testThatReaderLoginWithUsernameAndPassword_ReaderIsLoggedin() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct");

        LoginReaderResponse response = service.loginReader(login);
        assertTrue(response.isLoggedIn());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public  void testThatAReaderRegistersInputingBlankValueInFullname_ReaderIsNotRegister(String fullname) {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName(fullname);
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail.com");

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(0 , readerRepository.count());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public  void testThatAReaderRegistersInputingBlankValueInUsername_ReaderIsNotRegister(String username) {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Heaven kay");
        register.setUsername(username);
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail");

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(0 , readerRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public  void testThatAReaderRegistersInputingBlankValueInPassword_ReaderIsNotRegister(String password) {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Heaven kay");
        register.setUsername("maht_j");
        register.setPassword(password);
        register.setEmail("ajulogbemi09@gmail");

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(0 , readerRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public  void testThatAReaderRegistersInputingBlankValueInEmail_ReaderIsNotRegister(String email) {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Heaven kay");
        register.setUsername("maht_j");
        register.setPassword("correct");
        register.setEmail(email);

        assertThrows(IllegalArgumentException.class , () -> service.registerReader(register));

        assertEquals(0 , readerRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatReaderLoginWithBlankEmailOrUsernameAndPassword_ReaderIsNotLoggedin(String usernameOrEmail) {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail(usernameOrEmail);
        login.setPassword("correct");

        assertThrows(IllegalArgumentException.class ,()-> service.loginReader(login));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatReaderLoginWithUsernameAndBlankPassword_ReaderIsLoggedin(String password) {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword(password);

        assertThrows(IllegalArgumentException.class ,()-> service.loginReader(login));

    }

    @Test
    public void testThatReaderLoginWithWrongUsernameAndPassword_ReaderIsNotLoggedin() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg216");
        login.setPassword("correct");

        assertThrows(IllegalArgumentException.class, ()-> service.loginReader(login));

    }

    @Test
    public void testThatReaderLoginWithUsernameAndWrongPassword_ReaderIsNotLoggedin() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct098");

        assertThrows(IllegalArgumentException.class, ()-> service.loginReader(login));

    }

    @Test
    public void testThatReaderLogin_ReaderIsLoggedIn_ReaderLogoutWithUsername_ReaderIsLoggedOut() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct");

        LoginReaderResponse response = service.loginReader(login);
        assertTrue(response.isLoggedIn());

        LogoutReaderRequest logout = new LogoutReaderRequest();
        logout.setUsernameOrEmail("martg_09");

        LogoutReaderResponse logResponse = service.logoutReader(logout);

        assertFalse(logResponse.isLoggedIn());

    }

    @Test
    public void testThatReaderLogin_ReaderIsLoggedIn_ReaderLogoutWithEmail_ReaderIsLoggedOut() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct");

        LoginReaderResponse response = service.loginReader(login);
        assertTrue(response.isLoggedIn());

        LogoutReaderRequest logout = new LogoutReaderRequest();
        logout.setUsernameOrEmail("ajulogbemi09@gmail.com");

        LogoutReaderResponse logResponse = service.logoutReader(logout);

        assertFalse(logResponse.isLoggedIn());

    }

    @Test
    public void testThatReaderLogin_ReaderIsLoggedIn_ReaderLogoutWithWrongEmailOrUsername_ReaderIsLoggedOut() {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct");

        LoginReaderResponse response = service.loginReader(login);
        assertTrue(response.isLoggedIn());

        LogoutReaderRequest logout = new LogoutReaderRequest();
        logout.setUsernameOrEmail("ajulogbemi09@gmail");

        assertThrows(IllegalArgumentException.class , ()-> service.logoutReader(logout));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatReaderLogin_ReaderIsLoggedIn_ReaderLogoutWithBlankEmailOrUsername_ReaderIsLoggedOut(String usernameOrEmail) {
        service.registerReader(register);

        LoginReaderRequest login = new LoginReaderRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct");

        LoginReaderResponse response = service.loginReader(login);
        assertTrue(response.isLoggedIn());

        LogoutReaderRequest logout = new LogoutReaderRequest();
        logout.setUsernameOrEmail(usernameOrEmail);

        assertThrows(IllegalArgumentException.class , ()-> service.logoutReader(logout));

    }

}
