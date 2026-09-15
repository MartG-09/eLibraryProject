package ng.martG.eLibrary.dtos.requests.librarianRequest;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateBookRequest {

    private UUID id;
    private String title;
    private String author;
    private String category;
    private String description;

}
