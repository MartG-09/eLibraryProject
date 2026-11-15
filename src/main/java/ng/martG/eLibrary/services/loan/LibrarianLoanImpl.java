package ng.martG.eLibrary.services.loan;

import lombok.AllArgsConstructor;
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
import ng.martG.eLibrary.utils.librarianMappers.LoanMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LibrarianLoanImpl implements LibrarianLoanService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final LoanRepository loanRepository;

    @Override
    public LoanBookResponse loanBook(LoanBookRequest request) {
        if (request == null)
            throw new IllegalArgumentException("Loan request is required");
        if (request.getBookId() == null)
            throw new IllegalArgumentException("Book id is required");
        if (request.getReaderId() == null || request.getReaderId().isBlank())
            throw new IllegalArgumentException("Reader id is required");
        if (request.getLoanDays() <= 0)
            throw new IllegalArgumentException("Loan days must be greater than 0");

        Optional<Book> book = bookRepository.findById(request.getBookId());
        if (book.isEmpty())
            throw new IllegalArgumentException("Book Not Found");

        Optional<Reader> reader = readerRepository.findById(request.getReaderId());
        if (reader.isEmpty())
            throw new IllegalArgumentException("Reader Not Found");

        if (book.get().getAvailableCopies() <= 0)
            throw new IllegalArgumentException("Book is not available");

        Loan loan = new Loan();
        loan.setId(UUID.randomUUID().toString());
        loan.setBook(book.get());
        loan.setReader(reader.get());
        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(request.getLoanDays()));
        loan.setStatus(LoanStatus.BORROWED);

        Book borrowedBook = book.get();
        borrowedBook.setAvailableCopies(borrowedBook.getAvailableCopies() - 1);
        if (borrowedBook.getAvailableCopies() == 0)
            borrowedBook.setStatus(BookStatus.UNAVAILABLE);
        else
            borrowedBook.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(borrowedBook);
        loanRepository.save(loan);

        return LoanMapper.mapToLoanBookResponse(loan);
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        if (request == null || request.getLoanId() == null || request.getLoanId().isBlank())
            throw new IllegalArgumentException("Loan id is required");

        Optional<Loan> existingLoan = loanRepository.findById(request.getLoanId());
        if (existingLoan.isEmpty())
            throw new IllegalArgumentException("Loan Not Found");

        Loan loan = existingLoan.get();
        if (loan.getStatus() != LoanStatus.BORROWED)
            throw new IllegalArgumentException("Loan is not currently borrowed");

        Book book = loan.getBook();
        if (book == null)
            throw new IllegalArgumentException("Book Not Found");

        if (book.getAvailableCopies() >= book.getTotalCopies())
            throw new IllegalStateException("Book inventory is already at its maximum");

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        book.setStatus(BookStatus.AVAILABLE);
        loan.setReturnDate(LocalDate.now());
        loan.setStatus(LoanStatus.RETURNED);

        bookRepository.save(book);
        loanRepository.save(loan);

        return LoanMapper.mapToReturnBookResponse(loan);
    }

}

