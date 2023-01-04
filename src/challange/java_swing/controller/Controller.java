package challange.java_swing.controller;

import challange.java_swing.view.FormEvent;
import challange.java_swing.model.*;

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

    public void setData(List<Person> db) {

    }
}
