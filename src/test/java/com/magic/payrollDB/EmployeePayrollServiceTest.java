package com.magic.payrollDB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class EmployeePayrollServiceTest {
    @Test
    public void getDBConnection() {
        Connection dbConnection = new EmployeeDbConnection().getDBConnection();
        System.out.println(dbConnection);
    }
    @Test
    public void givenEmployeePayrollConnectionShouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData=employeePayrollService.readData();
        Assertions.assertEquals(2,employeePayrollData.size());
    }
}



