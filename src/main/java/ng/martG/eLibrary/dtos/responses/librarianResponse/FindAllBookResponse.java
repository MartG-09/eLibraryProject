package ng.martG.eLibrary.dtos.responses.librarianResponse;

import lombok.Data;
import ng.martG.eLibrary.data.models.Book;

import java.util.List;

@Data
public class FindAllBookResponse {

    private List<BookResponse> books;

}
