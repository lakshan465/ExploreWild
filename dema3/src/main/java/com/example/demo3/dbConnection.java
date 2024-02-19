package com.example.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class dbConnection {
    public static Connection connection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//com.mysql.jdbc.Driver
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/Pet", "root", "");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
