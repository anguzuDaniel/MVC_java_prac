package app.model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {
    private final LinkedList<Person> people;
    Connection conn;

    public Database() {
        people = new LinkedList<>();
    }

    public void connect() throws Exception {

        if (conn != null) return;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver not found");
        }

        // get the table to user
        // in this case my database is called mvc java
        String url = "jdbc:mysql://localhost:3306/mvc_java";
        conn = DriverManager.getConnection(url, "root", "password");
    }

    public void save() throws SQLException {
        String sql = "SELECT count(*) as count from person where id  = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        // update the person in the database
        String insertSql = "INSERT INTO person(id, name, age, employment, tax_id, us_citizen, gender, occupation) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement insertPreStmt = conn.prepareStatement(insertSql);


        // Update the user in the database
        String updateSql = "UPDATE person SET name = ?, age = ?, employment = ?, tax_id = ?, us_citizen = ?, gender = ?, occupation = ? " +
                "WHERE id = ?";

        PreparedStatement updatePreStmt = conn.prepareStatement(updateSql);

        for (Person person: people) {
            int id = person.getId();

            String name = person.getName();
            String occupation = person.getOccupation();
            AgeCategory ageCategory = person.getAgeCategory();
            EmploymentCategory employmentCategory = person.getEmploymentCategory();
            int taxId = Integer.parseInt(person.getTaxId());
            Gender gender = person.getGender();
            Boolean usCitizen = person.getUsCitizen();

            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();

            result.next();

            int count = result.getInt(1);

            if (count == 0) {
                System.out.println("Inserting person with id " + id);

                int col = 1;

                // prepare the values before inserting to the database
                insertPreStmt.setInt(col++, id);
                insertPreStmt.setString(col++, name);
                insertPreStmt.setString(col++, ageCategory.name());
                insertPreStmt.setString(col++, employmentCategory.name());
                insertPreStmt.setInt(col++, taxId);
                insertPreStmt.setBoolean(col++, usCitizen);
                insertPreStmt.setString(col++, gender.name());
                insertPreStmt.setString(col++, occupation);

                insertPreStmt.executeUpdate();
            }else {
                System.out.println("Updating person with id "+ id);

                int col = 1;

                // prepare the values before inserting to the database
                updatePreStmt.setString(col++, name);
                updatePreStmt.setString(col++, ageCategory.name());
                updatePreStmt.setString(col++, employmentCategory.name());
                updatePreStmt.setInt(col++, taxId);
                updatePreStmt.setBoolean(col++, usCitizen);
                updatePreStmt.setString(col++, gender.name());
                updatePreStmt.setString(col++, occupation);
                updatePreStmt.setInt(col++, id);


                updatePreStmt.executeUpdate();
            }

            System.out.println("Count for person with ID " + id + " is " + count);
        }

        insertPreStmt.close();
        stmt.close();
    }

    public void addPerson(Person person) {
        people.add(person);

        System.out.println(person);
    }

    public List<Person> getPeople() {
        // Collections.unmodifiableList() prevents other objects from modifying the people list
        return Collections.unmodifiableList(people);
    }

    public void disconnect() {
        if (conn == null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection");
            }
        }
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
