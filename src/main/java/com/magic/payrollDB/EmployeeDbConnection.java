package com.magic.payrollDB;

import java.sql.*;

public class EmployeeDbConnection {
    private String url = "jdbc:mysql://localhost:3306/pay_roll?useSSL=false";
    private String userName = "root";
    private String password = "SonaliJha@256";

    public Connection getDBConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,userName,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

