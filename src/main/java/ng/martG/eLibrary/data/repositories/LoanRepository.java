package ng.martG.eLibrary.data.repositories;

import ng.martG.eLibrary.data.models.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<Loan , String> {

}
