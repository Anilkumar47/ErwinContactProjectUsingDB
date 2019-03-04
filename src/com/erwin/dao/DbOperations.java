package com.erwin.dao;

import com.erwin.connection.DatabaseConnection;
import com.erwin.module.Contact;
import com.erwin.serviceimpl.OperationsImplementation;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbOperations {
    
    public static void insertIntoDb(Contact cp) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "insert into contacts values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, cp.getName());
            ps.setString(2, cp.getEmail());
            ps.setString(3, cp.getMobile());
            int i = ps.executeUpdate();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static void displayDbList() {
        OperationsImplementation.allContacts.clear();
        
        try {
            Connection con = DatabaseConnection.getConnection();
            Statement st = con.createStatement();
            String query = "select * from contacts";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                // System.out.println(rs.getString("name") + "\t\t\t" + rs.getString("email") + "\t\t\t" + rs.getString("mobile"));
                Contact c = new Contact();
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setMobile(rs.getString("mobile"));
                OperationsImplementation.allContacts.add(c);
            }
            con.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static void deleteData(Contact c) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String query = "DELETE FROM contacts WHERE mobile = " + c.getMobile() + ";";
            PreparedStatement ps = con.prepareStatement(query);
            int i = ps.executeUpdate();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }
    
    public static void updateDbData(String old, String newOne, String type) {
        try {
            Connection con = DatabaseConnection.getConnection();
            String query;
            if (type.equalsIgnoreCase("name")) {
                query = "UPDATE contacts SET name = ? WHERE name = ?";
            }else if(type.equalsIgnoreCase("email")){
                query = "UPDATE contacts SET email = ? WHERE email = ?";
            }else{
                query = "UPDATE contacts SET mobile = ? WHERE mobile = ?";
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newOne);
            ps.setString(2, old);
            int i = ps.executeUpdate();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
