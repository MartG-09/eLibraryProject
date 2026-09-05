package ng.martG.eLibrary.data.repositories;

import ng.martG.eLibrary.data.models.Reader;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends MongoRepository<Reader, String> {
    Optional<Reader> findReaderByEmail(String email);

    Optional<Reader> findReaderByUsername(String username);

}
