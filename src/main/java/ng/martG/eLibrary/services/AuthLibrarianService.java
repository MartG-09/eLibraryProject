package ng.martG.eLibrary.services;

import ng.martG.eLibrary.dtos.Requests.AuthLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.Requests.AuthLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.Requests.AuthLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.Responses.AuthLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.Responses.AuthLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.Responses.AuthLibrarian.RegisterLibrarianResponse;

public interface AuthLibrarianService {

    RegisterLibrarianResponse registerLibrarian(RegisterLibrarianRequest librarianRequest);

    LoginLibrarianResponse loginLibrarian(LoginLibrarianRequest librarianRequest);

    LogoutLibrarianResponse logoutLibrarian(LogoutLibrarianRequest librarianRequest);


}
