package br.com.ivogoncalves.ms_customer.application;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ivogoncalves.ms_customer.application.representation.CustomerSaveRequest;
import br.com.ivogoncalves.ms_customer.domain.Customer;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String status() {
		return "OK!";
	}
	
	@GetMapping("/{cpf}")
	public ResponseEntity<CustomerSaveRequest> findCustomerData(@PathVariable String cpf){
		Customer customer = customerService.findByCpf(cpf);
		var customerResponse = new CustomerSaveRequest(customer.getId(), customer.getCpf(), 
				customer.getName(), customer.getAge());
		return ResponseEntity.ok(customerResponse);
		
	}
	
	@PostMapping
	public ResponseEntity<CustomerSaveRequest> save(@RequestBody @Valid CustomerSaveRequest request) {
		var customer = request.toModel();
	 	Customer persistedCustomer = customerService.save(customer);
	 	URI headerLocation = ServletUriComponentsBuilder.fromCurrentRequest()
	 			         .path("/{cpf}")
	 			         .buildAndExpand(persistedCustomer.getCpf())
	 			         .toUri();
	 	return ResponseEntity.created(headerLocation)
	 			.body(new CustomerSaveRequest(persistedCustomer.getId(), persistedCustomer.getCpf(), 
	 				  persistedCustomer.getName(), persistedCustomer.getAge()));	
	 	
	}
}
