package com.erwin.client;

import com.erwin.connection.DatabaseConnection;
import com.erwin.dao.DbOperations;
import com.erwin.serviceimpl.OperationsImplementation;
import com.ewin.Exception.ContactException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class ContactMenu {

    public static Properties p = new Properties();

    public enum operationsMenu {
        CREATE, UPDATE, SEARCH, DELETE, DISPLAY, EXIT
    };

    public static int integerValidate(String inputValue) {
        int newValue = 0;
        try {
            newValue = Integer.parseInt(inputValue);
        } catch (NumberFormatException e) {
            System.out.println("Please Enter a valid number !");
        }
        return newValue;
    }

    public static String longValidate(String inputValue) {
        long newValue = 0;
        try {
            newValue = Long.parseLong(inputValue);
        } catch (NumberFormatException e) {
            System.out.println("Please Enter a valid number !");
            return "";
        }
        return inputValue;
    }

    public static String stringValidate(String inputValue) {
        long newValue = 0;
        try {
            newValue = Long.parseLong(inputValue);
        } catch (NumberFormatException e) {
            // System.out.println("Please Enter a valid number !");
            return inputValue;
        }
        return "error";
    }

    public static void menu() {
        Scanner s = new Scanner(System.in);
        OperationsImplementation imp = new OperationsImplementation();
        while (true) {
            System.out.println();
            System.out.println("---------------Welcome to Erwin Contacts----------------");
            System.out.println();
            System.out.println("Choose a option in the above list");
            int k = 1;
            for (int i = 0; i < operationsMenu.values().length; i++) {
                System.out.println(k++ + "." + operationsMenu.values()[i]);
            }
            String inputVlaue = s.next();
            int option = integerValidate(inputVlaue);

            if (option > operationsMenu.values().length) {
                System.out.println("Please enter a valid Number");
                continue;
            }
            try {
                operationsMenu menu = operationsMenu.values()[option - 1];

                switch (menu) {
                    case CREATE:
                        imp.create();
                        break;
                    case UPDATE:
                        imp.update();
                        break;
                    case SEARCH:
                        imp.search();
                        break;
                    case DELETE:
                        imp.delete();
                        break;
                    case DISPLAY:
                        imp.display();
                        break;
                    case EXIT:
                        // rw.writeDataToFile();
                        System.exit(0);
                        break;
                }
            } catch (Exception e) {
                // System.out.println("Please enter a valid numer");
            }
        }

    }

    public static void main(String[] args) throws ContactException, ClassNotFoundException, SQLException {

        try {
            FileInputStream fs = new FileInputStream("E:\\Projects\\Java Projects\\Demos\\ErwinContacts\\src\\ConfigurationFiles\\info.properties");
            p.load(fs);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException io) {
            System.out.println(io);
        }
        DatabaseConnection.URL = p.getProperty("url");
        DatabaseConnection.DRIVER = p.getProperty("driver");
        DatabaseConnection.getConnection();
        DbOperations.displayDbList();
        ContactMenu.menu();

    }

}
