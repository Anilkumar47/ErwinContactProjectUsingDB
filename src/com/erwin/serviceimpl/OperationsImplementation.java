package com.erwin.serviceimpl;

import com.erwin.client.ContactMenu;
import static com.erwin.client.ContactMenu.integerValidate;
import static com.erwin.client.ContactMenu.longValidate;
import static com.erwin.client.ContactMenu.stringValidate;
import com.erwin.dao.DbOperations;
import com.erwin.module.Contact;
import com.erwin.service.ContactOperations;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class OperationsImplementation implements ContactOperations {

    public enum searchOption {
        SEARCH_BY_NAME, SEARCH_BY_EMAIL, SEARCH_BY_MOBILE, GO_BACK
    }

    public enum deleteOption {
        DELETE_BY_NAME, DELETE_BY_EMAIL, DELETE_BY_MOBILE, GO_BACK
    }

    public enum updateOption {
        UPDATE_BY_NAME, UPDATE_BY_EMAIL, UPDATE_BY_MOBILE, GO_BACK
    }

    public static List<Contact> getList() {
        return allContacts;
    }

    public static List<Contact> allContacts = new CopyOnWriteArrayList<Contact>();
    Scanner s = new Scanner(System.in);

    @Override
    public void create() {

        Contact cp = new Contact();
        //inserting name
        while (true) {
            System.out.println("To create contact Enter name of person :");
            String name = s.next();
            String validateName = stringValidate(name);
            if (!validateName.equalsIgnoreCase("error")) {
                cp.setName(validateName);
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
        //inserting email
        while (true) {
            System.out.println("Enter email :");
            String email = s.next();
            String validateEmail = stringValidate(email);
            if (!validateEmail.equalsIgnoreCase("error")) {
                cp.setEmail(validateEmail);
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
        //inserting mobile number
        while (true) {
            System.out.println("Enter Mobile Number :");
            String mobile = s.next();
            int lengthOfMobileNumber = mobile.length();
            String validateMobile = longValidate(mobile);
            if (lengthOfMobileNumber != 10) {
                System.out.println("Mobile Number should be  10 digits");
                continue;
            }
            if (mobile.equalsIgnoreCase(validateMobile)) {
                cp.setMobile(validateMobile);
                break;
            }
        }
        System.out.println("Contact Created Successfully!");
        allContacts.add(cp);
        DbOperations.insertIntoDb(cp);
        System.out.println("You Want to create more(Y/N)");
        char createMore = s.next().charAt(0);
        if (createMore == 'y' || createMore == 'Y') {
            create();
        }
        getList();
    }

    @Override
    public void delete() {
        //System.out.println("1.Search by Name \n2.Search by Email \n3.Search by Mobile\n4.Go back");
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data to Delete !");
        } else {
            int k = 1;
            for (int i = 0; i < deleteOption.values().length; i++) {
                System.out.println(k++ + "." + deleteOption.values()[i]);
            }
            String inputValue = s.next();
            int delete = integerValidate(inputValue);

            if (delete > deleteOption.values().length) {
                System.out.println("Please enter a valid Number");
            }
            try {
                deleteOption menu = deleteOption.values()[delete - 1];
                switch (menu) {
                    case DELETE_BY_NAME:
                        deleteByName();
                        break;
                    case DELETE_BY_EMAIL:
                        _BY_EMAIL:
                        deleteByEmail();
                        break;
                    case DELETE_BY_MOBILE:
                        deleteByMobile();
                        break;
                    case GO_BACK: {
                        ContactMenu.menu();
                        break;
                    }
                }
            } catch (Exception e) {
                // System.out.println("please enter the valid number!");
                delete();
            }
        }

    }

    private void deleteByName() {
        boolean notFound = false;
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found to Delete !");
        } else {
            System.out.println("Enter the Name that you want to Delete!");
            String nameToDelete = s.next();
            String validateName = stringValidate(nameToDelete);
            if (!validateName.equalsIgnoreCase("error")) {
                for (Contact c : allContacts) {
                    if (nameToDelete.equals(c.getName())) {
                        System.out.println("Are you sure to delete(Y/N) " + nameToDelete);
                        char reCheck = s.next().charAt(0);
                        if (reCheck == 'y' || reCheck == 'Y') {
                            allContacts.remove(c);
                            DbOperations.deleteData(c);
                        }
                        notFound = false;
                    }
                }
                if (!notFound) {
                    System.out.println("No data found with this Name");
                }
            }
        }
    }

    public void deleteByMobile() {
        boolean notFound = false;
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found to Delete !");
        } else {
            System.out.println("Enter the mobile number that you want to Delete!");
            String mobileToDelete = s.next();
            int lengthOfMobileNumber = mobileToDelete.length();
            String validateMobile = longValidate(mobileToDelete);
            for (Contact c : allContacts) {
                if (mobileToDelete.equals(c.getMobile())) {
                    System.out.println("Are you sure to delete(Y/N) " + mobileToDelete);
                    char reCheck = s.next().charAt(0);
                    if (reCheck == 'y' || reCheck == 'Y') {
                        allContacts.remove(c);
                        DbOperations.deleteData(c);
                    }
                    notFound = true;
                }
            }
            if (!notFound) {
                System.out.println("No data found with this Name");
            }
        }
    }

    private void deleteByEmail() {
        boolean notFound = false;
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found to Delete !");
        } else {
            System.out.println("Enter the Email that you want to Delete!");
            String emailToDelete = s.next();
            String validateName = stringValidate(emailToDelete);
            if (!validateName.equalsIgnoreCase("error")) {
                for (Contact c : allContacts) {
                    if (emailToDelete.equals(c.getEmail())) {
                        System.out.println("Are you sure to delete(Y/N) " + emailToDelete);
                        char reCheck = s.next().charAt(0);
                        if (reCheck == 'y' || reCheck == 'Y') {
                            allContacts.remove(c);
                            DbOperations.deleteData(c);
                        }
                        notFound = true;
                    }
                }
                if (!notFound) {
                    System.out.println("No data found with this Email");
                }
            }
        }
    }

    @Override
    public void search() {
        //System.out.println("1.Search by Name \n2.Search by Email \n3.Search by Mobile\n4.Go back");
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found!");
        } else {
            int k = 1;
            for (int i = 0; i < searchOption.values().length; i++) {
                System.out.println(k++ + "." + searchOption.values()[i]);
            }
            String inputValue = s.next();
            int search = integerValidate(inputValue);

            if (search > searchOption.values().length) {
                System.out.println("Please enter a valid Number");
            }
            try {
                searchOption menu = searchOption.values()[search - 1];
                switch (menu) {
                    case SEARCH_BY_NAME:
                        searchByName();
                        break;
                    case SEARCH_BY_EMAIL:
                        searchByEmail();
                        break;
                    case SEARCH_BY_MOBILE:
                        searchByMobile();
                        break;
                    case GO_BACK: {
                        ContactMenu.menu();
                        break;
                    }
                }
            } catch (Exception e) {
                // System.out.println("please enter the valid number!");
                search();
            }
        }

    }

    private void searchByName() {
        while (true) {
            System.out.println("Enter the Name that you want to search : ");
            String name = s.next();
            String validateName = stringValidate(name);
            if (!validateName.equalsIgnoreCase("error")) {
                displayHeading();
                allContacts.stream()
                        .filter(p -> name.equalsIgnoreCase(p.getName()))
                        .forEach(p -> System.out.print(p.getName() + "\t\t" + p.getEmail() + "\t\t\t" + p.getMobile() + "\n"));
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    private void searchByEmail() {
        while (true) {
            System.out.println("Enter the Name that you want to search : ");
            String email = s.next();
            String validateName = stringValidate(email);
            if (!validateName.equalsIgnoreCase("error")) {
                displayHeading();
                allContacts.stream()
                        .filter(p -> email.equalsIgnoreCase(p.getEmail()))
                        .forEach(p -> System.out.print(p.getName() + "\t\t" + p.getEmail() + "\t\t\t" + p.getMobile() + "\n"));
                break;
            } else {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    private void searchByMobile() {
        System.out.println("Enter the Mobile Number that you want to search : ");
        String mobile = s.next();
        displayHeading();
        allContacts.stream()
                .filter(p -> mobile.equalsIgnoreCase(p.getMobile()))
                .forEach(p -> System.out.print(p.getName() + "\t\t" + p.getEmail() + "\t\t\t" + p.getMobile() + "\n"));

    }

    @Override
    public void update() {
        // System.out.println("1.Update by Name \n2.Update by Email \n3.Update by Mobile");
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found!");
        } else {
            int k = 1;
            for (int i = 0; i < updateOption.values().length; i++) {
                System.out.println(k++ + "." + updateOption.values()[i]);
            }
            String inputValue = s.next();
            int update = integerValidate(inputValue);
            if (update > updateOption.values().length) {
                System.out.println("Please enter a valid Number");
            }
            try {
                updateOption menu = updateOption.values()[update - 1];
                switch (menu) {
                    case UPDATE_BY_NAME:
                        System.out.println("Enter the name that you want to update");
                        String name = s.next();
                        allContacts.stream().filter(i -> i.getName().equalsIgnoreCase(name)).forEach(i -> updateMenu(i));
                        break;
                    case UPDATE_BY_EMAIL:
                        System.out.println("Enter the Email that you want to update");
                        String email = s.next();
                        allContacts.stream().filter(i -> i.getEmail().equalsIgnoreCase(email)).forEach(i -> updateMenu(i));
                        break;
                    case UPDATE_BY_MOBILE:
                        System.out.println("Enter the Mobile that you want to update");
                        String mobile = s.next();
                        allContacts.stream().filter(i -> i.getMobile().equalsIgnoreCase(mobile)).forEach(i -> updateMenu(i));
                        break;
                    case GO_BACK: {
                        ContactMenu.menu();
                    }
                    break;

                }
            } catch (Exception e) {
                update();
            }
        }

    }

    private void updateMenu(Contact c) {
        System.out.println("Choose the option that you want you update");
        System.out.println("1.Name\n2.Email\n3.Mobile");
        String inputValue = s.next();
        int optionToUpdate = integerValidate(inputValue);
        if (optionToUpdate > 4) {
            System.out.println("Please enter Valid Number");
        }
        try {
            switch (optionToUpdate) {
                case 1:
                    updateName(c.getName());
                    break;
                case 2:
                    updateEmail(c.getEmail());
                    break;
                case 3:
                    updateMobile(c.getMobile());
                    break;
                case 4: {
                    ContactMenu.menu();
                }
                break;
            }
        } catch (Exception e) {
            update();
        }
    }

    private void updateName(String name) {
        System.out.println("Enter New Name that you want update.");
        String NewName = s.next();
        allContacts.stream().filter(i -> i.getName().equalsIgnoreCase(name)).forEach(i -> i.setName(NewName));
        DbOperations.updateDbData(name, NewName,"name");
        System.out.println("Your Name is Updated Successfully!");
        display();
    }

    private void updateEmail(String email) {
        System.out.println("Enter New Email that you want update.");
        String NewEmail = s.next();
        allContacts.stream().filter(i -> i.getEmail().equalsIgnoreCase(email)).forEach(i -> i.setEmail(NewEmail));
        DbOperations.updateDbData(email, NewEmail,"email");
        System.out.println("Your Email is Updated Successfully!");
        display();

    }

    private void updateMobile(String mobile) {
        System.out.println("Enter New Mobile Number that you want update.");
        String NewMobile = s.next();
        allContacts.stream().filter(i -> i.getMobile().equalsIgnoreCase(mobile)).forEach(i -> i.setMobile(NewMobile));
        DbOperations.updateDbData(mobile, NewMobile,"mobile");
        System.out.println("Your Mobile Number is Updated Successfully!");
        display();

    }

    private void displayHeading() {
        System.out.println("---------------------------------------------------------");
        System.out.println("Name \t\t\t\t Email \t\t\t\t\t Mobile");
        System.out.println("---------------------------------------------------------");
    }

    @Override
    public void display() {
        System.out.println("Here is the list of Contacts");
        displayHeading();
        if (allContacts == null || allContacts.isEmpty()) {
            System.out.println("No Data Found!");
        }
        allContacts.forEach(i -> {
            System.out.print(i.getName() + "\t\t" + i.getEmail() + "\t\t\t" + i.getMobile() + "\n");
        });
        System.out.println("---------------------------------------------------------");

    }

}
