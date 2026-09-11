package ng.martG.eLibrary.controllers;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.dtos.requests.authReader.LoginReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.LogoutReaderRequest;
import ng.martG.eLibrary.dtos.requests.authReader.RegisterReaderRequest;
import ng.martG.eLibrary.dtos.responses.authReader.LoginReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.LogoutReaderResponse;
import ng.martG.eLibrary.dtos.responses.authReader.RegisterReaderResponse;
import ng.martG.eLibrary.services.auth.AuthReaderService;
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
    public RegisterReaderResponse registerReader(@RequestBody RegisterReaderRequest readerRequest) {
        return readerService.registerReader(readerRequest);
    }

    @PostMapping("/login")
    public LoginReaderResponse loginReader(@RequestBody LoginReaderRequest readerRequest) {
        return readerService.loginReader(readerRequest);
    }

    @PostMapping("/logout")
    public LogoutReaderResponse logoutReader(@RequestBody LogoutReaderRequest readerRequest) {
        return readerService.logoutReader(readerRequest);
    }

}
