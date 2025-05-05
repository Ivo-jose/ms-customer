package br.com.ivogoncalves.ms_customer.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.ivogoncalves.ms_customer.domain.Customer;
import br.com.ivogoncalves.ms_customer.domain.exceptions.AttributeValidationException;
import br.com.ivogoncalves.ms_customer.domain.exceptions.DataIntegrityViolationException;
import br.com.ivogoncalves.ms_customer.domain.exceptions.ResourceNotFoundException;
import br.com.ivogoncalves.ms_customer.infra.repository.CustomerRepositoty;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private CustomerRepositoty repository;
	@Autowired
	private Environment env;
	
	
	public Customer findByCpf(String cpf) {
		log.info("Finding customer by CPF: {} -- PORT: {}", cpf, env.getProperty("local.server.port"));
		checkFormatCpf(cpf);
		Optional<Customer> optional = repository.findByCpf(cpf);
		Customer customer = optional.orElseThrow(() -> new ResourceNotFoundException("Customer CPF not found! CPF: " + cpf));
		return customer;
	}
	
	@Transactional
	public Customer save(Customer customer) {
		log.info("Saving customer: {}", customer);
		checkFormatCpf(customer.getCpf());
		checkDuplicateCpf(customer.getCpf());
		return repository.save(customer);
	}

	private void checkDuplicateCpf(String cpf) {
		if(repository.existsByCpf(cpf))
			throw new DataIntegrityViolationException("Customer with this CPF already exists! CPF: " + cpf);
	}
	
	// Auxiliary method to check the format of the CPF
	private void checkFormatCpf(String cpf) {
		if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") || cpf == null)
			throw new AttributeValidationException("Invalid CPF format! Expected format: XXX.XXX.XXX-XX");
	}
}
