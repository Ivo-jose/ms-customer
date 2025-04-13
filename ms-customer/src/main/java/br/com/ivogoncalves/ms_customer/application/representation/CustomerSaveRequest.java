package br.com.ivogoncalves.ms_customer.application.representation;

import java.io.Serializable;

import br.com.ivogoncalves.ms_customer.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSaveRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cpf;
	private String name;
	private Integer age;
	
	public Customer toModel() {
		return  new Customer(cpf, name, age);
	}
}
