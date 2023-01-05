package app.controller;

import app.model.*;
import app.view.FormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    Database db = new Database();

    public List<Person> getPeople() {
        return db.getPeople();
    }


    public void addPerson(FormEvent event) {
        String name = event.getName();
        String occupation = event.getOccupation();
        int ageCatId = event.getAgeCategory();
        String empCat = event.getEmpCat();
        String taxId = event.getTaxId();
        boolean isUs = event.isUsCitizen();
        String gender = event.getGender();

        AgeCategory ageCat = null;

        switch (ageCatId) {
            case 0:
                ageCat = AgeCategory.CHILD;
                break;
            case 1:
                ageCat = AgeCategory.ADULT;
                break;
            case 2:
                ageCat = AgeCategory.SENIOR;
                break;
        }

        EmploymentCategory employmentCategory;

        if (empCat.equals("employed")) {
            employmentCategory = EmploymentCategory.employed;
        } else if (empCat.equals("self-employed")) {
            employmentCategory = EmploymentCategory.selfEmployed;
        } else if (empCat.equals("unemployed")) {
            employmentCategory = EmploymentCategory.unEmployed;
        } else {
            employmentCategory = EmploymentCategory.other;
            System.out.println(empCat);
        }

        Gender genderCat;

        if (gender.equals("male")) {
            genderCat = Gender.MALE;
        } else {
            genderCat = Gender.FEMALE;
        }

        Person person = new Person(name, occupation, ageCat, employmentCategory, taxId, isUs, genderCat);

        db.addPerson(person);
    }

    public void save() throws SQLException {
        db.save();
    }

    public void connect() throws Exception {
        db.connect();
    }

    public void load() throws SQLException {
        db.loadData();
    }

    public void disconnect() {
        db.disconnect();
    }

    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        db.loadFromFile(file);
    }

    public void removePerson(int index) {
        db.removePerson(index);
    }
}
