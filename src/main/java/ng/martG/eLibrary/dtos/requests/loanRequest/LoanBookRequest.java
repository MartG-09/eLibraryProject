package ng.martG.eLibrary.dtos.requests.loanRequest;

import lombok.Data;

import java.util.UUID;

@Data
public class LoanBookRequest {

    private UUID bookId;
    private String readerId;
    private int loanDays;
}
