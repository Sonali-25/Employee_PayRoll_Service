package com.magic.payrollDB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
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
    @Test
    public void given_EmployeePayroll_count_OfFemale(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        String result = employeePayrollService.findCountOFFeMale();
        Assertions.assertEquals("3",result);

    }
    @Test
    public void given_EmployeePayroll_minimumSalary_OfFemale(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        String result = employeePayrollService.findMinimumSalaryOfFemale();
        Assertions.assertEquals("40000.0",result);

    }
    @Test
    public void given_EmployeePayroll_maximumSalary_OfFemale(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        String result = employeePayrollService.findMaximumSalaryOfFemale();
        Assertions.assertEquals("70000.0",result);

    }
    @Test
    public void given_EmployeePayroll_connectionCount_employeeInsertion(){
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        int result=employeePayrollService.insertNewPerson();
        Assertions.assertEquals(1,result);
    }
    @Test
    public void given_EmployeePayroll_connectionCount_employeeMultipleInsertion() throws SQLException {
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        int result = employeePayrollService.insertIntoMultipleTables();
        Assertions.assertEquals(1,result);
    }
    @Test
    public void given_employess_should_return_entries(){
        EmployeePayrollData[] arrayofEmps={
                new EmployeePayrollData(15,"SIDDHARTH",1254102,LocalDate.now()),
                new EmployeePayrollData(16,"SIDDHARTH R",12544102,LocalDate.now()),
                new EmployeePayrollData(17,"SID R",12514102, LocalDate.now())
        };
        EmployeePayrollService employeePayrollService=new EmployeePayrollService();
        Instant start=Instant.now();
        employeePayrollService.addEmpToPayroll(Arrays.asList(arrayofEmps));
        Instant end= Instant.now();
        Assertions.assertEquals(3,arrayofEmps.length);
    }

}



