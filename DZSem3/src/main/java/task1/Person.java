package task1;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Person implements Externalizable {

    private Long id;
    private String name;
    private Integer age;


    public Person(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(id);
        if(name.equals("Amica")){
            out.writeObject("Alice");
        } else {
            out.writeObject(name);
        }
        out.writeObject(age * 8);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = (Long) in.readObject();
        name = (String) in.readObject();
        if(name.equals("Alice")){
            name = "Amica";
        }
        age = (Integer) in.readObject();
        age = age / 8;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
