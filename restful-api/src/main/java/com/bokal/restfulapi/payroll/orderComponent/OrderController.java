package com.bokal.restfulapi.payroll.orderComponent;

import com.bokal.restfulapi.payroll.employeeComponent.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
	
	private final OrderRepository repository;
	private final OrderModelAssembler assembler;
	
	public OrderController(OrderRepository repository, OrderModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	
	@GetMapping("/orders")
	CollectionModel<EntityModel<EmployeeOrder>> all() {
		
		List<EntityModel<EmployeeOrder>> orders = repository.findAll()
															.stream() //
															.map(assembler::toModel) //
															.collect(Collectors.toList());
		
		return CollectionModel.of(orders, //
								  linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}
	
	@PostMapping("/orders")
	ResponseEntity<EntityModel<EmployeeOrder>> create(@RequestBody EmployeeOrder order) {
		order.setStatus(Status.IN_PROGRESS);
		EmployeeOrder newOrder = repository.save(order);
		
		return ResponseEntity
				.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
				.body(assembler.toModel(newOrder));
	}
	
	@GetMapping("/orders/{id}")
	EntityModel<EmployeeOrder> one(@PathVariable Long id) {
		EmployeeOrder order = repository.findById(id)
										.orElseThrow(() -> new OrderNotFoundException(id));
		return assembler.toModel(order);
	}
	
	@PutMapping("/orders/{id}")
	ResponseEntity<?> complete(@PathVariable Long id) {
		EmployeeOrder order = repository.findById(id)
										.orElseThrow(() -> new OrderNotFoundException(id));
		
		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(repository.save(order)));
		}
		
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
							 .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
							 .body(Problem.create()
										  .withTitle("Method not allowed")
										  .withDetail("Cannot complete order with status: " + order.getStatus()));
	}
	
	@DeleteMapping("/orders/{id}")
	ResponseEntity<?> cancel(@PathVariable Long id) {
		EmployeeOrder order = repository.findById(id)
										.orElseThrow(() -> new OrderNotFoundException(id));
		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.CANCELED);
			return ResponseEntity.ok(assembler.toModel(order));
		}
		
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create()
							 .withTitle("Method not allowed")
							 .withDetail("You cannot cancel an order with status: " + order.getStatus())
					 );
	}
}
