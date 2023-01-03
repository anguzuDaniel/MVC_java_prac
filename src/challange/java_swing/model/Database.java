package challange.java_swing.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final ArrayList<Person> people;

    public Database() {
        people = new ArrayList<Person>();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public List<Person> getPeople() {
        return people;
    }

    public void disconnect() {
        System.out.println("Database disconnected");
    }
}
