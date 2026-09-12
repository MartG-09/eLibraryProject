package ng.martG.eLibrary.dtos.responses.librarianResponse;

import lombok.Data;
import ng.martG.eLibrary.data.models.BookStatus;

import java.util.UUID;

@Data
public class FindBookByIdResponse {

    private UUID bookId;
    private String title;
    private String author;
    private String category;
    private String description;
    private int totalCopies;
    private int availableCopies;
    private BookStatus bookStatus;

}

