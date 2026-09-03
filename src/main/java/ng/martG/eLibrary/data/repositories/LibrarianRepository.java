package ng.martG.eLibrary.data.repositories;

import ng.martG.eLibrary.data.models.Librarian;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends MongoRepository<Librarian , String> {

}
