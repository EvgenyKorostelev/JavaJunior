package task2;

import org.springframework.data.jpa.repository.JpaRepository;
import task1.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
