package ng.martG.eLibrary.utils.authMappers;

import ng.martG.eLibrary.data.models.Librarian;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;

import java.util.Locale;

public class AuthLibrarianMapper {

    public static Librarian mapToRegisterRequest(RegisterLibrarianRequest librarianRequest) {
        Librarian librarian = new Librarian();

        librarian.setFullName(librarianRequest.getFullName());
        librarian.setUsername(librarianRequest.getUsername().toLowerCase(Locale.ROOT));
        librarian.setPassword(librarianRequest.getPassword());
        librarian.setEmail(librarianRequest.getEmail().toLowerCase(Locale.ROOT));

        return librarian;
    }

    public static RegisterLibrarianResponse mapToRegisterResponse(Librarian librarian) {
        RegisterLibrarianResponse response = new RegisterLibrarianResponse();
        response.setFullName(librarian.getFullName());
        response.setUsername(librarian.getUsername());
        response.setEmail(librarian.getEmail());

        return response;

    }

    public static LoginLibrarianResponse mapToLoginResponse(Librarian librarian) {
        LoginLibrarianResponse response = new LoginLibrarianResponse();
        response.setEmail(librarian.getEmail());
        response.setUsername(librarian.getUsername());
        response.setLoggedIn(librarian.isLoggedIn());

        return response;

    }

    public static LogoutLibrarianResponse mapToLogoutResponse(Librarian librarian) {
        LogoutLibrarianResponse response = new LogoutLibrarianResponse();
        response.setUsername(librarian.getUsername());
        response.setLoggedIn(false);

        return response;
    }

}
