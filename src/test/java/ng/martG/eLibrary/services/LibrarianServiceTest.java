package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.repositories.BookRepository;
import ng.martG.eLibrary.data.repositories.LibrarianRepository;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.DeleteBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.FindBookByIdRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.UpdateBookRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.*;
import ng.martG.eLibrary.services.auth.AuthLibrarianService;
import ng.martG.eLibrary.services.library.LibrarianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsAuthor_LibrarianAddBook_AnExceptionIsThrown(String author) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor(author);
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsTitle_LibrarianAddBook_AnExceptionIsThrown(String title) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle(title);
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsCategory_LibrarianAddBook_AnExceptionIsThrown(String category) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory(category);
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        assertThrows(IllegalArgumentException.class , ()-> service.addBook(add));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    public void testThatLibrarianIsLoggedIn_ButPassBlankValuesAsDescription_LibrarianAddBook(String description) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription(description);
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
        add.setCategory("Science");
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

     @ParameterizedTest
     @NullAndEmptySource
     @ValueSource(strings = {"   "})
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianUpdatesBookByPassingBlankAuthor_ItThrowAnException(String author) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Romeo and Juliet");
        updateBook.setAuthor(author);

        assertThrows(IllegalArgumentException.class , ()-> service.updateBook(updateBook));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianUpdatesBookByPassingBlankTitle_ItThrowAnException(String title) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setAuthor(title);

        assertThrows(IllegalArgumentException.class , ()-> service.updateBook(updateBook));

    }

     @ParameterizedTest
     @NullAndEmptySource
     @ValueSource(strings = {"   "})
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianUpdatesBookByPassingBlankCategory_ItThrowAnException(String category) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Romeo and Juliet");
        updateBook.setAuthor("Musa leee");
        updateBook.setCategory(category);

        assertThrows(IllegalArgumentException.class , ()-> service.updateBook(updateBook));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianUpdatesBookByPassingBlankDescription_ItUpdates(String description) {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Romeo and Juliet");
        updateBook.setAuthor("Musa leee");
        updateBook.setCategory("For Business");
        updateBook.setDescription(description);

        UpdateBookResponse bookResponse = service.updateBook(updateBook);

        FindBookByIdRequest findBookByIdRequest = new FindBookByIdRequest();
        findBookByIdRequest.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBookByIdRequest);

        assertEquals(1, bookRepository.count());
        assertEquals(found.getTitle() , bookResponse.getTitle());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianUpdatesBook_ItUpdates() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Romeo and Juliet");
        updateBook.setAuthor("Musa leee");
        updateBook.setCategory("For Business");
        updateBook.setDescription("description");

        UpdateBookResponse bookResponse = service.updateBook(updateBook);

        FindBookByIdRequest findBookByIdRequest = new FindBookByIdRequest();
        findBookByIdRequest.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBookByIdRequest);

        assertEquals(1, bookRepository.count());
        assertEquals(found.getTitle() , bookResponse.getTitle());
        assertEquals(found.getAuthor() , bookResponse.getAuthor());
        assertEquals(found.getCategory() , bookResponse.getCategory());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianWantsToUpdateAuthorName_ItIsUpdated() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setAuthor("Musa leee");

        UpdateBookResponse bookResponse = service.patchBook(updateBook);

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBook);

        assertEquals(1, bookRepository.count());
        assertEquals(bookResponse.getAuthor() , found.getAuthor());
        assertNotEquals(addResponse.getAuthor() , bookResponse.getAuthor());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianWantsToUpdateTitle_ItIsUpdated() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Mechanical Book");

        UpdateBookResponse bookResponse = service.patchBook(updateBook);

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBook);

        assertEquals(1, bookRepository.count());
        assertEquals(bookResponse.getTitle() , found.getTitle());
        assertNotEquals(addResponse.getTitle() , bookResponse.getTitle());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianWantsToUpdateCategory_ItIsUpdated() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setCategory("Art Works");

        UpdateBookResponse bookResponse = service.patchBook(updateBook);

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBook);

        assertEquals(1, bookRepository.count());
        assertEquals(bookResponse.getCategory() , found.getCategory());
        assertNotEquals(addResponse.getCategory() , bookResponse.getCategory());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianWantsToUpdateDescription_ItIsUpdated() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setDescription("For guys aove 18yrs of age");

        UpdateBookResponse bookResponse = service.patchBook(updateBook);

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBook);

        assertEquals(1, bookRepository.count());
        assertEquals(bookResponse.getDescription() , found.getDescription());
        assertNotEquals(addResponse.getDescription() , bookResponse.getDescription());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianWantsToUpdateDescriptionAndTitle_ItIsUpdated() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Mechanical Book");
        updateBook.setDescription("For guys aove 18yrs of age");

        UpdateBookResponse bookResponse = service.patchBook(updateBook);

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBook);

        assertEquals(1, bookRepository.count());
        assertEquals(bookResponse.getDescription() , found.getDescription());
        assertNotEquals(addResponse.getDescription() , bookResponse.getDescription());

        assertEquals(bookResponse.getTitle() , found.getTitle());
        assertNotEquals(addResponse.getTitle() , bookResponse.getTitle());

    }

    @Test
    public void testThatLibrarianAddsBook_BookIsAdded_LibrarianWantsToUpdateDescriptionTitleAndAuthor_ItIsUpdated() {
        assertTrue(loginResponse.isLoggedIn());

        bookRepository.deleteAll();
        AddBookRequest add = new AddBookRequest();
        add.setUsernameOrEmail("libra_09");
        add.setTitle("Clean Code");
        add.setAuthor("Robert C Martins");
        add.setCategory("Software");
        add.setDescription("For seniors engineers");
        add.setTotalCopies(5);

        AddBookResponse addResponse = service.addBook(add);
        assertEquals(1, bookRepository.count());

        UpdateBookRequest updateBook = new UpdateBookRequest();
        updateBook.setId(addResponse.getId());
        updateBook.setTitle("Mechanical Book");
        updateBook.setAuthor("Musa leee");
        updateBook.setDescription("For guys aove 18yrs of age");

        UpdateBookResponse bookResponse = service.patchBook(updateBook);

        FindBookByIdRequest findBook = new FindBookByIdRequest();
        findBook.setId(bookResponse.getId());

        FindBookByIdResponse found = service.findBookById(findBook);

        assertEquals(1, bookRepository.count());
        assertEquals(bookResponse.getDescription() , found.getDescription());
        assertNotEquals(addResponse.getDescription() , bookResponse.getDescription());

        assertEquals(bookResponse.getTitle() , found.getTitle());
        assertNotEquals(addResponse.getTitle() , bookResponse.getTitle());

        assertEquals(bookResponse.getAuthor() , found.getAuthor());
        assertNotEquals(addResponse.getAuthor() , bookResponse.getAuthor());

    }

    @Test
    public void testThatLibrarianIsLoggedIn_LibrarianAddThreeBooks_BookIsAdded_LibrarianFindsAllBook_BookIsFound() {
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


        add.setUsernameOrEmail("libra_09");
        add.setTitle("Music life");
        add.setAuthor("Dj Melvin");
        add.setCategory("Music");
        add.setDescription("");
        add.setTotalCopies(15);

        AddBookResponse myBook = service.addBook(add);
        assertEquals(3, bookRepository.count());
        assertEquals("Music life", myBook.getTitle());

        FindAllBookResponse findBook = service.findAllBook();

        BookResponse firstBook = findBook.getBooks().get(0);
        BookResponse secondBook = findBook.getBooks().get(1);
        BookResponse thirdBook = findBook.getBooks().get(2);

        assertEquals(book.getTitle() ,  firstBook.getTitle());
        assertEquals(book.getAuthor() ,  firstBook.getAuthor());
        assertEquals(book.getId() ,  firstBook.getBookId());

        assertEquals(newBook.getTitle() ,  secondBook.getTitle());
        assertEquals(newBook.getAuthor() ,  secondBook.getAuthor());
        assertEquals(newBook.getId() ,  secondBook.getBookId());

        assertEquals(myBook.getTitle() ,  thirdBook.getTitle());
        assertEquals(myBook.getAuthor() ,  thirdBook.getAuthor());
        assertEquals(myBook.getId() ,  thirdBook.getBookId());

    }


}
