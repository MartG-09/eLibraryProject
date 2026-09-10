package ng.martG.eLibrary.dtos.requests.authLibrarian;

import lombok.Data;

@Data
public class LoginLibrarianRequest {

    private String usernameOrEmail;
    private String password;

}
