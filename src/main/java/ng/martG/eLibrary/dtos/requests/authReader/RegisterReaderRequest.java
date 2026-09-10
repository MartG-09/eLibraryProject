package ng.martG.eLibrary.dtos.requests.authReader;

import lombok.Data;

@Data
public class RegisterReaderRequest {

    private String fullName;
    private String username;
    private String password;
    private String email;

}
