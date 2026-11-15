package ng.martG.eLibrary.controllers;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.dtos.requests.authReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.responses.authReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.RegisterReaderResponse;
import ng.martG.eLibrary.services.auth.AuthReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/readers")
public class AuthReaderController {

    private final AuthReaderService readerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterReaderResponse> registerReader(@RequestBody RegisterReaderRequest readerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.registerReader(readerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginReaderResponse> loginReader(@RequestBody LoginReaderRequest readerRequest) {
        return ResponseEntity.ok(readerService.loginReader(readerRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutReaderResponse> logoutReader(@RequestBody LogoutReaderRequest readerRequest) {
        return ResponseEntity.ok(readerService.logoutReader(readerRequest));
    }

}
