package task1;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person(1L,"Amica", 32);

        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/persons.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        person.writeExternal(objectOutputStream);

        FileInputStream fileInputStream = new FileInputStream("src/main/resources/persons.bin");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        person.readExternal(objectInputStream);

        System.out.println(person);
    }
}
