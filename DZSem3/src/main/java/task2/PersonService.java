package task2;

import org.springframework.beans.factory.annotation.Autowired;
import task1.Person;

import java.util.List;
import java.util.Optional;

public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    // Метод для добавления нового Person
    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    // Метод для обновления Person
    public Person updatePerson(Long id, Person personDetails) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.setName(personDetails.getName());
            person.setAge(personDetails.getAge());
            return personRepository.save(person);
        } else {
            return null;
        }
    }

    // Метод для удаления Person
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    // Метод для получения всех Person
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Метод для получения Person по id
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }
}
