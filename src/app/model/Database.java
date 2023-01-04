package app.model;

import java.io.*;
import java.util.*;

public class Database {
    private final LinkedList<Person> people;

    public Database() {
        people = new LinkedList<>();
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
        // Collections.unmodifiableList() prevents other objects from modifying the people list
        return Collections.unmodifiableList(people);
    }

    public void disconnect() {
        System.out.println("Database disconnected");
    }

    public void saveToFile(File file) throws IOException {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            Person[] persons = people.toArray(new Person[people.size()]);

            oos.writeObject(persons);

            oos.close();
    }

    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Person[] persons = (Person[])ois.readObject();

        people.clear();

        people.addAll(Arrays.asList(persons));

        ois.close();
    }

    public void removePerson(int index) {
        people.remove(index);
    }
}
