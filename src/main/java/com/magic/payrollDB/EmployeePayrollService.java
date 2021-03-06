package com.magic.payrollDB;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String findSumOFSalaryFemale() {
        try {
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select SUM(basic_pay) from employee_pay_roll where Gender = 'F' ");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public String findAVGOFSalaryFeMale(){
        try{
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select AVG(basic_pay) from employee_pay_roll where Gender = 'F' ");
            resultSet.next();
            return resultSet.getString(1);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public String findCountOFFeMale(){
        try{
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(basic_pay) from employee_pay_roll where Gender = 'F' ");
            resultSet.next();
            return resultSet.getString(1);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public String findMinimumSalaryOfFemale(){
        try{
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select min(basic_pay) from employee_pay_roll where Gender = 'F' ");
            resultSet.next();
            return resultSet.getString(1);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public String findMaximumSalaryOfFemale(){
        try{
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select max(basic_pay) from employee_pay_roll where Gender = 'F' ");
            resultSet.next();
            return resultSet.getString(1);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int insertNewPerson(){
        String sql="INSERT INTO employee VALUES(4,'Sona',500000,'2020-01-01')";
        try {
            Connection dbConnection = new EmployeeDbConnection().getDBConnection();
            Statement statement = dbConnection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public int insertIntoMultipleTables() throws SQLException {
        Connection dbConnection = new EmployeeDbConnection().getDBConnection();
        try {
            dbConnection.setAutoCommit(false);
            Statement statement = dbConnection.createStatement();
            String sql1="INSERT INTO emp_pay_roll VALUES(6,'Sona','84715751555','Bihar')";
            statement.executeUpdate(sql1);
            String sql="INSERT INTO employee VALUES(5,'Sona',500000,'2020-01-01')";
            statement.executeUpdate(sql);
            dbConnection.commit();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            dbConnection.rollback();
        }
        return 0;
    }
    public void addEmpToPayroll(List<EmployeePayrollData> employeePayrollDataList) {
        employeePayrollDataList.forEach(employeePayrollData -> {
            this.addEmpToPayroll(employeePayrollData.id, employeePayrollData.name,
                    employeePayrollData.salary, employeePayrollData.startDate);
            System.out.println("Employee Added: " +employeePayrollData.id);
        });
    }

    private void addEmpToPayroll(int id, String name, double salary, LocalDate startDate) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
    }
    public void addEmployeesToPayrollwithThreads(List<EmployeePayrollData> employeePayrollDataList){
        Map<Integer,Boolean> employeeAdditionStatus = new HashMap<Integer,Boolean>();
        employeePayrollDataList.forEach(employeePayrollData ->{
            Runnable task=()->{
                employeeAdditionStatus.put(employeePayrollData.hashCode(),false);
                this.addEmpToPayroll(employeePayrollData.id, employeePayrollData.name,
                        employeePayrollData.salary, employeePayrollData.startDate);
                employeeAdditionStatus.put(employeePayrollData.hashCode(),true);

            };
            Thread thread= new Thread(task,employeePayrollData.name);
            thread.start();
        });
        while(employeeAdditionStatus.containsValue(false)){
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){}
        }
    }

}


