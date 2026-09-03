package ng.martG.eLibrary.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String category;
    private String description;
    private int availableCopies;
    private  int totalCopies;
    private BookStatus status;
}
