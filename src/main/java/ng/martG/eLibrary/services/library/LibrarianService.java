package ng.martG.eLibrary.services.library;

import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.responses.librarianResponse.AddBookResponse;

public interface LibrarianService {

    AddBookResponse addBook(AddBookRequest request);
}
