package ng.martG.eLibrary.services;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.data.repositories.ReaderRepository;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;
import ng.martG.eLibrary.utils.AuthMappers.AuthReaderMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthReaderImpl implements AuthReaderService{

    private ReaderRepository readerRepository;

    @Override
    public RegisterReaderResponse registerReader(RegisterReaderRequest readerRequest) {
        Optional<Reader> exiting_reader = readerRepository.findReaderByUsername(readerRequest.getUsername());

        if (exiting_reader.isPresent())
            throw new IllegalArgumentException("Username already exist");

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
