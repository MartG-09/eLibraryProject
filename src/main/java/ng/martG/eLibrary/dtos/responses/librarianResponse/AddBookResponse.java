package ng.martG.eLibrary.dtos.responses.librarianResponse;

import lombok.Data;

@Data
public class AddBookResponse {

    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

}
