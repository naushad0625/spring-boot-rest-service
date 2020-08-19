package com.bokal.restfulapi.payroll;

public class EmployeeNotFoundException extends RuntimeException{
    EmployeeNotFoundException(Long id) {
        super("Could not find emplotee " + id);
    }
}
