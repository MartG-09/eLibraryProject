package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.models.Book;
import ng.martG.eLibrary.data.models.BookStatus;
import ng.martG.eLibrary.data.models.Loan;
import ng.martG.eLibrary.data.models.LoanStatus;
import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.data.repositories.BookRepository;
import ng.martG.eLibrary.data.repositories.LoanRepository;
import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.requests.loanRequest.LoanBookRequest;
import ng.martG.eLibrary.dtos.requests.loanRequest.ReturnBookRequest;
import ng.martG.eLibrary.dtos.responses.loanResponse.LoanBookResponse;
import ng.martG.eLibrary.dtos.responses.loanResponse.ReturnBookResponse;
import ng.martG.eLibrary.services.loan.LibrarianLoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LibrarianLoanServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LibrarianLoanService service;

    private Book book;
    private Reader reader;

    @BeforeEach
    public void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        readerRepository.deleteAll();

        book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setCategory("Software");
        book.setTotalCopies(5);
        book.setAvailableCopies(5);
        book.setStatus(BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        reader = new Reader();
        reader.setFullName("Martins Emmanuel");
        reader.setUsername("martg_09");
        reader.setEmail("martg09@gmail.com");
        reader.setPassword("correct");
        reader = readerRepository.save(reader);
    }

    @Test
    public void testThatReaderLoansBook_AndLoanIsRegistered() {
        LoanBookResponse response = service.loanBook(createLoanRequest(1));

        assertEquals(1, loanRepository.count());
        assertEquals(LoanStatus.BORROWED, response.getStatus());
        assertEquals(book.getId(), response.getBookId());
        assertEquals(reader.getId(), response.getReaderId());
    }

    @Test
    public void testThatReaderLoansBookForOneDay_DueDateIsNextDay() {
        LoanBookResponse response = service.loanBook(createLoanRequest(1));

        assertEquals(LocalDate.now(), response.getBorrowDate());
        assertEquals(LocalDate.now().plusDays(1), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansBookForTwoDays_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(2));

        assertEquals(LocalDate.now().plusDays(2), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansBookForOneWeek_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(7));

        assertEquals(LocalDate.now().plusWeeks(1), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansBookForTwoWeeks_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(14));

        assertEquals(LocalDate.now().plusDays(14), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansBookForOneMonth_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(30));

        assertEquals(LocalDate.now().plusDays(30), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansBookForOneYear_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(365));

        assertEquals(LocalDate.now().plusDays(365), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansLastAvailableCopy_BookBecomesUnavailable() {
        book.setAvailableCopies(1);
        bookRepository.save(book);

        service.loanBook(createLoanRequest(7));

        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        assertEquals(0, updatedBook.getAvailableCopies());
        assertEquals(BookStatus.UNAVAILABLE, updatedBook.getStatus());
    }

    @Test
    public void testThatReaderLoansOneOfSeveralCopies_BookRemainsAvailable() {
        service.loanBook(createLoanRequest(7));

        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        assertEquals(4, updatedBook.getAvailableCopies());
        assertEquals(BookStatus.AVAILABLE, updatedBook.getStatus());
    }

    @Test
    public void testThatLoanIsSavedWithBorrowedStatus() {
        service.loanBook(createLoanRequest(7));

        Loan savedLoan = loanRepository.findAll().get(0);
        assertEquals(LoanStatus.BORROWED, savedLoan.getStatus());
    }

    @Test
    public void testThatLoanIsSavedWithBorrowDateToday() {
        service.loanBook(createLoanRequest(7));

        assertEquals(LocalDate.now(), loanRepository.findAll().get(0).getBorrowDate());
    }

    @Test
    public void testThatLoanIsSavedWithCorrectDueDate() {
        service.loanBook(createLoanRequest(10));

        assertEquals(LocalDate.now().plusDays(10), loanRepository.findAll().get(0).getDueDate());
    }

    @Test
    public void testThatLoanIsSavedForRequestedReader() {
        service.loanBook(createLoanRequest(7));

        assertEquals(reader.getId(), loanRepository.findAll().get(0).getReader().getId());
    }

    @Test
    public void testThatLoanIsSavedForRequestedBook() {
        service.loanBook(createLoanRequest(7));

        assertEquals(book.getId(), loanRepository.findAll().get(0).getBook().getId());
    }

    @Test
    public void testThatSuccessfulLoanResponseContainsLoanId() {
        LoanBookResponse response = service.loanBook(createLoanRequest(7));

        assertNotNull(response.getId());
        assertFalse(response.getId().isBlank());
    }

    @Test
    public void testThatReaderLoansBookForThirtyOneDays_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(31));

        assertEquals(LocalDate.now().plusDays(31), response.getDueDate());
    }

    @Test
    public void testThatReaderLoansBookForOneThousandDays_DueDateIsCorrect() {
        LoanBookResponse response = service.loanBook(createLoanRequest(1000));

        assertEquals(LocalDate.now().plusDays(1000), response.getDueDate());
    }

    @Test
    public void testThatLoanDecreasesAvailableCopiesByOne() {
        service.loanBook(createLoanRequest(7));

        assertEquals(4, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatReaderCanLoanBookTwiceWhenCopiesAreAvailable() {
        service.loanBook(createLoanRequest(7));
        service.loanBook(createLoanRequest(14));

        assertEquals(2, loanRepository.count());
        assertEquals(3, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatLoanRequestForMissingBook_AnErrorIsThrown() {
        LoanBookRequest request = createLoanRequest(7);
        request.setBookId(UUID.randomUUID());

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(request));
        assertEquals(0, loanRepository.count());
    }

    @Test
    public void testThatLoanRequestForMissingReader_AnErrorIsThrown() {
        LoanBookRequest request = createLoanRequest(7);
        request.setReaderId("missing-reader");

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(request));
        assertEquals(0, loanRepository.count());
    }

    @Test
    public void testThatReaderCannotLoanBookWithNoAvailableCopies() {
        book.setAvailableCopies(0);
        book.setStatus(BookStatus.UNAVAILABLE);
        bookRepository.save(book);

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(createLoanRequest(7)));
        assertEquals(0, loanRepository.count());
    }

    @Test
    public void testThatReaderCannotLoanBookWithNegativeAvailableCopies() {
        book.setAvailableCopies(-1);
        bookRepository.save(book);

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(createLoanRequest(7)));
        assertEquals(0, loanRepository.count());
    }

    @Test
    public void testThatReaderCannotLoanBookForZeroDays() {
        assertThrows(IllegalArgumentException.class, () -> service.loanBook(createLoanRequest(0)));
    }

    @Test
    public void testThatReaderCannotLoanBookForNegativeOneDay() {
        assertThrows(IllegalArgumentException.class, () -> service.loanBook(createLoanRequest(-1)));
    }

    @Test
    public void testThatReaderCannotLoanBookForNegativeSevenDays() {
        assertThrows(IllegalArgumentException.class, () -> service.loanBook(createLoanRequest(-7)));
    }

    @Test
    public void testThatReaderCannotLoanBookWhenBookIdIsMissing() {
        LoanBookRequest request = createLoanRequest(7);
        request.setBookId(null);

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(request));
    }

    @Test
    public void testThatReaderCannotLoanBookWhenReaderIdIsMissing() {
        LoanBookRequest request = createLoanRequest(7);
        request.setReaderId(null);

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(request));
    }

    @Test
    public void testThatReaderCannotLoanBookWhenReaderIdIsBlank() {
        LoanBookRequest request = createLoanRequest(7);
        request.setReaderId("   ");

        assertThrows(IllegalArgumentException.class, () -> service.loanBook(request));
    }

    @Test
    public void testThatNullLoanRequest_AnErrorIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> service.loanBook(null));
    }

    @Test
    public void testThatReaderCanReturnBorrowedBook_AndLoanIsUpdated() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(LoanStatus.RETURNED, response.getStatus());
        assertEquals(LoanStatus.RETURNED, loanRepository.findById(loan.getId()).orElseThrow().getStatus());
    }

    @Test
    public void testThatReturningBook_SetsReturnDateToToday() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(LocalDate.now(), response.getReturnDate());
    }

    @Test
    public void testThatReturningBook_IncreasesAvailableCopiesByOne() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(5, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatReturningBook_MakesBookAvailable() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 0, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(BookStatus.AVAILABLE, bookRepository.findById(book.getId()).orElseThrow().getStatus());
    }

    @Test
    public void testThatReturnResponseContainsLoanId() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(loan.getId(), response.getId());
    }

    @Test
    public void testThatReturnResponseContainsBookId() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(book.getId(), response.getBookId());
    }

    @Test
    public void testThatReturnResponseContainsReaderId() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(reader.getId(), response.getReaderId());
    }

    @Test
    public void testThatReturnResponseContainsBorrowDate() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(loan.getBorrowDate(), response.getBorrowDate());
    }

    @Test
    public void testThatReturnResponseContainsDueDate() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(loan.getDueDate(), response.getDueDate());
    }

    @Test
    public void testThatReturningBookPersistsLoanStatus() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(LoanStatus.RETURNED, loanRepository.findById(loan.getId()).orElseThrow().getStatus());
    }

    @Test
    public void testThatReturningBookPersistsReturnDate() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(LocalDate.now(), loanRepository.findById(loan.getId()).orElseThrow().getReturnDate());
    }

    @Test
    public void testThatReturningBookDoesNotChangeTotalCopies() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(5, bookRepository.findById(book.getId()).orElseThrow().getTotalCopies());
    }

    @Test
    public void testThatReturningBookAtZeroAvailableCopies_RestoresOneCopy() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 0, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(1, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatReturningBookWithOneCopyAvailable_RestoresTwoCopies() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 1, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(2, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatReturningBookWithThreeCopiesAvailable_RestoresFourCopies() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 3, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(4, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatReturningBookWithFourCopiesAvailable_RestoresAllCopies() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        service.returnBook(createReturnRequest(loan));

        assertEquals(5, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    @Test
    public void testThatReaderCannotReturnLoanThatDoesNotExist() {
        ReturnBookRequest request = new ReturnBookRequest();
        request.setLoanId("missing-loan");

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(request));
    }

    @Test
    public void testThatReaderCannotReturnLoanThatIsAlreadyReturned() {
        Loan loan = createSavedLoan(LoanStatus.RETURNED, 4, 5);

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(createReturnRequest(loan)));
    }

    @Test
    public void testThatReaderCannotReturnLoanThatIsPending() {
        Loan loan = createSavedLoan(LoanStatus.PENDING, 4, 5);

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(createReturnRequest(loan)));
    }

    @Test
    public void testThatReaderCannotReturnLoanWhenBookIsMissing() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);
        loan.setBook(null);
        loanRepository.save(loan);

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(createReturnRequest(loan)));
    }

    @Test
    public void testThatReaderCannotReturnBookWhenInventoryIsAlreadyAtCapacity() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 5, 5);

        assertThrows(IllegalStateException.class, () -> service.returnBook(createReturnRequest(loan)));
    }

    @Test
    public void testThatReaderCannotReturnBookWhenInventoryExceedsCapacity() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 6, 5);

        assertThrows(IllegalStateException.class, () -> service.returnBook(createReturnRequest(loan)));
    }

    @Test
    public void testThatReaderCannotReturnBookWithNullLoanId() {
        ReturnBookRequest request = new ReturnBookRequest();
        request.setLoanId(null);

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(request));
    }

    @Test
    public void testThatReaderCannotReturnBookWithBlankLoanId() {
        ReturnBookRequest request = new ReturnBookRequest();
        request.setLoanId("   ");

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(request));
    }

    @Test
    public void testThatNullReturnRequest_AnErrorIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> service.returnBook(null));
    }

    @Test
    public void testThatReturningBookPreservesOriginalBorrowDate() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);
        LocalDate borrowDate = loan.getBorrowDate();

        service.returnBook(createReturnRequest(loan));

        assertEquals(borrowDate, loanRepository.findById(loan.getId()).orElseThrow().getBorrowDate());
    }

    @Test
    public void testThatReturningBookPreservesOriginalDueDate() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);
        LocalDate dueDate = loan.getDueDate();

        service.returnBook(createReturnRequest(loan));

        assertEquals(dueDate, loanRepository.findById(loan.getId()).orElseThrow().getDueDate());
    }

    @Test
    public void testThatSuccessfulReturnIsSavedOnce() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertNotNull(response.getReturnDate());
        assertEquals(1, loanRepository.count());
    }

    @Test
    public void testThatReturnResponseContainsUpdatedLoanStatus() {
        Loan loan = createSavedLoan(LoanStatus.BORROWED, 4, 5);

        ReturnBookResponse response = service.returnBook(createReturnRequest(loan));

        assertEquals(LoanStatus.RETURNED, response.getStatus());
        assertEquals(LocalDate.now(), response.getReturnDate());
    }

    @Test
    public void testThatFailedReturnDoesNotIncreaseAvailableCopies() {
        Loan loan = createSavedLoan(LoanStatus.RETURNED, 4, 5);

        assertThrows(IllegalArgumentException.class, () -> service.returnBook(createReturnRequest(loan)));

        assertEquals(4, bookRepository.findById(book.getId()).orElseThrow().getAvailableCopies());
    }

    private LoanBookRequest createLoanRequest(int loanDays) {
        LoanBookRequest request = new LoanBookRequest();
        request.setBookId(book.getId());
        request.setReaderId(reader.getId());
        request.setLoanDays(loanDays);
        return request;
    }

    private ReturnBookRequest createReturnRequest(Loan loan) {
        ReturnBookRequest request = new ReturnBookRequest();
        request.setLoanId(loan.getId());
        return request;
    }

    private Loan createSavedLoan(LoanStatus status, int availableCopies, int totalCopies) {
        book.setAvailableCopies(availableCopies);
        book.setTotalCopies(totalCopies);
        book.setStatus(availableCopies == 0 ? BookStatus.UNAVAILABLE : BookStatus.AVAILABLE);
        book = bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setBorrowDate(LocalDate.now().minusDays(1));
        loan.setDueDate(LocalDate.now().plusDays(6));
        loan.setStatus(status);
        return loanRepository.save(loan);
    }
}
