package ng.martG.eLibrary.utils.AuthMappers;

import ng.martG.eLibrary.data.models.Reader;
import ng.martG.eLibrary.dtos.Requests.AuthReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.Responses.AuthReader.RegisterReaderResponse;

public class AuthReaderMapper {

    public static Reader mapToRegisterRequest(RegisterReaderRequest readerRequest) {
        Reader reader = new Reader();

        reader.setFullName(readerRequest.getFullName());
        reader.setUsername(readerRequest.getUsername());
        reader.setPassword(readerRequest.getPassword());
        reader.setEmail(readerRequest.getEmail());

        return reader;
    }

    public static RegisterReaderResponse mapToRegisterResponse(Reader reader) {
        RegisterReaderResponse response = new RegisterReaderResponse();
        response.setFullName(reader.getFullName());
        response.setUsername(reader.getUsername());
        response.setEmail(reader.getEmail());

        return response;

    }
}
