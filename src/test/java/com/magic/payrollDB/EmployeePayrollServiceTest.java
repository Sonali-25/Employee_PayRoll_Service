package com.magic.payrollDB;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmployeePayrollServiceTest {
    @Test
    public void getDBConnection() {
        Connection dbConnection = new EmployeeDbConnection().getDBConnection();
        System.out.println(dbConnection);
    }
}



