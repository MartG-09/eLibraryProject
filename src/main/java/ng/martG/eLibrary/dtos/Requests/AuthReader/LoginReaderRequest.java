package ng.martG.eLibrary.dtos.Requests.AuthReader;

import lombok.Data;

@Data
public class LoginReaderRequest {

    private String username;
    private String password;

}
