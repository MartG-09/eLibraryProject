package ng.martG.eLibrary.utils.validator;

import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;

public class ValidateLibrarianRequest {

    public static RegisterLibrarianRequest validateRegisterRequest(RegisterLibrarianRequest librarianRequest) {
        if (librarianRequest.getEmail() == null || librarianRequest.getEmail().isBlank())
            throw new IllegalArgumentException("Enter a valid email");


        if (librarianRequest.getUsername() == null || librarianRequest.getUsername().isBlank())
            throw new IllegalArgumentException("Enter a valid username");

        if (librarianRequest.getPassword() == null || librarianRequest.getPassword().isBlank())
            throw new IllegalArgumentException("Enter your password");

        if (librarianRequest.getFullName() == null || librarianRequest.getFullName().isBlank())
            throw new IllegalArgumentException("Enter a valid full name");

        return librarianRequest;

    }

    public static LoginLibrarianRequest validateRequest(LoginLibrarianRequest loginRequest) {
        if (loginRequest.getUsernameOrEmail() == null || loginRequest.getUsernameOrEmail().isBlank())
            throw new IllegalArgumentException("Enter a valid username or email");

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank())
            throw new IllegalArgumentException("Enter your password");

        return loginRequest;

    }
}
