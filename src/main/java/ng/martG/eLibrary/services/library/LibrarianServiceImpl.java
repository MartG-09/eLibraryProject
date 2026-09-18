package ng.martG.eLibrary.services.library;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.data.models.Book;
import ng.martG.eLibrary.data.models.Librarian;
import ng.martG.eLibrary.data.repositories.BookRepository;
import ng.martG.eLibrary.data.repositories.LibrarianRepository;
import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.DeleteBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.FindBookByIdRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.UpdateBookRequest;
import ng.martG.eLibrary.dtos.responses.librarianResponse.*;
import ng.martG.eLibrary.utils.librarianMappers.BookMapper;
import ng.martG.eLibrary.utils.validator.ValidateAddBookRequest;
import ng.martG.eLibrary.utils.validator.ValidateUpdateBookRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibrarianServiceImpl implements LibrarianService {

    private final LibrarianRepository librarianRepository;
    private BookRepository bookRepository;

    @Override
    public AddBookResponse addBook(AddBookRequest request) {
        ValidateAddBookRequest.validateAddBook(request);

        Optional<Librarian> librarian;

        if (request.getUsernameOrEmail().endsWith("@gmail.com")) {
            librarian = librarianRepository.findLibrarianByEmail(request.getUsernameOrEmail().toLowerCase(Locale.ROOT));
        }
        else {
            librarian = librarianRepository.findLibrarianByUsername(request.getUsernameOrEmail().toLowerCase(Locale.ROOT));
        }

        if (librarian.isEmpty()) {
            throw new IllegalArgumentException("Librarian not found");
        }

        if(!librarian.get().isLoggedIn())
            throw new IllegalArgumentException("You are not logged in");

        if (bookRepository.findByTitleAndAuthor(request.getTitle() , request.getAuthor()).isPresent())
            throw new IllegalArgumentException("Book already exists");

        Book book = BookMapper.mapToAddBookRequest(request);
        bookRepository.save(book);

        return BookMapper.mapToAddBookResponse(book);
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest request) {
        Optional<Book> existingBook = bookRepository.findById(request.getId());

        if (existingBook.isEmpty())
            throw new IllegalArgumentException("Book not found");

        Book book = existingBook.get();
        bookRepository.delete(existingBook.get());

        return BookMapper.mapToDeleteBookResponse(book);

    }

    @Override
    public FindBookByIdResponse findBookById(FindBookByIdRequest request) {
        Optional<Book> existingBook = bookRepository.findById(request.getId());

        if (existingBook.isEmpty())
            throw new IllegalArgumentException("Book not found");

        Book book = existingBook.get();

        return BookMapper.mapToFindBookByIdResponse(book);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest request) {
        Optional<Book> existingBook = bookRepository.findById(request.getId());

        if (existingBook.isEmpty())
            throw new IllegalArgumentException("Book not found");

        UpdateBookRequest bookRequest = ValidateUpdateBookRequest.validateUpdateBookRequest(request);
        Book book = existingBook.get();

        BookMapper.mapToUpdateBookRequest(bookRequest ,  book);
        bookRepository.save(book);

        return BookMapper.mapToUpdateBookResponse(book);
    }

    @Override
    public UpdateBookResponse patchBook(UpdateBookRequest request) {
        Optional<Book> existingBook = bookRepository.findById(request.getId());

        if (existingBook.isEmpty())
            throw new IllegalArgumentException("Book not found");

        Book book = existingBook.get();
        BookMapper.mapToPatchBookRequest(request , book);

        bookRepository.save(book);

        return BookMapper.mapToUpdateBookResponse(book);

    }

    @Override
    public FindAllBookResponse findAllBook() {
        List<Book> books =  bookRepository.findAll();

        List<BookResponse> bookResponses = new ArrayList<>();

        for (Book book : books) {
            bookResponses.add(BookMapper.mapToBookResponse(book));
        }

        FindAllBookResponse findAll = new FindAllBookResponse();
        findAll.setBooks(bookResponses);

        return findAll;
    }

}
