package ng.martG.eLibrary.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Librarian {

    @Id
    private String id;
    private String fullName;
    private String password;
    private String username;

}
