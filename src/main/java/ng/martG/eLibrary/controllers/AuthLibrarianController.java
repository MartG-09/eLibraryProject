package ng.martG.eLibrary.controllers;

import lombok.AllArgsConstructor;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LoginLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.LogoutLibrarianRequest;
import ng.martG.eLibrary.dtos.requests.authLibrarian.RegisterLibrarianRequest;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LoginLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.LogoutLibrarianResponse;
import ng.martG.eLibrary.dtos.responses.authLibrarian.RegisterLibrarianResponse;
import ng.martG.eLibrary.services.auth.AuthLibrarianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RegisterLibrarianResponse> registerLibrarian(@RequestBody RegisterLibrarianRequest librarianRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(librarianService.registerLibrarian(librarianRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginLibrarianResponse> loginLibrarian(@RequestBody LoginLibrarianRequest librarianRequest) {
        return ResponseEntity.ok(librarianService.loginLibrarian(librarianRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutLibrarianResponse> logoutLibrarian(@RequestBody LogoutLibrarianRequest librarianRequest) {
        return ResponseEntity.ok(librarianService.logoutLibrarian(librarianRequest));
    }

}
