package ng.martG.eLibrary.dtos.Responses.AuthReader;

import lombok.Data;

@Data
public class LogoutReaderResponse {

    private String username;
    private boolean loggedIn;

}
