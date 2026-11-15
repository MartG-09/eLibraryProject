package ng.martG.eLibrary.dtos.responses.loanResponse;

import lombok.Data;
import ng.martG.eLibrary.data.models.LoanStatus;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class LoanBookResponse {

    private String id;
    private String readerId;
    private UUID bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LoanStatus status;
}
