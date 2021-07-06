package com.magic.payrollDB;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestAPITest {

    @Before
    public void setup()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Employee[] getEmployeeList()
    {
        Response response = RestAssured.get("/employees");
        System.out.println("Employee Payroll Entries In JSONServer:\n" + response.asString());
        Employee[] arrayOfEmps = new Gson().fromJson(response.asString(), Employee[].class);
        return arrayOfEmps;
    }
    @Test
    public void givenEmployeeDataInJSONServer_WhenRetrieved_ShouldMatchTheCount()
    {
        Employee[] arrayOfEmps = getEmployeeList();
        EmployeeRepo employeeRepo;
        employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
        long entries = employeeRepo.countEntries();
        Assertions.assertEquals(2, entries);
    }
    @Test
    public void givenNewEmployee_whenAdded_ShouldMatch201ResponseAndCount()
    {
        Employee[] arrayOfEmps = getEmployeeList();
        EmployeeRepo employeeRepo;
        employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
        Employee employee = null;
        employee = new Employee(0, "Gill", "Thomas", 200000);

        Response response = addEmployeeToJSONServer(employee);
        int statusCode = response.getStatusCode();
        Assertions.assertEquals(201, statusCode);

        employee = new Gson().fromJson(response.asString(), Employee.class);
        employeeRepo.addEmployeeToPayroll(employee);
        long entries = employeeRepo.countEntries();
        Assertions.assertEquals(3, entries);
    }
}
