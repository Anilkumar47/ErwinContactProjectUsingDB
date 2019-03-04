package com.erwin.connection;

import com.erwin.client.ContactMenu;
import com.erwin.module.Contact;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public static String URL, DRIVER;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String userName = ContactMenu.p.getProperty("userName");
        String password = ContactMenu.p.getProperty("password");
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(URL,userName,password);
        return con;
    }
}
