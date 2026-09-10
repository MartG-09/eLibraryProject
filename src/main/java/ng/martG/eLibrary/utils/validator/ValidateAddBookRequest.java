package ng.martG.eLibrary.utils.validator;

import ng.martG.eLibrary.dtos.requests.librarianRequest.AddBookRequest;

public class ValidateAddBookRequest {

    public static void validateAddBook(AddBookRequest request) {
        if (request.getUsernameOrEmail().isBlank())
            throw new IllegalArgumentException("Username or email is required");

        if (request.getTitle().isBlank())
            throw new IllegalArgumentException("Title is required");

        if (request.getAuthor().isBlank())
            throw new IllegalArgumentException("Author is required");

        if (request.getTotalCopies() == 0)
            throw new IllegalArgumentException("Total copies is required");

        if (request.getCategory().isBlank())
            throw new IllegalArgumentException("Category is required");

    }

}
