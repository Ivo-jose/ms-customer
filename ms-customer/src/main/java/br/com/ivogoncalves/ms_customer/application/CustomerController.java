package br.com.ivogoncalves.ms_customer.application;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ivogoncalves.ms_customer.application.representation.CustomerSaveRequest;
import br.com.ivogoncalves.ms_customer.domain.Customer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private Environment env;
	
	@GetMapping
	public String status() {
		log.info("Getting microservice status from customers -- PORT: {}", env.getProperty("local.server.port"));
		return "OK!";
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<CustomerSaveRequest> findCustomerData(@RequestParam String cpf){
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
	 			         .queryParam("cpf",persistedCustomer.getCpf())
	 			         .build()
	 			         .toUri();
	 	return ResponseEntity.created(headerLocation)
	 			.body(new CustomerSaveRequest(persistedCustomer.getId(), persistedCustomer.getCpf(), 
	 				  persistedCustomer.getName(), persistedCustomer.getAge()));	
	 	
	}
}
