package ng.martG.eLibrary.dtos.responses.loanResponse;

import lombok.Data;
import ng.martG.eLibrary.data.models.LoanStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReturnBookResponse {

    private String id;
    private String readerId;
    private UUID bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
}
