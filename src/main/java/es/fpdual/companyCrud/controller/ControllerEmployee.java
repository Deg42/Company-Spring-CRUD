package es.fpdual.companyCrud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.fpdual.companyCrud.entities.Employee;
import es.fpdual.companyCrud.entities.Employee;
import es.fpdual.companyCrud.service.ServiceCompany;
import es.fpdual.companyCrud.service.ServiceEmployee;
import es.fpdual.companyCrud.service.exception.ValidateException;

@RestController
@RequestMapping("/api/v1")
public class ControllerEmployee {

	@Autowired
	private ServiceEmployee employeeService;

	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees(@RequestParam(required = false) String name) {
		try {
			List<Employee> employees = new ArrayList<Employee>();
			if (name == null) {
				this.employeeService.readAllEmployees().forEach(employees::add);
			} else {
				this.employeeService.readEmployeesByName(name).forEach(employees::add);
			}

			if (employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(employees, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<?> getCompanyById(@PathVariable("id") long id) {
		Employee employee = employeeService.readEmployeeById(id);

		if (employee == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@PostMapping("/employees")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		Map<Integer, Object> body = null;
		HttpStatus status = HttpStatus.OK;

		try {
			this.employeeService.saveEmployee(employee);
		} catch (ValidateException e) {
			e.printStackTrace();
			status = HttpStatus.PRECONDITION_FAILED;
			body = new HashMap<Integer, Object>();
			body.put(0, e.getErrors());
		}

		return new ResponseEntity<>(body, status);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> updateEmployeeById(@PathVariable long id, @RequestBody Employee newEmployee) {
		Map<Integer, Object> body = null;
		HttpStatus status = HttpStatus.OK;
		
		try {
			this.employeeService.updateEmployee(id, newEmployee);
		} catch (ValidateException e) {
			e.printStackTrace();
			status = HttpStatus.PRECONDITION_FAILED;
			body = new HashMap<Integer, Object>();
			body.put(0, e.getErrors());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(body, status);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteCompanyById(@PathVariable long id) {
		
		Employee company = this.employeeService.readEmployeeById(id);
		
		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		this.employeeService.deleteEmployee(id);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
