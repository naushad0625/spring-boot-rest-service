package com.bokal.restfulapi.payroll.employeeComponent;

public class EmployeeNotFoundException extends RuntimeException{
    EmployeeNotFoundException(Long id) {
        super("Could not find emplotee " + id);
    }
}
