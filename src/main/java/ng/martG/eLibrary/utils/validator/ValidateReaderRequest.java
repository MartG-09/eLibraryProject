package ng.martG.eLibrary.utils.validator;

import ng.martG.eLibrary.dtos.requests.authReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;

public class ValidateReaderRequest {

    public static RegisterReaderRequest validateRegisterRequest(RegisterReaderRequest readerRequest) {
        if (readerRequest.getEmail() == null || readerRequest.getEmail().isBlank())
            throw new IllegalArgumentException("Enter a valid email");


        if (readerRequest.getUsername() == null || readerRequest.getUsername().isBlank())
            throw new IllegalArgumentException("Enter a valid username");

        if (readerRequest.getPassword() == null || readerRequest.getPassword().isBlank())
            throw new IllegalArgumentException("Enter your password");

        if (readerRequest.getFullName() == null || readerRequest.getFullName().isBlank())
            throw new IllegalArgumentException("Enter a valid full name");

        return readerRequest;

    }

    public static LoginReaderRequest validateRequest(LoginReaderRequest loginRequest) {
        if (loginRequest.getUsernameOrEmail() == null || loginRequest.getUsernameOrEmail().isBlank())
            throw new IllegalArgumentException("Enter a valid username or email");

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank())
            throw new IllegalArgumentException("Enter your password");

        return loginRequest;

    }
}
