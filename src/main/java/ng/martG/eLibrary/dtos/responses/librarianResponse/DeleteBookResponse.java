package ng.martG.eLibrary.dtos.responses.librarianResponse;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteBookResponse {

    private UUID id;
    private String title;
    private String author;

}
