package ng.martG.eLibrary.data.models;

import lombok.Data;

@Data
public class Readers {

    private String id;
    private String name;
    private String password;
    private boolean isLoggedIn;

}
