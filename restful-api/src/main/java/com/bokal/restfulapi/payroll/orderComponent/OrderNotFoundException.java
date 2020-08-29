package com.bokal.restfulapi.payroll.orderComponent;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id) {
        super("No order found with id " + id);
    }
}
