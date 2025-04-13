package br.com.ivogoncalves.ms_customer.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ivogoncalves.ms_customer.domain.Customer;

@Repository
public interface CustomerRepositoty extends JpaRepository<Customer, Long> {

	Optional<Customer> findByCpf(String cpf);
}
