package ng.martG.eLibrary.services;

import ng.martG.eLibrary.dtos.Requests.AuthReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;

public interface AuthReaderService {

    RegisterReaderResponse registerReader(RegisterReaderRequest readerRequest);

    LoginReaderResponse loginReader(LoginReaderRequest readerRequest);

    LogoutReaderResponse logoutReader(LogoutReaderRequest readerRequest);

}
