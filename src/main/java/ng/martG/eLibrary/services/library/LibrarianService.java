package ng.martG.eLibrary.services.library;

import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.DeleteBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.FindBookByIdRequest;
import ng.martG.eLibrary.dtos.responses.librarianResponse.AddBookResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.DeleteBookResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.FindBookByIdResponse;

public interface LibrarianService {

    AddBookResponse addBook(AddBookRequest request);
    DeleteBookResponse deleteBook(DeleteBookRequest request);
    FindBookByIdResponse findBookById(FindBookByIdRequest request);

}
