package ng.martG.eLibrary.data.repositories;

import ng.martG.eLibrary.data.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Object> findByTitleAndAuthor(String title, String author);
}
