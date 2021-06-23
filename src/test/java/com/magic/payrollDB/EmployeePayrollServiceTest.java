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
    public void given_EmployeePayrollConnection_ShouldMatch_EmployeeCount(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData=employeePayrollService.readData();
        Assertions.assertEquals(2,employeePayrollData.size());
    }
    @Test
    public void given_NewSalary_Updated_Should_syncWithDb(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        int result = employeePayrollService.updateEmployeeData("Sonali",60000000);
        Assertions.assertEquals(1,result);
    }
    @Test
    public void given_NewSalary_UpdatedUsing_PreparedStatement(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        int result= employeePayrollService.updateUsingPreparedStatement(8100000,"Sonam");
        Assertions.assertEquals(1,result);
    }
    @Test
    public void given_EmployeePayroll_Connection_ShouldMatch_EmployeeCounting_InGivenDate(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        int result=employeePayrollService.findInDateRange();
        Assertions.assertEquals(2,result);
    }
    @Test
    public void given_EmployeePayroll_SumOf_FemaleSalary(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        String result = employeePayrollService.findSumOFSalaryFemale();
        Assertions.assertEquals("160000.0",result);
    }
    @Test
    public void given_EmployeePayroll_AverageOfSalary_OfFemale(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        String result = employeePayrollService.findAVGOFSalaryFeMale();
        Assertions.assertEquals("53333.333333333336",result);

    }
}



