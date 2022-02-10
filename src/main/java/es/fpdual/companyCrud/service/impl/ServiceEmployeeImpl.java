package es.fpdual.companyCrud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fpdual.companyCrud.entities.Employee;
import es.fpdual.companyCrud.repository.RepositoryEmployee;
import es.fpdual.companyCrud.service.ServiceEmployee;
import es.fpdual.companyCrud.service.exception.ValidateException;

@Service
public class ServiceEmployeeImpl implements ServiceEmployee {

	RepositoryEmployee employeeRepository = null;

	@Autowired
	public ServiceEmployeeImpl(RepositoryEmployee employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void saveEmployee(Employee employee) throws ValidateException {
		Map<Integer, String> errors = this.validate(employee);

		Employee employeeToSave = new Employee();
		
		employeeToSave.setName(employee.getName());
		employeeToSave.setCompany(employee.getCompany());
		
		if (!errors.isEmpty()) {
			throw new ValidateException(errors);
		}

		this.employeeRepository.saveAndFlush(employeeToSave);
	}

	@Override
	public List<Employee> readAllEmployees() {
		return this.employeeRepository.findAll();
	}

	@Override
	public Employee readEmployeeById(Long id) {
		return this.employeeRepository.findById(id).orElse(null);
		
	}

	@Override
	public void updateEmployee(Long id, Employee newEmployee) {
		Map<Integer, String> errors = this.validate(newEmployee);
					
		Optional<Employee> oldEmployee = this.employeeRepository.findById(id);

		if (oldEmployee.isPresent() && errors.isEmpty()) {
			Employee oldPresentEmployee = oldEmployee.get();
			oldPresentEmployee.setName(newEmployee.getName());
		
			this.employeeRepository.saveAndFlush(oldPresentEmployee);
		}

	}

	@Override
	public void deleteEmployee(Long id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public void deleteAllEmployees() {
		this.employeeRepository.deleteAll();
	}

	@Override
	public List<Employee> readEmployeesByName(String name) {
		return this.employeeRepository.findByNameContaining(name);
	}

	public Map<Integer, String> validate(Employee employee) {
		Map<Integer, String> result = new HashMap<Integer, String>();

		if (employee.getName() == null || employee.getName().isEmpty()) {
			result.put(1, "Employee name is empty");
		}
//		if (employee.getCompany() == null) {
//			result.put(2, "Company is empty");
//		}
		return result;
	}

}
