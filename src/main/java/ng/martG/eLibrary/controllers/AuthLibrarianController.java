package ng.martG.eLibrary.controllers;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;
import ng.martG.eLibrary.services.auth.AuthLibrarianService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/librarian")
public class AuthLibrarianController {

    private final AuthLibrarianService librarianService;

    @PostMapping("/register")
    public RegisterLibrarianResponse registerLibrarian(@RequestBody RegisterLibrarianRequest librarianRequest) {
        return librarianService.registerLibrarian(librarianRequest);
    }

    @PostMapping("/login")
    public LoginLibrarianResponse loginLibrarian(@RequestBody LoginLibrarianRequest librarianRequest) {
        return librarianService.loginLibrarian(librarianRequest);
    }

    @PostMapping("/logout")
    public LogoutLibrarianResponse logoutLibrarian(@RequestBody LogoutLibrarianRequest librarianRequest) {
        return librarianService.logoutLibrarian(librarianRequest);
    }

}
