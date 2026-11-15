package ng.martG.eLibrary.services.loan;

import ng.martG.eLibrary.dtos.requests.loanRequest.LoanBookRequest;
import ng.martG.eLibrary.dtos.requests.loanRequest.ReturnBookRequest;
import ng.martG.eLibrary.dtos.responses.loanResponse.LoanBookResponse;
import ng.martG.eLibrary.dtos.responses.loanResponse.ReturnBookResponse;

public interface LibrarianLoanService {

    LoanBookResponse loanBook(LoanBookRequest request);

    ReturnBookResponse returnBook(ReturnBookRequest request);
}
