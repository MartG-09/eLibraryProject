package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AuthReaderServiceTest {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private AuthReaderService service;

    @Test
    public void testThatReaderRegisters_AndReaderIsRegistered() {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Martins Emmanuel");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail.com");

        RegisterReaderResponse bob = service.registerReader(register);

        assertEquals(1 , readerRepository.count());
        assertEquals("Martins Emmanuel" , bob.getFullName());

    }

    @Test
    public void testThatAReaderRegistersWithADuplicateUsername_AErrorIsThrown() {
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Martins Emmanuel");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail.com");

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
        readerRepository.deleteAll();
        RegisterReaderRequest register = new RegisterReaderRequest();
        register.setFullName("Martins Emmanuel");
        register.setUsername("martg_09");
        register.setPassword("correct");
        register.setEmail("ajulogbemi09@gmail.com");

        service.registerReader(register);

        register.setFullName("Gold Emmanuel");
        register.setUsername("levit99");
        register.setPassword("correct");
        register.setEmail("gbemi09@gmail.com");

        service.registerReader(register);

        assertEquals(2 , readerRepository.count());

    }
}
