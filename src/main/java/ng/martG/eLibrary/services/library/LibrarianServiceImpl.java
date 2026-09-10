package ng.martG.eLibrary.services.library;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.data.models.Book;
import ng.martG.eLibrary.data.models.Librarian;
import ng.martG.eLibrary.data.repositories.BookRepository;
import ng.martG.eLibrary.data.repositories.LibrarianRepository;
import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.responses.librarianResponse.AddBookResponse;
import ng.martG.eLibrary.utils.librarianMappers.BookMapper;
import ng.martG.eLibrary.utils.validator.ValidateAddBookRequest;
import org.springframework.stereotype.Service;

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
}
