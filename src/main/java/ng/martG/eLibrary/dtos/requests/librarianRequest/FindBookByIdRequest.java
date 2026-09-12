package ng.martG.eLibrary.dtos.requests.librarianRequest;

import lombok.Data;

import java.util.UUID;

@Data
public class FindBookByIdRequest {

    private UUID id;

}
