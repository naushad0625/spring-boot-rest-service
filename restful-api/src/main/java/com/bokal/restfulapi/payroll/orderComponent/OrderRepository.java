package com.bokal.restfulapi.payroll.orderComponent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<EmployeeOrder, Long> {
}
