package ng.martG.eLibrary.dtos.responses.authLibrarian;

import lombok.Data;

@Data
public class LogoutLibrarianResponse {

    private String username;
    private boolean loggedIn;

}
