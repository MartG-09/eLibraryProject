package ng.martG.eLibrary.dtos.Responses.AuthReader;

import lombok.Data;

@Data
public class LoginReaderResponse {

    private String username;
    private String email;
    private boolean isLoggedIn;

}
