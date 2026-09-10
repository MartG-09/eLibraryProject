package ng.martG.eLibrary.dtos.requests.librarianRequest;

import lombok.Data;
import ng.martG.eLibrary.data.models.Librarian;

@Data
public class AddBookRequest {

    private String usernameOrEmail;
    private String title;
    private String author;
    private String category;
    private String description;
    private  int totalCopies;

}
