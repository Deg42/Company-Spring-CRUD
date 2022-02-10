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

import es.fpdual.companyCrud.entities.Company;
import es.fpdual.companyCrud.service.ServiceCompany;
import es.fpdual.companyCrud.service.exception.ValidateException;

@RestController
@RequestMapping("/api/v1")
public class ControllerCompany {

	@Autowired
	private ServiceCompany companyService;

	@GetMapping("/companies")
	public ResponseEntity<?> getAllCompanies(@RequestParam(required = false) String name) {
		try {
			List<Company> companies = new ArrayList<Company>();
			if (name == null) {
				this.companyService.readAllCompanies().forEach(companies::add);
			} else {
				this.companyService.readCompaniesByName(name).forEach(companies::add);
			}

			if (companies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(companies, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/companies/{id}")
	public ResponseEntity<?> getCompanyById(@PathVariable("id") long id) {
		Company company = companyService.readCompanyById(id);

		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	@PostMapping("/companies")
	public ResponseEntity<?> createCompany(@RequestBody Company company) {
		Map<Integer, Object> body = null;
		HttpStatus status = HttpStatus.OK;

		try {
			this.companyService.saveCompany(company, company.getEmployees());
		} catch (ValidateException e) {
			e.printStackTrace();
			status = HttpStatus.PRECONDITION_FAILED;
			body = new HashMap<Integer, Object>();
			body.put(0, e.getErrors());
		}

		return new ResponseEntity<>(body, status);
	}

	@PutMapping("/companies/{id}")
	public ResponseEntity<?> updateCompanyById(@PathVariable long id, @RequestBody Company newCompany) {
		Map<Integer, Object> body = null;
		HttpStatus status = HttpStatus.OK;
		
		try {
			this.companyService.updateCompany(id, newCompany);
		} catch (ValidateException e) {
			e.printStackTrace();
			status = HttpStatus.PRECONDITION_FAILED;
			body = new HashMap<Integer, Object>();
			body.put(0, e.getErrors());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(body, status);
	}
	
	@DeleteMapping("/companies/{id}")
	public ResponseEntity<?> deleteCompanyById(@PathVariable long id) {
		
		Company company = this.companyService.readCompanyById(id);
		
		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		this.companyService.deleteCompany(id);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
