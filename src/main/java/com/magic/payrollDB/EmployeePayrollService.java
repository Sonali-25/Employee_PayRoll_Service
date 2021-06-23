package com.magic.payrollDB;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {
    public List<EmployeePayrollData> readData() {
        String sql = "Select * from employee";
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
            }
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public int updateEmployeeData(String name, double salary) {
        String sql = String.format("update employee set salary = %.2f where name ='%s'", salary, name);
        try {
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateUsingPreparedStatement(double salary, String name) {
        String sql = "update employee set salary=? where name=?";
        try {
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            PreparedStatement ps = dbConnection.prepareStatement(sql);
            ps.setDouble(1, salary);
            ps.setString(2, name);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int findInDateRange() {
        String sql = "Select count(*) from employee where start BETWEEN '2019-10-11' AND '2021-06-19'";
        try {
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


