package ng.martG.eLibrary.dtos.requests.authLibrarian;

import lombok.Data;

@Data
public class RegisterLibrarianRequest {

    private String fullName;
    private String username;
    private String password;
    private String email;

}
