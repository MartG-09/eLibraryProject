package ng.martG.eLibrary.data.repositories;

import ng.martG.eLibrary.data.models.Librarian;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends MongoRepository<Librarian , String> {

    Optional<Librarian> findLibrarianByUsername(String username);

    Optional<Librarian> findLibrarianByEmail(String email);

}
