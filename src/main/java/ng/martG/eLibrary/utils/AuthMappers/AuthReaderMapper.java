package ng.martG.eLibrary.utils.AuthMappers;

import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;

import java.util.Locale;

public class AuthReaderMapper {

    public static Reader mapToRegisterRequest(RegisterReaderRequest readerRequest) {
        Reader reader = new Reader();

        reader.setFullName(readerRequest.getFullName());
        reader.setUsername(readerRequest.getUsername().toLowerCase(Locale.ROOT));
        reader.setPassword(readerRequest.getPassword());
        reader.setEmail(readerRequest.getEmail().toLowerCase(Locale.ROOT));

        return reader;
    }

    public static RegisterReaderRequest validateRegisterRequest(RegisterReaderRequest readerRequest) {
        if (readerRequest.getEmail().isBlank())
            throw new IllegalArgumentException("Enter a valid email");


        if (readerRequest.getUsername().isBlank())
            throw new IllegalArgumentException("Enter a valid username");

        if (readerRequest.getPassword().isBlank())
            throw new IllegalArgumentException("Enter your password");

        return readerRequest;

    }

    public static RegisterReaderResponse mapToRegisterResponse(Reader reader) {
        RegisterReaderResponse response = new RegisterReaderResponse();
        response.setFullName(reader.getFullName());
        response.setUsername(reader.getUsername());
        response.setEmail(reader.getEmail());

        return response;

    }

    public static LoginReaderRequest validateRequest(LoginReaderRequest loginRequest) {
        if (loginRequest.getUsernameOrEmail().isBlank())
            throw new IllegalArgumentException("Enter a valid username or email");

        if (loginRequest.getPassword().isBlank())
            throw new IllegalArgumentException("Enter your password");

        return loginRequest;

    }

    public static LoginReaderResponse mapToLoginResponse(Reader reader) {
        LoginReaderResponse response = new LoginReaderResponse();
        response.setEmail(reader.getEmail());
        response.setUsername(reader.getUsername());
        response.setLoggedIn(reader.isLoggedIn());

        return response;
    }
}
