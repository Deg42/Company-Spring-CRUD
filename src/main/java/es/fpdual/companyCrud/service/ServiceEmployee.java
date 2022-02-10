package es.fpdual.companyCrud.service;

import java.util.List;

import es.fpdual.companyCrud.entities.Company;
import es.fpdual.companyCrud.entities.Employee;
import es.fpdual.companyCrud.service.exception.ValidateException;

public interface ServiceEmployee {

	void saveEmployee(Employee employee) throws ValidateException;

	List<Employee> readAllEmployees();
	
	Employee readEmployeeById(Long id);
	
	void updateEmployee(Long id, Employee employee) throws ValidateException;
	
	void deleteEmployee(Long id);
	
	void deleteAllEmployees();
	
	List<Employee> readEmployeesByName(String name);
	

}
