package ng.martG.eLibrary.services.auth;

import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;

public interface AuthLibrarianService {

    RegisterLibrarianResponse registerLibrarian(RegisterLibrarianRequest librarianRequest);

    LoginLibrarianResponse loginLibrarian(LoginLibrarianRequest librarianRequest);

    LogoutLibrarianResponse logoutLibrarian(LogoutLibrarianRequest librarianRequest);


}
