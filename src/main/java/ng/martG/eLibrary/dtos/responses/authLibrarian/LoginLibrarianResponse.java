package ng.martG.eLibrary.dtos.responses.authLibrarian;

import lombok.Data;

@Data
public class LoginLibrarianResponse {

    private String username;
    private String email;
    private boolean isLoggedIn;

}
