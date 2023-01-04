package app.model;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private ArrayList<Person> people;

    public Database() {
        people = new ArrayList<>();
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
