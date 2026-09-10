package ng.martG.eLibrary.utils.authMappers;

import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.responses.authReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.RegisterReaderResponse;

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


    public static RegisterReaderResponse mapToRegisterResponse(Reader reader) {
        RegisterReaderResponse response = new RegisterReaderResponse();
        response.setFullName(reader.getFullName());
        response.setUsername(reader.getUsername());
        response.setEmail(reader.getEmail());

        return response;

    }

    public static LoginReaderResponse mapToLoginResponse(Reader reader) {
        LoginReaderResponse response = new LoginReaderResponse();
        response.setEmail(reader.getEmail());
        response.setUsername(reader.getUsername());
        response.setLoggedIn(reader.isLoggedIn());

        return response;
    }

    public static LogoutReaderResponse mapToLogoutResponse(Reader reader) {
        LogoutReaderResponse response = new LogoutReaderResponse();
        response.setUsername(reader.getUsername());
        response.setLoggedIn(false);

        return response;
    }
}
