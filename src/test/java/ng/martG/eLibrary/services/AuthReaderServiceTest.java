package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    public void testThatReaderRegistersWithEmailNotEndingWithAtGmailDotcom_AndReaderIsRegistered() {
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

}
