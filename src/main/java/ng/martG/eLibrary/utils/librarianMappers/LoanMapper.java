package ng.martG.eLibrary.utils.librarianMappers;

import ng.martG.eLibrary.data.models.Loan;
import ng.martG.eLibrary.dtos.responses.loanResponse.LoanBookResponse;
import ng.martG.eLibrary.dtos.responses.loanResponse.ReturnBookResponse;

public class LoanMapper {

    public static LoanBookResponse mapToLoanBookResponse(Loan loan) {

        LoanBookResponse response = new LoanBookResponse();
        response.setId(loan.getId());
        response.setReaderId(loan.getReader().getId());
        response.setBookId(loan.getBook().getId());
        response.setBorrowDate(loan.getBorrowDate());
        response.setDueDate(loan.getDueDate());
        response.setStatus(loan.getStatus());

        return response;
    }

    public static ReturnBookResponse mapToReturnBookResponse(Loan loan) {
        ReturnBookResponse response = new ReturnBookResponse();
        response.setId(loan.getId());
        response.setReaderId(loan.getReader().getId());
        response.setBookId(loan.getBook().getId());
        response.setBorrowDate(loan.getBorrowDate());
        response.setDueDate(loan.getDueDate());
        response.setReturnDate(loan.getReturnDate());
        response.setStatus(loan.getStatus());

        return response;
    }
}
