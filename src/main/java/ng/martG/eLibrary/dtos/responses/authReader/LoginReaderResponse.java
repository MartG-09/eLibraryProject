package ng.martG.eLibrary.dtos.responses.authReader;

import lombok.Data;

@Data
public class LoginReaderResponse {

    private String username;
    private String email;
    private boolean isLoggedIn;

}
