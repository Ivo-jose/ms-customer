package br.com.ivogoncalves.ms_customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer {

    @Id	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column
	private String cpf;
    @Column(nullable = false)
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	private String name;
    @Column
	private Integer age;
	
	
	public Customer(String cpf, String name, Integer age) {
		this.cpf = cpf;
		this.name = name;
		this.age = age;
	}
}
