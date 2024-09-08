import entity.Person;
import repository.PersonService;

public class ProgramApp {
    public static void main(String[] args) {
        PersonService personService = new PersonService();
        Person person = new Person();
        Person person1 = Person.createPerson("Abv", 33);
        personService.add(person);
        personService.add(person1);
    }
}
