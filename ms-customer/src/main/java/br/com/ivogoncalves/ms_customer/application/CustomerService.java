package br.com.ivogoncalves.ms_customer.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ivogoncalves.ms_customer.domain.Customer;
import br.com.ivogoncalves.ms_customer.infra.exceptions.AttributeValidationException;
import br.com.ivogoncalves.ms_customer.infra.exceptions.ResourceNotFoundException;
import br.com.ivogoncalves.ms_customer.infra.repository.CustomerRepositoty;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private CustomerRepositoty repository;
	
	@Transactional
	public Customer save(Customer customer) {
		log.info("Saving customer: {}", customer);
		checkFormatCpf(customer.getCpf());
		return repository.save(customer);
	}
	
	public Customer findByCpf(String cpf) {
		log.info("Finding customer by CPF: {}", cpf);
		checkFormatCpf(cpf);
		Optional<Customer> optional = repository.findByCpf(cpf);
		Customer customer = optional.orElseThrow(() -> new ResourceNotFoundException("Customer CPF not found! CPF: " + cpf));
		return customer;
	}
	
	private void checkFormatCpf(String cpf) {
		if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"))
			throw new AttributeValidationException("Invalid CPF format! Expected format: XXX.XXX.XXX-XX");
	}
}
