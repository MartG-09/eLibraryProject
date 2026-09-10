package ng.martG.eLibrary.dtos.responses.authReader;

import lombok.Data;

@Data
public class LogoutReaderResponse {

    private String username;
    private boolean loggedIn;

}
