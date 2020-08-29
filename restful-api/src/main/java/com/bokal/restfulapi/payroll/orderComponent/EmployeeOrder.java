package com.bokal.restfulapi.payroll.orderComponent;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EmployeeOrder {
	
	private @Id
	@GeneratedValue
	Long id;
	private String description;
	private Status status;
	
	public EmployeeOrder() {}
	
	public EmployeeOrder(String description, Status status) {
		this.description = description;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		EmployeeOrder order = (EmployeeOrder) o;
		return Objects.equals(id, order.id) &&
				Objects.equals(description, order.description) &&
				status == order.status;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, description, status);
	}
	
	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", description='" + description + '\'' +
				", status=" + status +
				'}';
	}
}
