package other.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import other.domain.SuspendArea;

import java.util.ArrayList;

public interface SuspendAreaRepository extends MongoRepository<SuspendArea, String> {
    ArrayList<SuspendArea> findAll();
    void deleteAll();
}
