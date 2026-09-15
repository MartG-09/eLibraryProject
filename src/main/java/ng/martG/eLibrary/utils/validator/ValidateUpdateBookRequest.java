package ng.martG.eLibrary.utils.validator;

import ng.martG.eLibrary.dtos.requests.librarianRequest.UpdateBookRequest;

public class ValidateUpdateBookRequest {

    public static UpdateBookRequest validateUpdateBookRequest(UpdateBookRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank())
            throw new IllegalArgumentException("Title is required");

        if (request.getAuthor() == null || request.getAuthor().isBlank())
            throw new IllegalArgumentException("Author is required");

        if (request.getCategory() == null || request.getCategory().isBlank())
            throw new IllegalArgumentException("Category is required");

        return request;

    }
}
