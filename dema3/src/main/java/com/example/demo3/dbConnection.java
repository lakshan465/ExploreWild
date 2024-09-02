package com.example.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class dbConnection {
    public static Connection connection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//com.mysql.jdbc.Driver

            // Oshan palnA
            //Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://143.198.196.56:3306/explorewild", "explorewild", "Exp#World1");

            // Lakshan
            //Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11702663", "sql11702663", "dbQCaS1mX6");

            //planB
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/sql11702663", "root", "");


            //"jdbc:mysql://sql11.freesqldatabase.com:3306/sql11702663", "sql11702663", "dbQCaS1mX6"


            System.out.println("successfully connected!");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
