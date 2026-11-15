package ng.martG.eLibrary.controllers;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.dtos.requests.loanRequest.LoanBookRequest;
import ng.martG.eLibrary.dtos.requests.loanRequest.ReturnBookRequest;
import ng.martG.eLibrary.dtos.responses.loanResponse.LoanBookResponse;
import ng.martG.eLibrary.dtos.responses.loanResponse.ReturnBookResponse;
import ng.martG.eLibrary.services.loan.LibrarianLoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/loans")
public class LibrarianLoanController {

    private final LibrarianLoanService loanService;

    @PostMapping
    public ResponseEntity<LoanBookResponse> loanBook(@RequestBody LoanBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.loanBook(request));
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnBookResponse> returnBook(@RequestBody ReturnBookRequest request) {
        return ResponseEntity.ok(loanService.returnBook(request));
    }
}
