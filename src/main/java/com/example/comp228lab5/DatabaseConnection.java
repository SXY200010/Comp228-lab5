package com.example.comp228lab5;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD"; // Database URL
    private static final String USER = "COMP228_F24_soh_55"; // Username
    private static final String PASSWORD = "Wh720731"; // Replace with your password

    // Method to establish database connection
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
