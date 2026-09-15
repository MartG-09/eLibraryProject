package ng.martG.eLibrary.services.auth;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.requests.authReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.responses.authReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.RegisterReaderResponse;
import ng.martG.eLibrary.utils.validator.ValidateReaderRequest;
import ng.martG.eLibrary.utils.authMappers.AuthReaderMapper;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthReaderImpl implements AuthReaderService{

    private ReaderRepository readerRepository;

    @Override
    public RegisterReaderResponse registerReader(RegisterReaderRequest readerRequest) {
        RegisterReaderRequest request = ValidateReaderRequest.validateRegisterRequest(readerRequest);

        if (!request.getEmail().toLowerCase(Locale.ROOT).endsWith("@gmail.com"))
            throw new IllegalArgumentException("Email must be a gmail address");

        if (readerRepository.findReaderByUsername(request.getUsername().toLowerCase(Locale.ROOT)).isPresent())
            throw new IllegalArgumentException("Username already exists");

        if (readerRepository.findReaderByEmail(request.getEmail().toLowerCase(Locale.ROOT)).isPresent())
                throw new IllegalArgumentException("Email already exists");

        Reader reader = AuthReaderMapper.mapToRegisterRequest(request);
        readerRepository.save(reader);

        return AuthReaderMapper.mapToRegisterResponse(reader);

    }

    @Override
    public LoginReaderResponse loginReader(LoginReaderRequest readerRequest) {
        LoginReaderRequest request = ValidateReaderRequest.validateRequest(readerRequest);
        String usernameOrEmail = request.getUsernameOrEmail().toLowerCase(Locale.ROOT);

        Optional<Reader> existingReader;

        if (usernameOrEmail.endsWith("@gmail.com")) {
            existingReader = readerRepository.findReaderByEmail(usernameOrEmail);
        }

        else {
            existingReader = readerRepository.findReaderByUsername(usernameOrEmail);
        }

        if (existingReader.isEmpty())
            throw new IllegalArgumentException("Wrong email or username");

        Reader reader = existingReader.get();

        if (!reader.getPassword().equals(request.getPassword()))
            throw new IllegalArgumentException("Invalid password!!!");

        reader.setLoggedIn(true);
        readerRepository.save(reader);

        return AuthReaderMapper.mapToLoginResponse(reader);
    }

    @Override
    public LogoutReaderResponse logoutReader(LogoutReaderRequest readerRequest) {
        Optional<Reader> existingReader;

        if (readerRequest.getUsernameOrEmail() == null || readerRequest.getUsernameOrEmail().isBlank())
            throw new IllegalArgumentException("Enter your email");

        if (readerRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT).endsWith("@gmail.com"))
            existingReader =  readerRepository.findReaderByEmail(readerRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT));

        else {
            existingReader = readerRepository.findReaderByUsername(readerRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT));
        }

        if (existingReader.isEmpty())
            throw new IllegalArgumentException("Wrong email or username");

        Reader reader = existingReader.get();

        reader.setLoggedIn(false);
        readerRepository.save(reader);

        return AuthReaderMapper.mapToLogoutResponse(reader);


    }

}
