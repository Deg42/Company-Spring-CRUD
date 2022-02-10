package es.fpdual.companyCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.fpdual.companyCrud.entities.Company;
import es.fpdual.companyCrud.entities.Employee;

// @Repository
public interface RepositoryEmployee extends JpaRepository<Employee, Long> {

	List<Employee> findByNameContaining(String name);
}
