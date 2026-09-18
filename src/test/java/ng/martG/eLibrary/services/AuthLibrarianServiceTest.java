package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.repositories.LibrarianRepository;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;
import ng.martG.eLibrary.services.auth.AuthLibrarianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthLibrarianServiceTest {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private AuthLibrarianService service;

    private RegisterLibrarianRequest register;

    @BeforeEach
    public void setUp() {
        librarianRepository.deleteAll();
        register = new RegisterLibrarianRequest();
        register.setFullName("Librarian Manuel");
        register.setUsername("libra_09");
        register.setPassword("incorrect");
        register.setEmail("ajulogbemi09@gmail.com");

    }

    @Test
    public void testThatLibrarianRegisters_AndLibrarianIsRegistered() {
        RegisterLibrarianResponse bob = service.registerLibrarian(register);

        assertEquals(1 , librarianRepository.count());
        assertEquals("Librarian Manuel" , bob.getFullName());

    }

    @Test
    public void testThatALibrarianRegistersWithADuplicateUsername_AnErrorIsThrown() {
        service.registerLibrarian(register);

        register.setFullName("Gold Emmanuel");
        register.setUsername("libra_09");
        register.setPassword("correct");
        register.setEmail("gbemi09@gmail.com");

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(1 , librarianRepository.count());

    }

    @Test
    public void testThatALibrarianRegistersWithATwoDifferentUsername_LibrarianIsRegistered() {
        service.registerLibrarian(register);

        register.setFullName("Gold Emmanuel");
        register.setUsername("levit99");
        register.setPassword("correct");
        register.setEmail("gbemi09@gmail.com");

        service.registerLibrarian(register);

        assertEquals(2 , librarianRepository.count());

    }

    @Test
    public void testThatALibrarianRegistersWithADuplicateEmail_AnErrorIsThrown() {
        service.registerLibrarian(register);

        register.setFullName("Diamond John");
        register.setUsername("save_09");
        register.setPassword("incorrect");
        register.setEmail("ajulogbemi09@gmail.com");

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(1 , librarianRepository.count());

    }

    @Test
    public void testThatLibrarianRegistersWithEmailNotEndingWithAtGmailDotcom_AndLibrabrianIsNotRegistered() {
        librarianRepository.deleteAll();
        RegisterLibrarianRequest register = new RegisterLibrarianRequest();
        register.setFullName("Heaven kay");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@");

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(0 , librarianRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public  void testThatALibrarianRegistersInputingBlankValueInFullname_LibrarianIsNotRegister(String fullname) {
        librarianRepository.deleteAll();
        RegisterLibrarianRequest register = new RegisterLibrarianRequest();
        register.setFullName(fullname);
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail.com");

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(0 , librarianRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public  void testThatALibrarianRegistersInputingBlankValueInUsername_LibrarianIsNotRegister(String username) {
        librarianRepository.deleteAll();
        RegisterLibrarianRequest register = new RegisterLibrarianRequest();
        register.setFullName("Heaven kay");
        register.setUsername(username);
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail");

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(0 , librarianRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public  void testThatALibrarianRegistersInputingBlankValueInPassword_LibrarianIsNotRegister(String password) {
        librarianRepository.deleteAll();
        RegisterLibrarianRequest register = new RegisterLibrarianRequest();
        register.setFullName("Heaven kay");
        register.setUsername("maht_j");
        register.setPassword(password);
        register.setEmail("ajulogbemi09@gmail");

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(0 , librarianRepository.count());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public  void testThatALibrarianRegistersInputingBlankValueInEmail_LibrarianIsNotRegister(String email) {
        librarianRepository.deleteAll();
        RegisterLibrarianRequest register = new RegisterLibrarianRequest();
        register.setFullName("Heaven kay");
        register.setUsername("maht_j");
        register.setPassword("correct");
        register.setEmail(email);

        assertThrows(IllegalArgumentException.class , () -> service.registerLibrarian(register));

        assertEquals(0 , librarianRepository.count());

    }

    @Test
    public void testThatLibrarianLoginWithUsernameAndPassword_LibrarianIsLoggedin() {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("libra_09");
        login.setPassword("incorrect");

        LoginLibrarianResponse response = service.loginLibrarian(login);
        assertTrue(response.isLoggedIn());

    }

    @Test
    public void testThatLibrarianLoginWithEmailAndPassword_LibrarianIsLoggedin() {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("ajulogbemi09@gmail.com");
        login.setPassword("incorrect");

        LoginLibrarianResponse response = service.loginLibrarian(login);
        assertTrue(response.isLoggedIn());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public void testThatLibrarianLoginWithBlankEmailOrUsernameAndPassword_LibrarianIsNotLoggedin(String usernameOrEmail) {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail(usernameOrEmail);
        login.setPassword("correct");

        assertThrows(IllegalArgumentException.class ,()-> service.loginLibrarian(login));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public void testThatLibrarianLoginWithUsernameAndBlankPassword_LibrarianIsLoggedin(String password) {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword(password);

        assertThrows(IllegalArgumentException.class ,()-> service.loginLibrarian(login));

    }

    @Test
    public void testThatLibrarianLoginWithWrongUsernameAndPassword_LibrarianIsNotLoggedin() {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("martg216");
        login.setPassword("correct");

        assertThrows(IllegalArgumentException.class, ()-> service.loginLibrarian(login));

    }

    @Test
    public void testThatLibrarianLoginWithUsernameAndWrongPassword_LibrarianIsNotLoggedin() {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("martg_09");
        login.setPassword("correct098");

        assertThrows(IllegalArgumentException.class, ()-> service.loginLibrarian(login));

    }

    @Test
    public void testThatLogin_ReaderIsLoggedIn_ReaderLogoutWithUsername_ReaderIsLoggedOut() {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("libra_09");
        login.setPassword("incorrect");

        LoginLibrarianResponse response = service.loginLibrarian(login);
        assertTrue(response.isLoggedIn());

        LogoutLibrarianRequest logout = new LogoutLibrarianRequest();
        logout.setUsernameOrEmail("libra_09");

        LogoutLibrarianResponse logResponse = service.logoutLibrarian(logout);

        assertFalse(logResponse.isLoggedIn());

    }

    @Test
    public void testThatLibrarianLogin_LibrariainIsLoggedIn_LibrarianLogoutWithEmail_LibrarianIsLoggedOut() {
      service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("libra_09");
        login.setPassword("incorrect");

        LoginLibrarianResponse response = service.loginLibrarian(login);
        assertTrue(response.isLoggedIn());

        LogoutLibrarianRequest logout = new LogoutLibrarianRequest();
        logout.setUsernameOrEmail("ajulogbemi09@gmail.com");

        LogoutLibrarianResponse logResponse = service.logoutLibrarian(logout);

        assertFalse(logResponse.isLoggedIn());

    }

    @Test
    public void testThatLibrarianLogin_LibrarianIsLoggedIn_LibrarianLogoutWithWrongEmailOrUsername_LibrarianIsLoggedOut() {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("libra_09");
        login.setPassword("incorrect");

        LoginLibrarianResponse response = service.loginLibrarian(login);
        assertTrue(response.isLoggedIn());

        LogoutLibrarianRequest logout = new LogoutLibrarianRequest();
        logout.setUsernameOrEmail("ajulogbemi09@gmail");

        assertThrows(IllegalArgumentException.class , ()-> service.logoutLibrarian(logout));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public void testThatLibrarianLogin_LibrarianIsLoggedIn_LibrarianLogoutWithBlankEmailOrUsername_AnExceptionIsThrown(String usernameOrEmail) {
        service.registerLibrarian(register);

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("libra_09");
        login.setPassword("incorrect");

        LoginLibrarianResponse response = service.loginLibrarian(login);
        assertTrue(response.isLoggedIn());

        LogoutLibrarianRequest logout = new LogoutLibrarianRequest();
        logout.setUsernameOrEmail(usernameOrEmail);

        assertThrows(IllegalArgumentException.class , ()-> service.logoutLibrarian(logout));

    }
}
