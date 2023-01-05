package app.model;

import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("Running Database");

        Database db = new Database();
        try {
            db.connect();

            System.out.println("Connected successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        db.addPerson(new Person("Anguzu Daniel", "Software Eng", AgeCategory.ADULT, EmploymentCategory.selfEmployed, "0200", false, Gender.MALE));
        db.addPerson(new Person("Anzo Benjamin", "Software Eng", AgeCategory.ADULT, EmploymentCategory.selfEmployed, "0201", false, Gender.MALE));

        try {
            db.save();

            System.out.println("Saving person");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // loads data from the database
        try {
            db.loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        db.disconnect();
    }
}
