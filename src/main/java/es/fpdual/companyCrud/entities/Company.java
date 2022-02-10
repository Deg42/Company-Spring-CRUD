package es.fpdual.companyCrud.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	// mappedBy and Ignored by var name
	@JsonIgnoreProperties("company")
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Employee> employees = new ArrayList<>();
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee.setCompany(this);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		employee.setCompany(null);
	}

}
