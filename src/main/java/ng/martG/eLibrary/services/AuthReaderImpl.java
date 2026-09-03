package ng.martG.eLibrary.services;

import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;
import ng.martG.eLibrary.utils.AuthMappers.AuthReaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthReaderImpl implements AuthReaderService{

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public RegisterReaderResponse registerReader(RegisterReaderRequest readerRequest) {
        Reader reader = AuthReaderMapper.mapToRegisterRequest(readerRequest);
        readerRepository.save(reader);

        return AuthReaderMapper.mapToRegisterResponse(reader);
    }

    @Override
    public LoginReaderResponse loginReader(LoginReaderRequest readerRequest) {
        return null;
    }

    @Override
    public LogoutReaderResponse logoutReader(LogoutReaderRequest readerRequest) {
        return null;
    }

}
