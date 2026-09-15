package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.repositories.BookRepository;
import ng.martG.eLibrary.data.repositories.LibrarianRepository;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.DeleteBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.FindBookByIdRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.AddBookResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.DeleteBookResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.FindBookByIdResponse;
import ng.martG.eLibrary.services.auth.AuthLibrarianService;
import ng.martG.eLibrary.services.library.LibrarianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LibrarianServiceTest {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthLibrarianService authLibrarian;

    @Autowired
    private LibrarianService service;

    private LoginLibrarianResponse loginResponse;


    @BeforeEach
    public void setUp() {
        librarianRepository.deleteAll();
        RegisterLibrarianRequest register = new RegisterLibrarianRequest();
        register.setFullName("Martins Emmanuel");
        register.setUsername("libra_09");
        register.setPassword("incorrect");
        register.setEmail("ajulogbemi09@gmail.com");
        RegisterLibrarianResponse bob = authLibrarian.registerLibrarian(register);

        assertEquals(1 , librarianRepository.count());
        assertEquals("Martins Emmanuel" , bob.getFullName());

        LoginLibrarianRequest login = new LoginLibrarianRequest();

        login.setUsernameOrEmail("libra_09");
        login.setPassword("incorrect");

        loginResponse = authLibrarian.loginLibrarian(login);

    }

    @Test
    public void testThatLibrarianIsLoggedIn_LibrarianAddBook_BookIsAdded() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        service.addBook(add);
        assertEquals(1, bookRepository.count());

    }

    @Test
    public void testThatLibrarianIsNotLoggedIn_LibrarianAddBook_AnExceptionIsThrown() {
        assertTrue(loginResponse.isLoggedIn());

        LogoutLibrarianRequest logout = new LogoutLibrarianRequest();
        logout.setUsernameOrEmail("libra_09");
        LogoutLibrarianResponse logoutLibrarian = authLibrarian.logoutLibrarian(logout);

        assertFalse(logoutLibrarian.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @Test
    public void testThatLibrarianIsLoggedIn_ButPassInWrongUsernameOrEmail_LibrarianAddBook_AnExceptionIsThrown() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_10");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);
        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @Test
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsAuthor_LibrarianAddBook_AnExceptionIsThrown() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @Test
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsTitle_LibrarianAddBook_AnExceptionIsThrown() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @Test
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsCategory_LibrarianAddBook_AnExceptionIsThrown() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @Test
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsDescription_LibrarianAddBook() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("");
        add.setTotalCopies(5);

        service.addBook(add);
        assertEquals(1, bookRepository.count());

    }

    @Test
    public void testThatLibrarianIsLoggedIn_ButPassZeroAsTotalCopies_LibrarianAddBook_AnExceptionIsThrown() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(0);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @Test
    public void testThatLibrarianIsLoggedIn_LibrarianAddsBookThatHaveDuplicateTitleAndAuthor_BookIsNotAdded() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse book = service.addBook(add);
        assertEquals(1, bookRepository.count());
        assertEquals("Clean Code", book.getTitle());

        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert M Martins");
        add.setCategory("Money");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(3);
        service.addBook(add);
        assertEquals(2, bookRepository.count());

    }

    @Test
    public void testThatLibrarianIsLoggedIn_LibrarianAddsBook_BookIsAdded_LibrarianFindsBookById_BookIsFound() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse book = service.addBook(add);
        assertEquals(1, bookRepository.count());
        assertEquals("Clean Code", book.getTitle());

        add.setUsernameOrEmail("libra_09");
        add.setTitle("Money bank");
        add.setAuthor("Ariyo Ryan");
        add.setCategory("Business");
        add.setDescription("For begineers in business");
        add.setTotalCopies(2);
        AddBookResponse newBook = service.addBook(add);
        assertEquals(2, bookRepository.count());
        assertEquals("Money bank", newBook.getTitle());

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(book.getId());

        FindBookByIdResponse foundBook = service.findBookById(findBook);
        assertEquals(book.getTitle() , foundBook.getTitle());
        assertEquals(book.getAuthor() , foundBook.getAuthor());
        assertEquals(book.getStatus() , foundBook.getBookStatus());

    }

     @Test
    public void testThatLibrarianIsLoggedIn_LibrarianAddsBook_BookIsAdded_LibrarianDeletesBookById_BookCannotbeFound() {
         assertTrue(loginResponse.isLoggedIn());

         bookRepository.deleteAll();
         AddBookRequest add = new AddBookRequest();
         add.setUsernameOrEmail("libra_09");
         add.setTitle("Clean Code");
         add.setAuthor("Robert C Martins");
         add.setCategory("Software");
         add.setDescription("For seniors engineers");
         add.setTotalCopies(5);

         AddBookResponse book = service.addBook(add);
         assertEquals(1, bookRepository.count());
         assertEquals("Clean Code", book.getTitle());

         add.setUsernameOrEmail("libra_09");
         add.setTitle("Money bank");
         add.setAuthor("Ariyo Ryan");
         add.setCategory("Business");
         add.setDescription("For begineers in business");
         add.setTotalCopies(2);
         AddBookResponse newBook = service.addBook(add);
         assertEquals(2, bookRepository.count());
         assertEquals("Money bank", newBook.getTitle());

         DeleteBookRequest deleteBook = new DeleteBookRequest();
         deleteBook.setId(book.getId());

         DeleteBookResponse removedBook = service.deleteBook(deleteBook);
         assertEquals(1, bookRepository.count());

         assertEquals("Clean Code", removedBook.getTitle());
         assertEquals("Robert C Martins", removedBook.getAuthor());

     }

     @Test
    public void testThatLibrarianIsLoggedIn_LibrarianAddsBook_BookIsAdded_LibrarianDeletesBookById_FindBookById_ItThrowAnException() {
         assertTrue(loginResponse.isLoggedIn());

         bookRepository.deleteAll();
         AddBookRequest add = new AddBookRequest();
         add.setUsernameOrEmail("libra_09");
         add.setTitle("Clean Code");
         add.setAuthor("Robert C Martins");
         add.setCategory("Software");
         add.setDescription("For seniors engineers");
         add.setTotalCopies(5);

         AddBookResponse book = service.addBook(add);
         assertEquals(1, bookRepository.count());
         assertEquals("Clean Code", book.getTitle());

         add.setUsernameOrEmail("libra_09");
         add.setTitle("Money bank");
         add.setAuthor("Ariyo Ryan");
         add.setCategory("Business");
         add.setDescription("For begineers in business");
         add.setTotalCopies(2);
         AddBookResponse newBook = service.addBook(add);
         assertEquals(2, bookRepository.count());
         assertEquals("Money bank", newBook.getTitle());

         DeleteBookRequest deleteBook = new DeleteBookRequest();
         deleteBook.setId(book.getId());

         service.deleteBook(deleteBook);
         assertEquals(1, bookRepository.count());

         FindBookByIdRequest findBookById = new FindBookByIdRequest();
         findBookById.setId(book.getId());

         assertThrows(IllegalArgumentException.class , ()-> service.findBookById(findBookById));

     }
}
