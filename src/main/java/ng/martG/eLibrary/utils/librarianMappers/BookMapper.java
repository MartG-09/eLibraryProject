package ng.martG.eLibrary.utils.librarianMappers;

import ng.martG.eLibrary.data.models.Book;
import ng.martG.eLibrary.data.models.BookStatus;
import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;
import ng.martG.eLibrary.dtos.requests.librarianRequest.UpdateBookRequest;
import ng.martG.eLibrary.dtos.responses.librarianResponse.AddBookResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.DeleteBookResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.FindBookByIdResponse;
import ng.martG.eLibrary.dtos.responses.librarianResponse.UpdateBookResponse;

public class BookMapper {

    public static Book mapToAddBookRequest(AddBookRequest request) {
        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getTotalCopies());
        book.setStatus(BookStatus.AVAILABLE);

        return book;
    }

    public static AddBookResponse mapToAddBookResponse(Book book) {
        AddBookResponse response = new AddBookResponse();

        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory());
        response.setDescription(book.getDescription());
        response.setTotalCopies(book.getTotalCopies());
        response.setAvailableCopies(book.getAvailableCopies());
        response.setStatus(book.getStatus());

        return response;
    }

    public static FindBookByIdResponse mapToFindBookByIdResponse(Book book) {
        FindBookByIdResponse response = new FindBookByIdResponse();
        response.setBookId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory());
        response.setDescription(book.getDescription());
        response.setTotalCopies(book.getTotalCopies());
        response.setAvailableCopies(book.getAvailableCopies());
        response.setBookStatus(book.getStatus());

        return response;

    }

    public static DeleteBookResponse mapToDeleteBookResponse(Book book) {
        DeleteBookResponse response = new DeleteBookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());

        return  response;
    }

    public static UpdateBookResponse mapToUpdateBookResponse(Book book) {
        UpdateBookResponse response = new UpdateBookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory());
        response.setDescription(book.getDescription());
        response.setTotalCopies(book.getTotalCopies());
        response.setAvailableCopies(book.getAvailableCopies());
        response.setStatus(book.getStatus());

        return response;
    }

    public static void mapToUpdateBookRequest(UpdateBookRequest bookRequest , Book book) {
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setCategory(bookRequest.getCategory());
        book.setDescription(bookRequest.getDescription());

    }

}
