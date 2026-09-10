package ng.martG.eLibrary.dtos.requests.authReader;

import lombok.Data;

@Data
public class LoginReaderRequest {

    private String usernameOrEmail;
    private String password;

}
