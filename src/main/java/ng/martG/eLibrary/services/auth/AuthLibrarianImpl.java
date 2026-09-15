package ng.martG.eLibrary.services.auth;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.data.models.Librarian;
import ng.martG.eLibrary.data.repositories.LibrarianRepository;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;
import ng.martG.eLibrary.utils.authMappers.AuthLibrarianMapper;
import ng.martG.eLibrary.utils.validator.ValidateLibrarianRequest;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthLibrarianImpl implements AuthLibrarianService {

    private LibrarianRepository librarianRepository;

    @Override
    public RegisterLibrarianResponse registerLibrarian(RegisterLibrarianRequest librarianRequest) {
        RegisterLibrarianRequest request = ValidateLibrarianRequest.validateRegisterRequest(librarianRequest);

        if (!request.getEmail().toLowerCase(Locale.ROOT).endsWith("@gmail.com"))
            throw new IllegalArgumentException("Email must be a gmail address");

        if (librarianRepository.findLibrarianByUsername(request.getUsername().toLowerCase(Locale.ROOT)).isPresent())
            throw new IllegalArgumentException("Username already exists");

        if (librarianRepository.findLibrarianByEmail(request.getEmail().toLowerCase(Locale.ROOT)).isPresent())
                throw new IllegalArgumentException("Email already exists");

        Librarian librarian = AuthLibrarianMapper.mapToRegisterRequest(request);
        librarianRepository.save(librarian);

        return AuthLibrarianMapper.mapToRegisterResponse(librarian);

    }

    @Override
    public LoginLibrarianResponse loginLibrarian(LoginLibrarianRequest librarianRequest) {
        LoginLibrarianRequest request = ValidateLibrarianRequest.validateRequest(librarianRequest);
        String usernameOrEmail = request.getUsernameOrEmail().toLowerCase(Locale.ROOT);

        Optional<Librarian> existingLibrarian;

        if (usernameOrEmail.endsWith("@gmail.com")) {
            existingLibrarian = librarianRepository.findLibrarianByEmail(usernameOrEmail);
        }

        else {
            existingLibrarian = librarianRepository.findLibrarianByUsername(usernameOrEmail);
        }

        if (existingLibrarian.isEmpty())
            throw new IllegalArgumentException("Wrong email or username");

        Librarian librarian = existingLibrarian.get();

        if (!librarian.getPassword().equals(request.getPassword()))
            throw new IllegalArgumentException("Invalid password!!!");

        librarian.setLoggedIn(true);
        librarianRepository.save(librarian);

        return AuthLibrarianMapper.mapToLoginResponse(librarian);
    }

    @Override
    public LogoutLibrarianResponse logoutLibrarian(LogoutLibrarianRequest librarianRequest) {
        Optional<Librarian> existingLibrarian;

        if (librarianRequest.getUsernameOrEmail() == null || librarianRequest.getUsernameOrEmail().isBlank())
            throw new IllegalArgumentException("Enter your email");

        if (librarianRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT).endsWith("@gmail.com"))
            existingLibrarian =  librarianRepository.findLibrarianByEmail(librarianRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT));

        else {
            existingLibrarian = librarianRepository.findLibrarianByUsername(librarianRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT));
        }

        if (existingLibrarian.isEmpty())
            throw new IllegalArgumentException("Wrong email or username");

        Librarian librarian = existingLibrarian.get();

        librarian.setLoggedIn(false);
        librarianRepository.save(librarian);

        return AuthLibrarianMapper.mapToLogoutResponse(librarian);

    }
}
