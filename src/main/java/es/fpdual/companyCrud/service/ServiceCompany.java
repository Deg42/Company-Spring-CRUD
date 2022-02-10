package es.fpdual.companyCrud.service;

import java.util.List;

import es.fpdual.companyCrud.entities.Company;
import es.fpdual.companyCrud.entities.Employee;
import es.fpdual.companyCrud.service.exception.ValidateException;

public interface ServiceCompany {

	void saveCompany(Company company, List<Employee> employees) throws ValidateException;

	List<Company> readAllCompanies();
	
	Company readCompanyById(Long id);
	
	void updateCompany(Long id, Company tutorial) throws ValidateException;
	
	void deleteCompany(Long id);
	
	void deleteAllCompanies();
	
	List<Company> readCompaniesByName(String name);
	

}
