package com.bokal.restfulapi.payroll;

import com.bokal.restfulapi.payroll.employeeComponent.Employee;
import com.bokal.restfulapi.payroll.employeeComponent.EmployeeRepository;
import com.bokal.restfulapi.payroll.orderComponent.EmployeeOrder;
import com.bokal.restfulapi.payroll.orderComponent.OrderRepository;
import com.bokal.restfulapi.payroll.orderComponent.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
		return args -> {
			
			employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
			employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));
			
			employeeRepository.findAll()
							  .forEach(employee -> log.info("Preloaded " + employee));
			
			orderRepository.save(new EmployeeOrder("MacBook Pro", Status.COMPLETED));
			orderRepository.save(new EmployeeOrder("iPhone", Status.IN_PROGRESS));
			
			orderRepository.findAll()
						   .forEach(order -> log.info("Preloaded " + order));
		};
	}
}
