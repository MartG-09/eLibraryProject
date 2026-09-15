package ng.martG.eLibrary.dtos.responses.librarianResponse;

import lombok.Data;
import ng.martG.eLibrary.data.models.BookStatus;

import java.util.UUID;

@Data
public class UpdateBookResponse {

    private UUID id;
    private String title;
    private String author;
    private String category;
    private String description;
    private int availableCopies;
    private  int totalCopies;
    private BookStatus status;

}
