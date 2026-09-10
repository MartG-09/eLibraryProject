package ng.martG.eLibrary.services.auth;

import ng.martG.eLibrary.dtos.requests.authReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.responses.authReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.RegisterReaderResponse;

public interface AuthReaderService {

    RegisterReaderResponse registerReader(RegisterReaderRequest readerRequest);

    LoginReaderResponse loginReader(LoginReaderRequest readerRequest);

    LogoutReaderResponse logoutReader(LogoutReaderRequest readerRequest);

}
