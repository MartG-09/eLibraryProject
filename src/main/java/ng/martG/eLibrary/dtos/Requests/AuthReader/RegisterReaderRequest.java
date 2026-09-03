package ng.martG.eLibrary.dtos.Requests.AuthReader;

import lombok.Data;

@Data
public class RegisterReaderRequest {

    private String fullName;
    private String username;
    private String password;
    private String email;

}
